package springai.test.demo.Service;

import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.client.Generation;
import org.springframework.ai.document.Document;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.ai.retriever.impl.VectorStoreRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Value("classpath:/system-info-prompt.st")
    private Resource systemInfoPrompt;

    private final AiClient aiClient;
    private final VectorStore vectorStore;
    public ChatService(AiClient aiClient, VectorStore vectorStore){
        this.aiClient=aiClient;
        this.vectorStore=vectorStore;
    }
    public String generateResponse(String msg){
        List<Document> similarDoc=new VectorStoreRetriever(vectorStore).retrieve(msg);

        UserMessage userMessage=new UserMessage(msg);

        Message systemMsg=getSystemMsg(similarDoc);
        Prompt prompt=new Prompt(List.of(systemMsg , userMessage));
        AiResponse aiResponse=aiClient.generate(prompt);
        return aiResponse.getGeneration().getText();
    }
    private Message getSystemMsg(List<Document> doc){
        String docs=doc.stream()
                .map(entry -> entry.getContent())
                .collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate=new SystemPromptTemplate(systemInfoPrompt);
        return systemPromptTemplate.createMessage(Map.of("documents",docs));
    }
}

package springai.test.demo.Controllers;


import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springai.test.demo.Sec.ApiKey;
import springai.test.demo.Service.ChatService;

@RestController
@RequestMapping("chatbot")
public class ChatController {
    private final ChatService chatService;
    ChatLanguageModel chatLanguageModel= OpenAiChatModel.withApiKey(ApiKey.apikey);
    ConversationalChain chain=ConversationalChain.builder().chatLanguageModel(OpenAiChatModel.withApiKey(ApiKey.apikey)).build();

    public ChatController(ChatService chatService){
        this.chatService=chatService;

    }
    @PostMapping(produces = "text/plain")
    public String chatbot(@RequestBody InfosQst infosQst ){
        return chatService.generateResponse(infosQst.qst());

    }
    record InfosQst(String qst){}
    @PostMapping("/conv")
    public String chat(@RequestBody String msg){
        return chatLanguageModel.generate(msg);
    }
    @PostMapping("/save")
    public String rememberChat(@RequestBody String msg){
        return chain.execute(msg);
    }

}

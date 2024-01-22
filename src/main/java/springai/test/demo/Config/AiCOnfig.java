package springai.test.demo.Config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.loader.impl.JsonLoader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.impl.InMemoryVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiCOnfig {
    @Value("classpath:/info.txt")
    private Resource info;
    @Bean
    VectorStore vectorStore(EmbeddingClient embeddingClient){
        return new InMemoryVectorStore(embeddingClient);
    }
    @Bean
    ApplicationRunner loadInfo(VectorStore vectorStore){
        return args -> {
            vectorStore.add(new TextLoader(info).load());
        };
    }

}

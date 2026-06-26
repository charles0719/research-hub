package com.raven.demo.springai;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class SpringAiRagDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiRagDemoApplication.class, args);
    }

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    CommandLineRunner loadKnowledgeBase(SimpleVectorStore vectorStore) {
        return args -> {
            List<Document> documents = loadDocuments("docs");
            if (!documents.isEmpty()) {
                vectorStore.add(documents);
            }
        };
    }

    private List<Document> loadDocuments(String classpathDir) throws IOException, URISyntaxException {
        Path docDir = new ClassPathResource(classpathDir).getFile().toPath();
        try (Stream<Path> stream = Files.list(docDir)) {
            return stream
                    .filter(path -> path.toString().endsWith(".md"))
                    .map(this::toDocument)
                    .toList();
        }
    }

    private Document toDocument(Path path) {
        try {
            String content = Files.readString(path);
            return new Document(content, Map.of("source", path.getFileName().toString()));
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load document: " + path, ex);
        }
    }
}

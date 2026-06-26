package com.raven.demo.springai;

import java.util.List;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.stereotype.Service;

@Service
public class RagService {

    private static final String SYSTEM_PROMPT = """
            你是一个面试知识库助手。
            回答时优先基于给定上下文，不要脱离上下文自由发挥。
            如果上下文不足以支持明确答案，请直接说明“当前知识库上下文不足”。
            回答风格要求：先给结论，再给原理，再给面试表达。
            """;

    private final ChatClient chatClient;
    private final SimpleVectorStore vectorStore;

    public RagService(ChatClient.Builder chatClientBuilder, SimpleVectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    public RagAnswer ask(String question) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder().query(question).topK(4).build());
        String context = documents.stream()
                .map(document -> "来源: " + document.getMetadata().get("source") + "\n" + document.getText())
                .reduce((left, right) -> left + "\n\n---\n\n" + right)
                .orElse("当前没有检索到相关上下文。");

        String answer = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user("""
                        问题：
                        %s

                        上下文：
                        %s
                        """.formatted(question, context))
                .call()
                .content();

        List<String> sources = documents.stream()
                .map(document -> String.valueOf(document.getMetadata().get("source")))
                .distinct()
                .toList();

        return new RagAnswer(question, answer, sources);
    }

    public record RagAnswer(String question, String answer, List<String> sources) {
    }
}

package com.raven.demo.langchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import java.util.List;

public class OrderAgentDemoApplication {

    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Please set OPENAI_API_KEY before running this demo.");
        }

        String modelName = System.getenv().getOrDefault("OPENAI_CHAT_MODEL", "gpt-4o-mini");
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.0)
                .build();

        OrderAssistant assistant = AiServices.builder(OrderAssistant.class)
                .chatLanguageModel(model)
                .tools(new OrderTools(new FakeOrderDataStore()))
                .build();

        List<String> prompts = List.of(
                "帮我看下订单 1003 为什么还没发货，如果需要的话顺便建一个运营工单。",
                "订单 2001 现在发到哪了？",
                "订单 3008 为什么没有物流信息？");

        for (String prompt : prompts) {
            System.out.println("===== 用户问题 =====");
            System.out.println(prompt);
            System.out.println("===== 助手回答 =====");
            System.out.println(assistant.answer(prompt));
            System.out.println();
        }
    }
}

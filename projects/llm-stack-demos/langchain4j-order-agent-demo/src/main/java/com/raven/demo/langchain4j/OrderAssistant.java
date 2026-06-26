package com.raven.demo.langchain4j;

import dev.langchain4j.service.SystemMessage;

public interface OrderAssistant {

    @SystemMessage("""
            你是一个订单履约助手。
            你的职责是：
            1. 优先通过工具查询真实订单和物流数据
            2. 不要编造订单状态
            3. 如果判断需要建工单，再调用建单工具
            4. 最终回答先给结论，再给原因，再给下一步建议
            """)
    String answer(String userMessage);
}

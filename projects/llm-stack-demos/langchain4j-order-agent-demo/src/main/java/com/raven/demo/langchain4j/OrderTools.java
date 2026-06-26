package com.raven.demo.langchain4j;

import dev.langchain4j.agent.tool.Tool;

public class OrderTools {

    private final FakeOrderDataStore dataStore;

    public OrderTools(FakeOrderDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Tool("按订单号查询订单履约状态和阻塞原因")
    public String queryOrder(String orderId) {
        FakeOrderDataStore.OrderSnapshot order = dataStore.queryOrder(orderId);
        if (order == null) {
            return "没有找到订单 " + orderId;
        }
        return """
                订单号: %s
                状态: %s
                SKU: %s
                数量: %d
                原因: %s
                """.formatted(order.orderId(), order.status(), order.skuCode(), order.quantity(), order.statusReason());
    }

    @Tool("按订单号查询物流轨迹")
    public String queryLogistics(String orderId) {
        return dataStore.queryLogistics(orderId);
    }

    @Tool("为订单问题创建运营工单")
    public String createTicket(String orderId, String reason) {
        return "已为订单 " + orderId + " 创建运营工单，工单原因：" + reason + "，模拟工单号：OPS-" + orderId;
    }
}

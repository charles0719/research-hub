package com.raven.demo.langchain4j;

import java.util.Map;

public class FakeOrderDataStore {

    private final Map<String, OrderSnapshot> orders = Map.of(
            "1003", new OrderSnapshot("1003", "WAIT_STOCK", "SKU-REDIS-001", 2, "华东仓库存不足，等待补货"),
            "2001", new OrderSnapshot("2001", "SHIPPED", "SKU-MYSQL-009", 1, "已出库，等待承运商揽收完成"),
            "3008", new OrderSnapshot("3008", "WAIT_PAY", "SKU-JAVA-021", 1, "订单待支付，尚未进入履约"));

    private final Map<String, String> logistics = Map.of(
            "2001", "顺丰运单 SF123456789CN，已到上海转运中心，预计明日送达",
            "3008", "订单尚未发货，没有物流信息");

    public OrderSnapshot queryOrder(String orderId) {
        return orders.get(orderId);
    }

    public String queryLogistics(String orderId) {
        return logistics.getOrDefault(orderId, "当前没有查到物流轨迹");
    }

    public record OrderSnapshot(String orderId, String status, String skuCode, int quantity, String statusReason) {
    }
}

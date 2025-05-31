package spring.practice.elmenus_lite.model;

import java.util.Map;
import java.util.Set;

public enum OrderStatusEnum {
    PENDING,
    PREPARING,
    READY_FOR_PICKUP,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    private static final Map<OrderStatusEnum, Set<OrderStatusEnum>> statusTransition =
            Map.of(
                    PENDING,Set.of(PREPARING,CANCELLED),
                    PREPARING,Set.of(READY_FOR_PICKUP,CANCELLED),
                    READY_FOR_PICKUP,Set.of(OUT_FOR_DELIVERY,CANCELLED),
                    OUT_FOR_DELIVERY,Set.of(DELIVERED,CANCELLED),
                    DELIVERED,Set.of(),
                    CANCELLED,Set.of()
            );

    public boolean isAllowedTransition(OrderStatusEnum newStatus) {
        return statusTransition.get(this).contains(newStatus);
    }
}

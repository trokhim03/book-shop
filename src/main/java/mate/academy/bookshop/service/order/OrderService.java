package mate.academy.bookshop.service.order;

import java.util.List;
import mate.academy.bookshop.dto.order.OrderRequestDto;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.dto.order.OrderUpdateStatusDto;
import mate.academy.bookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.bookshop.model.User;

public interface OrderService {
    OrderResponseDto createOrderByUser(User user, OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getOrderByUser(User user);

    OrderResponseDto updateStatusByOrderId(Long orderId,
                                           OrderUpdateStatusDto orderUpdateStatusDto);

    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId, Long userId);

    OrderItemResponseDto getOrderItemFromOrderById(Long orderId, Long orderItemId);
}

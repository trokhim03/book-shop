package mate.academy.bookshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookshop.dto.order.OrderRequestDto;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.dto.order.OrderUpdateStatusDto;
import mate.academy.bookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.bookshop.model.User;
import mate.academy.bookshop.service.order.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Operations for managing orders")
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get all orders for the authenticated user",
            description = "Retrieves a list of orders for the currently authenticated user.")
    @GetMapping
    public List<OrderResponseDto> getOrders(Authentication authentication) {
        return orderService.getOrderByUser((User) authentication.getPrincipal());
    }

    @Operation(summary = "Create a new order",
            description = "Creates a new order for the authenticated user.")
    @PostMapping
    public OrderResponseDto createOrder(Authentication authentication,
                                        @RequestBody @Valid OrderRequestDto orderRequestDto) {
        return orderService.createOrderByUser((User) authentication.getPrincipal(),
                orderRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update the status of an order",
            description = "Updates the status of an order identified"
                    + " by orderId for the authenticated user.")
    @PatchMapping("/{orderId}")
    public OrderResponseDto updateStatus(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderUpdateStatusDto orderUpdateStatusDto) {
        return orderService.updateStatusByOrderId(orderId, orderUpdateStatusDto);
    }

    @Operation(summary = "Get all items in a specific order",
            description = "Retrieves the list of items for a specific"
                    + " order identified by orderId for the authenticated user.")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItems(@PathVariable Long orderId,
                                                    Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemsByOrderId(orderId, user.getId());
    }

    @Operation(summary = "Get a specific item from an order",
            description = "Retrieves a specific item from an order "
                    + "by itemId, within the order identified by orderId.")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemResponseDto getOrderItem(@PathVariable Long orderId,
                                             @PathVariable Long itemId) {
        return orderService.getOrderItemFromOrderById(orderId, itemId);
    }
}

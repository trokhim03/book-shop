package mate.academy.bookshop.mapper;

import java.util.List;
import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.order.OrderRequestDto;
import mate.academy.bookshop.dto.order.OrderResponseDto;
import mate.academy.bookshop.dto.order.OrderUpdateStatusDto;
import mate.academy.bookshop.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItemsDto", source = "orderItems", qualifiedByName = "orderItemToDto")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    @Mapping(target = "orderDate", source = "orderDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDto(List<Order> order);

    Order toEntity(OrderRequestDto orderRequestDto);

    void updateOrderStatusFromDto(OrderUpdateStatusDto orderUpdateStatusDto,
                                  @MappingTarget Order order);
}

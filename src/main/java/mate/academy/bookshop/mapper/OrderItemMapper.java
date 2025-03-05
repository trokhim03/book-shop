package mate.academy.bookshop.mapper;

import java.util.List;
import mate.academy.bookshop.config.MapperConfig;
import mate.academy.bookshop.dto.orderitem.OrderItemResponseDto;
import mate.academy.bookshop.model.OrderItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Named("orderItemToDto")
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @IterableMapping(qualifiedByName = "orderItemToDto")
    List<OrderItemResponseDto> toDto(List<OrderItem> orderItems);
}

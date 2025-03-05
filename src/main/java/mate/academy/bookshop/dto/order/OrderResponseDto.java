package mate.academy.bookshop.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import mate.academy.bookshop.dto.orderitem.OrderItemResponseDto;

@Data
public class OrderResponseDto {
    private Long id;

    private Long userId;

    private List<OrderItemResponseDto> orderItemsDto;

    private LocalDateTime orderDate;

    private BigDecimal total;

    private String status;
}

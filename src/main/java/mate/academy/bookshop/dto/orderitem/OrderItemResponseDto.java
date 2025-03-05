package mate.academy.bookshop.dto.orderitem;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long id;

    private Long bookId;

    private int quantity;

    private BigDecimal price;
}

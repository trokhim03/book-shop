package mate.academy.bookshop.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.bookshop.model.Order;
import mate.academy.bookshop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder(Order order);

    Optional<OrderItem> findByIdAndOrderId(Long orderItemId, Long orderId);

}

package mate.academy.bookshop.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.bookshop.model.Order;
import mate.academy.bookshop.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    List<Order> findAllByUser(User user);

    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}

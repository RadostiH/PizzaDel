package sapproject.pizzadelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sapproject.pizzadelivery.domain.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllOrdersByCustomer_UsernameOrderByFinishedOn(String username);
}

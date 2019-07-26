package sapproject.pizzadelivery.service;

import org.springframework.transaction.annotation.Transactional;
import sapproject.pizzadelivery.domain.models.service.OrderServiceModel;

import java.util.List;

public interface OrderService {


    @Transactional
    void createOrder(OrderServiceModel orderServiceModel);

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomer(String username);

    OrderServiceModel findOrderById(String id);

    void deleteOrder(String id);
}

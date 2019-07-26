package sapproject.pizzadelivery.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sapproject.pizzadelivery.domain.models.rest.ProductOrderRequestModel;
import sapproject.pizzadelivery.domain.models.service.ProductServiceModel;
import sapproject.pizzadelivery.domain.models.view.ProductAllViewModel;
import sapproject.pizzadelivery.service.OrderService;
import sapproject.pizzadelivery.service.ProductService;

import java.security.Principal;

@RestController
@RequestMapping("/api/order")
public class OrdersApiController {
    private final OrderService orderService;

    public OrdersApiController(OrderService orderService) {
        this.orderService = orderService;

    }



    @PostMapping("/submit")
    public void submitOrder(@RequestBody ProductOrderRequestModel model, Principal principal) throws Exception {
        String name = principal.getName();
        //orderService.createOrder(model.getId(), name);
    }
}

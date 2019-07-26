package sapproject.pizzadelivery.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sapproject.pizzadelivery.domain.models.rest.ProductOrderRequestModel;
import sapproject.pizzadelivery.domain.models.service.ProductServiceModel;
import sapproject.pizzadelivery.domain.models.view.OrderViewModel;
import sapproject.pizzadelivery.domain.models.view.ProductAllViewModel;
import sapproject.pizzadelivery.service.OrderService;
import sapproject.pizzadelivery.service.ProductService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    private final ProductService productService;
    private final ModelMapper mapper;
    private final OrderService orderService;

    @Autowired
    public OrdersController(ProductService productService,
                            ModelMapper modelMapper, OrderService orderService){
        this.productService = productService;
        this.mapper = modelMapper;
        this.orderService = orderService;
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, ModelAndView modelAndView){
        ProductServiceModel productServiceModel = productService.findProductById(id);
        ProductAllViewModel viewModel = this.mapper.map(productServiceModel,
                ProductAllViewModel.class);
        modelAndView.addObject("product",viewModel);

        return super.view("order/details", modelAndView);

    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderViewModel> orderViewModels = orderService.findAllOrders()
                .stream()
                .map(o -> mapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", orderViewModels);

        return view("order/all-orders", modelAndView);
    }

    @GetMapping("/all/details/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")

    public ModelAndView allOrderDetails(@PathVariable String id, ModelAndView modelAndView) {
        OrderViewModel orderViewModel = this.mapper.map(this.orderService.findOrderById(id), OrderViewModel.class);
        modelAndView.addObject("order", orderViewModel);

        return super.view("order/order-details", modelAndView);
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")

    public ModelAndView getMyOrders(ModelAndView modelAndView, Principal principal) {
        List<OrderViewModel> orderViewModels = orderService.findOrdersByCustomer(principal.getName())
                .stream()
                .map(o -> mapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", orderViewModels);

        return view("order/all-orders", modelAndView);
    }

    @GetMapping("/my/details/{id}")
    @PreAuthorize("isAuthenticated()")

    public ModelAndView myOrderDetails(@PathVariable String id, ModelAndView modelAndView) {
        OrderViewModel orderViewModel = this.mapper.map(this.orderService.findOrderById(id), OrderViewModel.class);
        modelAndView.addObject("order", orderViewModel);

        return super.view("order/order-details", modelAndView);
    }

    @PostMapping("/all/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView deleteOrder(@PathVariable String id){
        this.orderService.deleteOrder(id);
        return redirect("/orders/all/");
    }





}

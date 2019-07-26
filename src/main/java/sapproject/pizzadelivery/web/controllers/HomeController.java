package sapproject.pizzadelivery.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import sapproject.pizzadelivery.domain.entities.Product;
import sapproject.pizzadelivery.domain.models.view.CategoryAllViewModel;
import sapproject.pizzadelivery.domain.models.view.ProductAllViewModel;
import sapproject.pizzadelivery.service.CategoryService;
import sapproject.pizzadelivery.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return super.view("index");
    }


    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService.findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());



        modelAndView.addObject("categories", categories);

        return super.view("home", modelAndView);
    }




}

package sapproject.pizzadelivery.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sapproject.pizzadelivery.domain.entities.Category;
import sapproject.pizzadelivery.domain.models.binding.ProductAddBindingModel;
import sapproject.pizzadelivery.domain.models.service.CategoryServiceModel;
import sapproject.pizzadelivery.domain.models.service.ProductServiceModel;
import sapproject.pizzadelivery.domain.models.view.ProductAllViewModel;
import sapproject.pizzadelivery.error.ProductNameAlreadyExistsException;
import sapproject.pizzadelivery.error.ProductNotFoundException;
import sapproject.pizzadelivery.service.CategoryService;
import sapproject.pizzadelivery.service.CloudinaryService;
import sapproject.pizzadelivery.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CloudinaryService cloudinaryService, ModelMapper modelMapper, CategoryService categoryService) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView addProduct(){
        return super.view("products/add-product");
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductAddBindingModel model) throws IOException {
        try {
            ProductServiceModel productServiceModel =
                    this.modelMapper.map(model, ProductServiceModel.class);
            productServiceModel.setCategories(
                    this.categoryService.findAllCategories()
                            .stream()
                            .filter(c -> model.getCategories().contains(c.getName()))
                            .collect(Collectors.toList())
            );
            productServiceModel.setImgUrl(this.cloudinaryService.uploadImage(model.getImage()));
            this.productService.addProduct(productServiceModel);

        }catch (ProductNameAlreadyExistsException ex){
            ex = new ProductNameAlreadyExistsException("Product already exists!");
        }
        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView allProducts(ModelAndView modelAndView){


        modelAndView.addObject("products", this.productService.allProducts()
        .stream()
        .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList()));

        return super.view("products/all-products", modelAndView);

    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable String id, ModelAndView modelAndView){
        modelAndView.addObject("model",this.modelMapper.map(this.productService.findProductById(id),
                ProductAllViewModel.class));
        return super.view("products/details", modelAndView);

    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView editDetails(@PathVariable String id, ModelAndView modelAndView){
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(
                CategoryServiceModel::getName).collect(Collectors.toList())
        );
        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);

        modelAndView.addObject("category");
        return super.view("products/edit-product", modelAndView);

    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute ProductAddBindingModel model) {
        this.productService.editProduct(id, this.modelMapper.map(model, ProductServiceModel.class));

        return super.redirect("/products/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView){
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductAddBindingModel model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories().stream().map(
                CategoryServiceModel::getName).collect(Collectors.toList())
        );
        modelAndView.addObject("product", model);
        modelAndView.addObject("productId", id);
        modelAndView.addObject("category");
        return super.view("products/delete-product", modelAndView);

    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView confirmDeleteProduct(@PathVariable String id){
        this.productService.deleteProduct(id);
        return super.redirect("/products/all");
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<ProductAllViewModel> fetchByCategory(@PathVariable String category) {
        if(category.equals("all")) {
            return this.productService.findAllProducts()
                    .stream()
                    .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                    .collect(Collectors.toList());
        }

        return this.productService.findAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductAllViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ModelAndView handleProductNotFound(ProductNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }

    @ExceptionHandler({ProductNameAlreadyExistsException.class})
    public ModelAndView handleProductNameALreadyExist(ProductNameAlreadyExistsException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatusCode());

        return modelAndView;
    }


}

package sapproject.pizzadelivery.web.controllers;

import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sapproject.pizzadelivery.domain.entities.Category;
import sapproject.pizzadelivery.domain.models.binding.CategoryAddBindingModel;
import sapproject.pizzadelivery.domain.models.service.CategoryServiceModel;
import sapproject.pizzadelivery.domain.models.view.CategoryAllViewModel;
import sapproject.pizzadelivery.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;


    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView addCategory(){
        return super.view("/category/add-category");

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView addCategory(@ModelAttribute CategoryAddBindingModel model){
        this.categoryService.addCategory(this.modelMapper.map(model, CategoryServiceModel.class));

        return super.redirect("/categories/all");

    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView allCategories(ModelAndView modelAndView){
        modelAndView.addObject("categories",
                this.categoryService.findAllCategories().stream()
        .map(c -> this.modelMapper.map(c, CategoryAllViewModel.class))
                        .collect(Collectors.toList()));
        return super.view("category/all-categories", modelAndView);

    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView){
        CategoryAllViewModel categoryAllViewModel =this.modelMapper.map(this.categoryService.findCategoryById(id),
                CategoryAllViewModel.class);
        modelAndView.addObject("model" , categoryAllViewModel);

        return super.view("category/edit-category", modelAndView);

    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView confirmEditCategory(@PathVariable String id, @ModelAttribute CategoryAddBindingModel model){
        this.categoryService.editCategory(id, this.modelMapper.map(model, CategoryServiceModel.class));

        return super.redirect("/categories/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView){
        CategoryAllViewModel categoryAllViewModel =this.modelMapper.map(this.categoryService.findCategoryById(id),
                CategoryAllViewModel.class);
        modelAndView.addObject("model" , categoryAllViewModel);

        return super.view("category/delete-category", modelAndView);

    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView confirmDeleteCategory(@PathVariable String id){
        this.categoryService.deleteCategory(id);

        return super.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseBody
    public List<CategoryAllViewModel> fetchCategories(){
        return this.categoryService.findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryAllViewModel.class))
                .collect(Collectors.toList());
    }



}

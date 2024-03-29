package sapproject.pizzadelivery.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sapproject.pizzadelivery.domain.entities.Category;
import sapproject.pizzadelivery.domain.models.service.CategoryServiceModel;
import sapproject.pizzadelivery.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);

        return this.modelMapper.map((this.categoryRepository.saveAndFlush(category)), CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategoryById(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid Id!")
        );
        return this.modelMapper.map(category, CategoryServiceModel.class);

    }

    @Override
    public CategoryServiceModel editCategory(String id, CategoryServiceModel categoryServiceModel) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id!"));

        category.setName(categoryServiceModel.getName());

        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel deleteCategory(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Invalid id!"));

        this.categoryRepository.delete(category);

        return this.modelMapper.map(category, CategoryServiceModel.class);
    }
}

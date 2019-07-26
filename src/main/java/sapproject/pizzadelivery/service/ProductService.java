package sapproject.pizzadelivery.service;

import sapproject.pizzadelivery.domain.models.binding.ProductAddBindingModel;
import sapproject.pizzadelivery.domain.models.service.ProductServiceModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> allProducts();

    ProductServiceModel findProductById(String id);




    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);


    List<ProductServiceModel> findAllByCategory(String category);

    List<ProductServiceModel> findAll();

    void deleteProduct(String id);
}


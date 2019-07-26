package sapproject.pizzadelivery.domain.models.view;

import java.math.BigDecimal;

public class OrderProductViewModel {

    private ProductAllViewModel product;
    private BigDecimal price;

    public OrderProductViewModel() {
    }

    public ProductAllViewModel getProduct() {
        return product;
    }

    public void setProduct(ProductAllViewModel product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
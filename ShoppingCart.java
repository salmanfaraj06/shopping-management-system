package ShoppingManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);

    }


    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Product product : this.products) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    public List<Product> getProducts() {
        return products;
    }


}

package ShoppingManagementSystem;

import java.util.List;

public interface ShoppingManager {

     void addProduct(Product product, List<Product> productList);

     void deleteProduct(String productId, List<Product> productList);

     void printProductList(List<Product> productList);

     void saveToFile(List<Product> productList);

     List<Product> loadFromFile();


}

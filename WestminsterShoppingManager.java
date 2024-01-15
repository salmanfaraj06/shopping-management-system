package ShoppingManagementSystem;

import java.io.*;
import java.util.*;


public class WestminsterShoppingManager implements ShoppingManager {


    @Override
    public void addProduct(Product product, List<Product> productList) {
        if (productList.size() >= 50) {
            System.out.println("Maximum number of products reached. Cannot add more.");
        } else {
            boolean productExists = false;
            for (Product existingProduct : productList) {
                if (existingProduct.getProductId().equals(product.getProductId())) {
                    productExists = true;
                    break;
                }
            }
    
            if (productExists) {
                System.out.println("Product ID already exists. Please enter a different ID.");
            } else {

                productList.add(product);
                System.out.println("Product added successfully.");
            }
        }
    }

    @Override
    public void deleteProduct(String productId, List<Product> productList) {


        for (Product product : productList) {

            if (product.getProductId().equals(productId)) {
                int totalcount = productList.size();
                productList.remove(product);
                System.out.println(product.getProductName()+" deleted from products successfully.");
                System.out.println("Total products available : "+(totalcount-1));
                return;
            }
        }
        System.out.println("Product not found.");
    }

    @Override
    public void printProductList(List<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("No products in the list.");
        } else {
            productList.sort(Comparator.comparing(Product::getProductId));
            System.out.println("Products in the list:");
            System.out.println();
            // Loop through the list and print the details of each product.
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    System.out.println("Electronics:");
                    product.displayInfo();
                } else if (product instanceof Clothing) {
                    System.out.println("Clothing:");
                    product.displayInfo();
                }
            }
        }
    }

    @Override
    public void saveToFile(List<Product> productList) {
        try {
            // FileWriter object with the filename, it will automatically create a new file or overwrite an existing file with the same name.
            FileWriter writer = new FileWriter("products.txt");
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    writer.write("Electronics:");
                } else if (product instanceof Clothing) {
                    writer.write("Clothing:");
                }
                writer.write("\n");
                writer.write("Product ID: " + product.getProductId());
                writer.write("\n");
                writer.write("Product Name: " + product.getProductName());
                writer.write("\n");
                writer.write("Available Items: " + product.getAvailableItems());
                writer.write("\n");
                writer.write("Price: " + product.getPrice());
                writer.write("\n");
                if (product instanceof Electronics electronics) {
                    writer.write("Brand: " + electronics.getBrand());
                    writer.write("\n");
                    writer.write("Warranty Period: " + electronics.getWarrantyPeriod());
                    writer.write("\n");
                } else if (product instanceof Clothing clothing) {
                    writer.write("Size: " + clothing.getSize().toUpperCase());
                    writer.write("\n");
                    writer.write("Color: " + clothing.getColor());
                }
                writer.write("\n");

            }
            writer.close();
            System.out.println("Products saved to file.");

        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
        }

    }
    public List<Product> loadFromFile() {
        List<Product> loadedProducts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.equals("Electronics:")) {
                    String id = getValueFromLine(reader.readLine());
                    String name = getValueFromLine(reader.readLine());
                    int availableItems = Integer.parseInt(getValueFromLine(reader.readLine()));
                    double price = Double.parseDouble(getValueFromLine(reader.readLine()));
                    String brand = getValueFromLine(reader.readLine());
                    int warranty = Integer.parseInt(getValueFromLine(reader.readLine()));
                    loadedProducts.add(new Electronics(id, name, availableItems, price, brand, warranty));


                } else if (line.equals("Clothing:")) {
                    String id = getValueFromLine(reader.readLine());
                    String name = getValueFromLine(reader.readLine());
                    int availableItems = Integer.parseInt(getValueFromLine(reader.readLine()));
                    double price = Double.parseDouble(getValueFromLine(reader.readLine()));
                    String size = getValueFromLine(reader.readLine());
                    String color = getValueFromLine(reader.readLine());
                    loadedProducts.add(new Clothing(id, name, availableItems, price, size, color));
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred trying to load to file ");
        }
        return loadedProducts;
    }


    private String getValueFromLine(String line) {
        return line.split(":")[1].trim();
    }


}

package ShoppingManagementSystem;

public abstract class Product {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public abstract void displayInfo();

    public String getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }


    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }


    public double getPrice() {
        return price;
    }
}

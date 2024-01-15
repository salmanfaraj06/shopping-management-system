package ShoppingManagementSystem;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WestminsterShoppingManager manager = new WestminsterShoppingManager();
        List<Product> productList = manager.loadFromFile();


        while (true) {
            System.out.println();
            System.out.println("1. Add product");
            System.out.println("2. Delete product");
            System.out.println("3. Print products");
            System.out.println("4. Save products");
            System.out.println("5. Login to shop");
            System.out.println("6. Exit\n");
            System.out.print("Enter your choice: ");
            try {

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add product

                        System.out.print("Enter product type (1 for Clothing, 2 for Electronics): ");
                        int type = scanner.nextInt();

                        System.out.print("Enter product ID: ");
                        while (!scanner.hasNext()){
                            System.out.println("Invalid input. Please enter a valid product type: ");
                            scanner.next();
                        }
                        String id = scanner.next();

                        System.out.print("Enter product name: ");
                        while (!scanner.hasNext("[a-zA-Z]+")) {
                            System.out.println("Invalid input. Please enter a valid product name: ");
                            scanner.next();
                        }
                        String name = scanner.next();

                        System.out.print("Enter number of available items: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number of available items: ");
                            scanner.next();
                        }
                        int availableItems = scanner.nextInt();

                        System.out.print("Enter product price: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.println("Invalid input. Please enter a valid product price: ");
                            scanner.next();
                        }
                        double price = scanner.nextDouble();

                        if (type == 1) {
                            System.out.print("Enter clothing size: XS , S , M , L , XL , XXL ");
                            String size = scanner.next();
                            String[] validSizes = {"XS", "S", "M", "L", "XL", "XXL"};
                            boolean isValidSize = Arrays.asList(validSizes).contains(size.toUpperCase());
                            while (!isValidSize) {
                                System.out.println("Invalid size. Please enter a valid size (XS, S, M, L, XL, XXL): ");
                                size = scanner.next();
                                isValidSize = Arrays.asList(validSizes).contains(size.toUpperCase());
                            }

                            System.out.print("Enter clothing color: ");
                            while (!scanner.hasNext("[a-zA-Z]+")) {
                                System.out.println("Invalid input. Please enter a valid clothing color: ");
                                scanner.next();
                            }
                            String color = scanner.next();

                            Clothing clothing = new Clothing(id, name, availableItems, price, size, color);
                            manager.addProduct(clothing, productList);
                        } else if (type == 2) {
                            System.out.print("Enter electronics brand: ");
                            String brand = scanner.next();
                            System.out.print("Enter warranty period: ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Invalid input. Please enter a valid warranty period: ");
                                scanner.next();
                            }
                            int warrantyPeriod = scanner.nextInt();
                            Electronics electronics = new Electronics(id, name, availableItems, price, brand, warrantyPeriod);
                            manager.addProduct(electronics, productList);
                        } else {
                            System.out.println("Invalid product type. Please try again.");

                        }
                        break;


                    case 2:
                        // Delete product
                        System.out.print("Enter product ID: ");
                        id = scanner.next();
                        manager.deleteProduct(id, productList);
                        break;
                    case 3:
                        // Print products
                        manager.printProductList(productList);
                        break;
                    case 4:
                        // Save products
                        manager.saveToFile(productList);
                        break;
                    case 5:
                        // Login to shop
                        AuthGUI authGUI = new AuthGUI();
                        authGUI.showAuth();
                        break;

                    case 6:
                        // Exit
                        System.out.println("Thank you for using Westminster Shopping Manager.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
   }
}

package ShoppingManagementSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class ShopGUI extends JFrame {
    private final JComboBox<String> categoryComboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextArea selectedProductDetails;
    private WestminsterShoppingManager manager;
    private ShoppingCart shoppingCart;


    public ShopGUI() {
        manager = new WestminsterShoppingManager();
        List<Product> productList = manager.loadFromFile();


        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("  Select Product Category  ");
        String[] categories = {"All", "Clothing", "Electronics"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            updateProductTable(selectedCategory);
        });

        JButton shoppingCartButton = new JButton("Shopping Cart");
        topPanel.add(categoryComboBox, BorderLayout.CENTER);
        topPanel.add(shoppingCartButton, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);


        tableModel = new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Price (£)", "Info"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This causes all cells to be non editable https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
                return false;
            }
        };
        productTable = new JTable(tableModel);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = (Product) tableModel.getValueAt(selectedRow, tableModel.getColumnCount() - 1);
                displayProductDetails(selectedProduct);
            }

        });

        int rowHeight = 25;
        int columnWidth = 70;

        productTable.setRowHeight(rowHeight);
        for (int column = 0; column < productTable.getColumnCount(); column++) {
            productTable.getColumnModel().getColumn(column).setMinWidth(columnWidth);
        }

        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20,20,20));
        add(scrollPane, BorderLayout.CENTER);

        // Display section at the bottom with product details and add to cart button
        JPanel displayPanel = new JPanel(new BorderLayout());
        selectedProductDetails = new JTextArea();
        selectedProductDetails.setEditable(false);
        selectedProductDetails.setPreferredSize(new Dimension(200, 250));

        shoppingCart = new ShoppingCart();
        JButton sortButton = new JButton("Sort Table");
        sortButton.addActionListener(e -> {
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
            productTable.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
            sorter.sort();
            //https://stackoverflow.com/questions/28823670/how-to-sort-jtable-in-shortest-way
        });

        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                Product selectedProduct = (Product) tableModel.getValueAt(selectedRow, tableModel.getColumnCount() - 1);
                if (selectedProduct.getAvailableItems() <= 0) {
                    JOptionPane.showMessageDialog(this, "This product is out of stock.", "Out of Stock", JOptionPane.ERROR_MESSAGE);
                } else {
                    shoppingCart.addProduct(selectedProduct);
                    selectedProduct.setAvailableItems(selectedProduct.getAvailableItems() - 1); // Decrease the available items

                    for (Product product : productList) {
                        if (product.getProductId().equals(selectedProduct.getProductId())) {
                            product.setAvailableItems(product.getAvailableItems() - 1); // Decrease the available items
                            break;
                        }
                    }
                    manager.saveToFile(productList);
                    manager.loadFromFile();
                    JOptionPane.showMessageDialog(this, "Product added to the shopping cart.");

                }
            } else {
                JOptionPane.showMessageDialog(this, "No product selected.");
            }
        });




        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                //https://stackoverflow.com/questions/6644922/jtable-cell-renderer
                Product product = (Product) table.getValueAt(row, table.getColumnCount() - 1);

                if (product.getAvailableItems() <= 3) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }

                return c;
            }
        });

        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart);
        shoppingCartButton.addActionListener(e -> shoppingCartGUI.showShoppingCart());
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(addToCartButton);
        buttonsPanel.add(sortButton);

        displayPanel.add(selectedProductDetails, BorderLayout.CENTER);
        displayPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(displayPanel, BorderLayout.SOUTH);


    }



    private void displayProductDetails(Product product) {
        selectedProductDetails.setText("\n     Selected Product - Details\n\n");
        selectedProductDetails.append("     Product ID: " + product.getProductId() + "\n\n");
        selectedProductDetails.append("     Product Name: " + product.getProductName() + "\n\n");
        selectedProductDetails.append("     Price: " + product.getPrice() + "\n\n");

        if (product instanceof Clothing clothing) {
            selectedProductDetails.append("     Size: " + clothing.getSize() + "\n\n");
            selectedProductDetails.append("     Color: " + clothing.getColor() + "\n\n");
        } else if (product instanceof Electronics electronics) {
            selectedProductDetails.append("     Brand: " + electronics.getBrand() + "\n\n");
            selectedProductDetails.append("     Warranty Period: " + electronics.getWarrantyPeriod() + "\n\n");
        }
    }


    private void updateProductTable(String category) {
        tableModel.setRowCount(0); // Clear the table
        List<Product> productList = manager.loadFromFile();
        for (Product product : productList) {
            String productCategory = product instanceof Clothing ? "Clothing" : "Electronics";
            if (category.equals("All") || productCategory.equals(category)) {
                tableModel.addRow(new Object[]{product.getProductId(), product.getProductName(),
                        productCategory, product.getPrice(), product});
            }
        }
    }


    public static void main(String[] args) {

        ShopGUI frame = new ShopGUI();
        frame.setTitle("Shopping Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}




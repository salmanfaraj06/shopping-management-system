package ShoppingManagementSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShoppingCartGUI extends JFrame {
    private DefaultTableModel shoppingCartTableModel;
    private JLabel totalLabel;
    private JLabel categoryDiscountLabel;

    private JLabel finalTotalLabel;
    private ShoppingCart shoppingCart;
    private JTable shoppingCartTable;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        shoppingCartTableModel = new DefaultTableModel(new Object[]{"Product", "Quantity", "Price"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                                      //https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
                return false;
            }

        };



        //  shopping cart GUI
        JPanel shoppingCartPanel = new JPanel(new BorderLayout());
        shoppingCartPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane shoppingCartScrollPane = new JScrollPane(shoppingCartTable);
        totalLabel = new JLabel();
        categoryDiscountLabel = new JLabel();
        finalTotalLabel = new JLabel();
        shoppingCartPanel.add(shoppingCartScrollPane, BorderLayout.CENTER);
        JPanel totalPanel = new JPanel(new GridLayout(0, 1));
        totalPanel.add(totalLabel);
        totalPanel.add(categoryDiscountLabel);
        totalPanel.add(finalTotalLabel);
        shoppingCartPanel.add(totalPanel, BorderLayout.SOUTH);
        add(shoppingCartPanel);

    }

    public void showShoppingCart() {

        shoppingCartTableModel.setRowCount(0);

        // Add products to the shopping cart table
        String productinfo = null;
        List<Product> products = shoppingCart.getProducts();
        for (Product product : products) {
            String  category = product instanceof Clothing ? "Clothing" : "Electronics";
            if (category == "Clothing"){
                Clothing clothing = (Clothing) product;
                 productinfo = product.getProductId()+"  "+product.getProductName()+" \n "+clothing.getSize()+"  "+clothing.getColor();
            } else if (category == "Electronics") {
                Electronics electronics = (Electronics) product;
                 productinfo = product.getProductId()+"  "+product.getProductName()+" \n "+electronics.getBrand()+"  "+electronics.getWarrantyPeriod();
            }



            int quantity = 1;
            double price = product.getPrice();

            shoppingCartTableModel.addRow(new Object[]{ productinfo , quantity, price});
        }
        int rowHeight = 50;
        int columnWidth = 100;

        shoppingCartTable.setRowHeight(rowHeight);
        for (int column = 0; column < shoppingCartTable.getColumnCount(); column++) {
            shoppingCartTable.getColumnModel().getColumn(column).setMinWidth(columnWidth);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);


        for (int column = 0; column < shoppingCartTable.getColumnCount(); column++) {
            shoppingCartTable.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }


        double totalCost = shoppingCart.calculateTotalCost();
        totalLabel.setText("Total       " + totalCost+" £ ");


        double categoryDiscount = 0.0;
        int electronicsCount = 0;
        int clothingCount = 0;


        for (Product product : products) {
            if (product instanceof Electronics) {
                electronicsCount++;
            } else if (product instanceof Clothing) {
                clothingCount++;
            }

        }

        if (electronicsCount >= 3 || clothingCount >= 3 ) {
            categoryDiscount = totalCost * 0.2;
            categoryDiscountLabel.setText("Category Discount (20%)      " + categoryDiscount+" £");
        } else {
            categoryDiscountLabel.setText("");
        }

        double finalTotal = totalCost - (categoryDiscount);
         finalTotalLabel.setText("Final Total       " + finalTotal+" £");




        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 350);
        setVisible(true);
    }
}

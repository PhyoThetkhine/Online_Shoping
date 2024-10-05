package mini_online_shop.model;


public class CartItem {
    private ProductBean product;
    private int quantity;

    // Getters and setters
    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
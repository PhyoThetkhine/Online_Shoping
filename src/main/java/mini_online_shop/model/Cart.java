package mini_online_shop.model;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    // Key: Product ID, Value: CartItem
    private Map<Integer, CartItem> items = new HashMap<>();

    /**
     * Adds a product to the cart. If the product already exists, increments the quantity.
     *
     * @param product The product to add.
     */
    public void addProduct(ProductBean product) {
        int productId = product.getId();
        if (items.containsKey(productId)) {
            CartItem item = items.get(productId);
            item.setQuantity(item.getQuantity() + 1);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(1);
            items.put(productId, item);
        }
    }

    /**
     * Removes a product from the cart.
     *
     * @param productId The ID of the product to remove.
     */
    public void removeProduct(int productId) {
        items.remove(productId);
    }

    /**
     * Clears all items from the cart.
     */
    public void clearCart() {
        items.clear();
    }

    // Getters and setters
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    /**
     * Calculates the total number of items in the cart.
     *
     * @return Total item count.
     */
    public int getTotalItemCount() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }
    /**
     * Calculates the total price of all items in the cart.
     *
     * @return Total price.
     */
    public double getTotalPrice() {
        return items.values().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}

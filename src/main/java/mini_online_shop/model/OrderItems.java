package mini_online_shop.model;

import lombok.Data;

@Data
public class OrderItems {
	private int id;
	private double qty;
	private double price_at_time;
	private order orde_id;
	private ProductBean product_id;
	

}

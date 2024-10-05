package mini_online_shop.model;

import java.util.Date;

import lombok.Data;
@Data
public class order {
	private Integer id;
	 private Date date;
	 private double totalAmount;
	 private String status;
	  private User userId;

}

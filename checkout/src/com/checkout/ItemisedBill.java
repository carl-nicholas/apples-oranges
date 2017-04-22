package com.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemisedBill {

	BigDecimal cartCost = new BigDecimal(0);

	Map<Product, Integer> itemCount = new HashMap<Product, Integer>();

	/**
	 * @return the cartCost
	 */
	public BigDecimal getCartCost() {
		return cartCost;
	}

	/**
	 * @param cartCost
	 *            the cartCost to set
	 */
	public void setCartCost(BigDecimal cartCost) {
		this.cartCost = cartCost;
	}

	/**
	 * @return the itemCount
	 */
	public Map<Product, Integer> getItemCount() {
		return itemCount;
	}

	/**
	 * @param itemCount
	 *            the itemCount to set
	 */
	public void setItemCount(Map<Product, Integer> itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * 
	 * @param product
	 */
	public void addItem(Product product) {
		if (itemCount.containsKey(product)) {
			itemCount.put(product, itemCount.get(product) + 1);
		} else {
			itemCount.put(product, 1);
		}
	}
}
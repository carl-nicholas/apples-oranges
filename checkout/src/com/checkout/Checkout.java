package com.checkout;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Checkout {

	private static final Map<Product, BigDecimal> products;
	static {
		products = new HashMap<Product, BigDecimal>();
		products.put(Product.Apple, new BigDecimal(0.6).setScale(2, BigDecimal.ROUND_HALF_UP));
		products.put(Product.Orange, new BigDecimal(0.25).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	private static final Map<Product, Integer> discounts;
	static {
		discounts = new HashMap<Product, Integer>();
		discounts.put(Product.Apple, 2);
		discounts.put(Product.Orange, 3);
	}

	/**
	 * Returns the cost of an individual item.
	 * 
	 * @param product
	 *            - The item
	 * @return The cost of the item
	 * @throws NoPriceForProductException
	 */
	private static BigDecimal getItemCost(Product product) throws NoPriceForProductException {
		BigDecimal retVal = null;
		if (products.containsKey(product)) {
			retVal = products.get(product);
		} else {
			throw new NoPriceForProductException("No price found for " + product.name());
		}
		return retVal;
	}

	private static ItemisedBill getCartCost(Product[] cart) throws NoPriceForProductException {
		ItemisedBill itemisedBill = new ItemisedBill();
		if ((cart != null) && (cart.length > 0)) {
			for (Product item : cart) {
				BigDecimal itemCost = getItemCost(item);
				itemisedBill.setCartCost(itemisedBill.getCartCost().add(itemCost));
				itemisedBill.addItem(item);
				System.out.println(item.name() + " at £" + itemCost);
			}
		}
		return itemisedBill;
	}

	private static void applyOffers(ItemisedBill itemisedBill) throws NoPriceForProductException {
		if ((itemisedBill != null) && (itemisedBill.getItemCount() != null)
				&& (itemisedBill.getItemCount().size() > 0)) {
			Iterator<Entry<Product, Integer>> iCount = itemisedBill.getItemCount().entrySet().iterator();
			BigDecimal discountCost = new BigDecimal(0);
			while (iCount.hasNext()) {
				Map.Entry itemCount = (Map.Entry) iCount.next();
				if (discounts.containsKey(itemCount.getKey())) {
					Integer offerThreshold = discounts.get(itemCount.getKey());
					int discountCount = ((Integer) itemCount.getValue()) / offerThreshold;

					BigDecimal itemDiscount = getItemCost((Product) itemCount.getKey())
							.multiply(new BigDecimal(discountCount));
					if (itemDiscount.compareTo(new BigDecimal(0)) > 0) {
						System.out.println(offerThreshold + " for " + (offerThreshold - 1) + " on " + itemCount.getKey()
								+ " saves you £" + itemDiscount);
						discountCost = itemDiscount.add(discountCost);
					}
				}
			}
			itemisedBill.setCartCost(itemisedBill.getCartCost().subtract(discountCost));
		}
	}

	public static void main(String[] args) {
		Product[] myCart = { Product.Apple, Product.Apple, Product.Orange, Product.Orange, Product.Apple,
				Product.Orange, Product.Apple, Product.Orange, Product.Apple, Product.Orange, Product.Orange,
				Product.Orange };
		try {
			ItemisedBill result = getCartCost(myCart);
			applyOffers(result);
			System.out.println("That will be £" + result.getCartCost() + " please.");
		} catch (NoPriceForProductException e) {
			System.out.println(e.getMessage());
		}
	}

}

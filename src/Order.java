import java.util.ArrayList;

public class Order {
	private int ID;
	private double totalAmount;
	private ArrayList<Item> items;
	private ArrayList<Integer> quantities;

	public Order() {
		totalAmount = 0;
		items = new ArrayList<>();
		quantities = new ArrayList<>();
	}

	public void addItem(Item item, int quantity) {
		for (int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) {
				quantities.set(i, quantities.get(i) + quantity);
				return;
			}
		}
		items.add(item);
		quantities.add(quantity);
	}
	
	public void clear() {
		items = new ArrayList<>();
		quantities = new ArrayList<>();
	}
	
	public String getInfo() {
		String info = String.format("%-8s%15s%15s\n", "Item", "Price", "Quantity");
		for (int i = 0; i < items.size(); i++) {
			info += String.format("%-8s%15.0f%15d\n", items.get(i).getName(), items.get(i).getPrice(),
					quantities.get(i));
			totalAmount += items.get(i).getPrice() * quantities.get(i);
		}
		info += "-------------------------\n";
		info += String.format("The total amount:%.2f", totalAmount);
		return info;
	}
	
	public String checkout() {
		totalAmount = 0;
		return getInfo();
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item findItem(String itemName) {
		for (Item item : items) {
			if (item.getName().equals(itemName)) {
				return item;
			}
		}
		return null;
	}
}

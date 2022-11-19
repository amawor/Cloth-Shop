import java.util.ArrayList;

public class ItemManager {
	private ArrayList<Item> items;

	public ItemManager() {
		items = new ArrayList<>();
	}

	public void addNewItem(String name, int price) {
		items.add(new Item(name, price));
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

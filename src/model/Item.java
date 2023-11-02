package model;

public class Item {
	private String id = "";

	public Item(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}

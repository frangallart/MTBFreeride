package com.norriors.java.mtbfreeride.Controllers;

/**
 * Classe DrawerItems
 */
public class DrawerItems {

	String ItemName;
	int imgResID;
	String title;

	public DrawerItems(String itemName, int imgResID) {
		ItemName = itemName;
		this.imgResID = imgResID;
	}

	public DrawerItems(String title) {
		this(null, 0);
		this.title = title;
	}

    public DrawerItems(int imgResID) {
        this(null, 0);
        this.imgResID = imgResID;
    }

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public int getImgResID() {
		return imgResID;
	}

	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

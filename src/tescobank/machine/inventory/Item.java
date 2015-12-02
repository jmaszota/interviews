package tescobank.machine.inventory;

/**
 * Created by jacek.maszota on 27.10.2015.
 */
public class Item {

    private ItemType itemType;
    private float price;
    private int count;

    public Item(ItemType itemType, float price, int count) {
        this.itemType = itemType;
        this.price = price;
        this.count = count;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

import java.util.HashMap;
import java.util.Map;

public class Room {
    private String description;
    private Map<String, Room> exits;
    private Map<String, Item> items;

    public Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new HashMap<>();
    }

    public void setExit(String direction, Room room) {
        exits.put(direction, room);
    }

    public Room getConnectedRoom(String direction) {
        return exits.get(direction);
    }

    public void addItem(Item item) {
        items.put(item.getName(), item);
    }

    public Item get(String itemName) {
        return items.get(itemName);
    }

    public void remove(Item item) {
        items.remove(item.getName());
    }

    public String describe() {
        return description + "\nExits: " + exits.keySet() + "\nItems: " + items.keySet();
    }
}

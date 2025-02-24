import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class MUDController {
    private String currentRoom;
    private List<String> inventory;
    private boolean running;
    private List<String> roomItems;
    private String previousRoom;

    public MUDController() {
        this.currentRoom = "You are at the entrance of a mysterious cave.";
        this.inventory = new ArrayList<>();
        this.roomItems = new ArrayList<>(Arrays.asList("Torch", "Shield"));
        this.previousRoom = null;
    }

    public void runGameLoop() {
        running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the MUD Game!");
        System.out.println("Available commands: look, move <direction>, pick up <item>, inventory, help, exit");
        System.out.println("Type 'help' for more details.");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            handleInput(input);
        }
        System.out.println("Game over. See you next time!");
    }

    private void handleInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1] : null;

        switch (command) {
            case "look":
                lookAround();
                break;
            case "move":
                if (argument != null) {
                    move(argument.toLowerCase());
                } else {
                    System.out.println("Move where?");
                }
                break;
            case "pick":
                if (argument != null && argument.startsWith("up ")) {
                    pickUp(argument.substring(3));
                } else {
                    System.out.println("Pick up what?");
                }
                break;
            case "inventory":
                checkInventory();
                break;
            case "help":
                showHelp();
                break;
            case "exit":
                running = false;
                break;
            default:
                System.out.println("Unrecognized command.");
        }
    }

    private void lookAround() {
        System.out.println(currentRoom);
        if (!roomItems.isEmpty()) {
            System.out.println("Objects nearby: " + String.join(", ", roomItems));
        } else {
            System.out.println("There are no items here.");
        }
    }

    private void move(String direction) {
        previousRoom = currentRoom;
        roomItems.clear();

        switch (direction) {
            case "north":
                currentRoom = "A dark tunnel leading deeper into the cave.";
                roomItems.add("Key");
                break;
            case "south":
                currentRoom = "You are at the entrance of a mysterious cave.";
                roomItems.add("Torch");
                roomItems.add("Shield");
                break;
            case "east":
                currentRoom = "A hidden chamber filled with ancient relics.";
                roomItems.add("Golden Idol");
                break;
            case "west":
                currentRoom = "A narrow passage with strange carvings on the walls.";
                roomItems.add("Map");
                break;
            default:
                System.out.println("That path is blocked.");
                return;
        }
        System.out.println("You move " + direction + ".");
        lookAround();
    }

    private void pickUp(String itemName) {
        for (String item : new ArrayList<>(roomItems)) {
            if (item.equalsIgnoreCase(itemName)) {
                roomItems.remove(item);
                inventory.add(item);
                System.out.println("You picked up " + item + ".");
                return;
            }
        }
        System.out.println("No such item found.");
    }

    private void checkInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are carrying nothing.");
        } else {
            System.out.println("You are carrying: " + String.join(", ", inventory));
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("look - Describes the current room and its objects");
        System.out.println("move <direction> - Moves in a specified direction (north, south, east, west)");
        System.out.println("pick up <item> - Picks up an item from the ground");
        System.out.println("inventory - Lists the items you are carrying");
        System.out.println("help - Displays this command list");
        System.out.println("exit - Ends the game");
    }

    public static void main(String[] args) {
        new MUDController().runGameLoop();
    }
}

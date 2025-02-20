import java.util.Scanner;

public class MUDController {
    private Player player;
    private boolean running;
    private Scanner scanner;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
        this.scanner = new Scanner(System.in);
    }

    public void runGameLoop() {
        System.out.println("Welcome to the MUD game! Type 'help' for commands.");
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            handleInput(input);
        }
    }

    private void handleInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String argument = "";
        if (parts.length > 1) {
            argument = parts[1];
        }

        switch (command) {
            case "look":
                lookAround();
                break;
            case "move":
                move(argument);
                break;
            case "pick":
                if (argument.startsWith("up ")) {
                    pickUp(argument.substring(3));
                } else {
                    System.out.println("Invalid command. Did you mean 'pick up <item>'?");
                }
                break;
            case "inventory":
                checkInventory();
                break;
            case "help":
                showHelp();
                break;
            case "exit":
            case "quit":
                running = false;
                System.out.println("Exiting game. Goodbye!");
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
        }
    }

    private void lookAround() {
        Room room = player.getCurrentRoom();
        System.out.println(room.describe());
    }

    private void move(String direction) {
        if (direction.isEmpty()) {
            System.out.println("Move where? Usage: move <direction>");
            return;
        }

        Room nextRoom = player.getCurrentRoom().getConnectedRoom(direction);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("You move " + direction + ".");
            lookAround();
        } else {
            System.out.println("You can't go that way!");
        }
    }

    private void pickUp(String itemName) {
        if (itemName.isEmpty()) {
            System.out.println("Pick up what?");
            return;
        }

        Room room = player.getCurrentRoom();
        Item item = room.get(itemName);
        if (item != null) {
            room.remove(item);
            player.addItem(item);
            System.out.println("You pick up the " + itemName + ".");
        } else {
            System.out.println("No item named '" + itemName + "' here!");
        }
    }

    private void checkInventory() {
        System.out.println("You are carrying: " + player.getInventoryString());
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("look - Describes the current room");
        System.out.println("move <direction> - Moves in a specified direction");
        System.out.println("pick up <item> - Picks up an item from the ground");
        System.out.println("inventory - Shows your inventory");
        System.out.println("exit - Exits the game");
    }
}

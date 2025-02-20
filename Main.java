public class Main {
    public static void main(String[] args) {
        Room startRoom = new Room("You are in a small, dimly lit room.");
        Room hallway = new Room("You are in a long, narrow hallway.");
        Room treasureRoom = new Room("You have entered a room filled with gold and jewels!");

        startRoom.setExit("north", hallway);
        hallway.setExit("south", startRoom);
        hallway.setExit("east", treasureRoom);
        treasureRoom.setExit("west", hallway);

        Item key = new Item("golden key");
        startRoom.addItem(key);

        Player player = new Player(startRoom);
        MUDController game = new MUDController(player);
        game.runGameLoop();
    }
}
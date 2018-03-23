import java.util.Stack;

public class Player {
    private Room currentRoom;
    private Stack<Room> lastRoom;
    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        lastRoom = new Stack<>();
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRoom.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }
    
    /** 
     * Va a la anterior sala, si la hay.
     */
    public void goLastRoom() 
    {
        if(!lastRoom.empty()) {
            currentRoom = lastRoom.pop();
            printLocationInfo();
        }
    } 
    
    /**
     * Muestra por pantalla la informacion de la sala 
     * actual y las salidas posibles.
     */
    public void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Informa de que el jugador ya ha comido.
     */
    public void comer() {
        System.out.println("You have eaten now and you are not hungry anymore");
    }
    
    /**
     * Devuelve la sala actual.
     * @return Devuelve la sala actual.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
}
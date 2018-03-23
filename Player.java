import java.util.Stack;
import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Stack<Room> lastRoom;
    private ArrayList<Item> listaDeObjetosDelJugador;
    
    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        lastRoom = new Stack<>();
        listaDeObjetosDelJugador = new ArrayList<>();
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
    
    /**
     * Coge el objeto que el jugador desee en la sala actual 
     * a traves del ID que tenga el objeto.
     * @param command El comando que va a ser procesado.
     */
    public void coger(Command command) {
        if(command.hasSecondWord()) {
            String segundaPalabra = command.getSecondWord();
            if(currentRoom.getObjeto(segundaPalabra) != null) {  
                if(currentRoom.getObjeto(segundaPalabra).puedeSerCogidoElObjeto()) {
                    listaDeObjetosDelJugador.add(currentRoom.getObjeto(segundaPalabra));
                    currentRoom.removeObjeto(segundaPalabra);
                }
                else {
                    System.out.println("This item can't be picked up!");
                }
            }
            else {
                System.out.println("Choose the ID of one item!");
            }
        }
        else {
            System.out.println("Take what?");
        }
    }
    
    /**
     * Muestra por pantalla las caracteristicas de 
     * todos los objetos que tiene el jugador actualmente.
     */
    public void mostrarObjetos() {
        int pesoTotalDeTodosLosObjetosQueTieneElJugador = 0;
        if(!listaDeObjetosDelJugador.isEmpty()) {
            for(Item objeto : listaDeObjetosDelJugador) {
                pesoTotalDeTodosLosObjetosQueTieneElJugador += objeto.getPeso();
                System.out.println(objeto.getCaracteristicas());
            }
            System.out.println("Total weight of all items: " + pesoTotalDeTodosLosObjetosQueTieneElJugador + " kg");
        }
        else {
            System.out.println("You don't have any item yet!");
        }
    }
}
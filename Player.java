import java.util.Stack;
import java.util.ArrayList;

/**
 * La clase Player representa los jugadores que pueden 
 * desplazarse por las salas del juego.
 * @author Javier de Cea Dominguez
 * @version 2018.03.23
 */
public class Player {
    private Room currentRoom;
    private Stack<Room> lastRoom;
    private ArrayList<Item> listaDeObjetosDelJugador;
    private int pesoMaximoQuePuedeSoportarElJugador;
    private int pesoTotalDeTodosLosObjetosQueTieneElJugador;
    
    /**
     * Constructor para objetos de la clase Player.
     * @param currentRoom la sala actual en la que se encuentra el jugador.
     */
    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        lastRoom = new Stack<>();
        listaDeObjetosDelJugador = new ArrayList<>();
        pesoMaximoQuePuedeSoportarElJugador = 35;
        pesoTotalDeTodosLosObjetosQueTieneElJugador = 0;
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
            Item objeto = currentRoom.getObjeto(command.getSecondWord());
            if(objeto != null) {  
                if(objeto.puedeSerCogidoElObjeto()) {
                    pesoTotalDeTodosLosObjetosQueTieneElJugador += objeto.getPeso();
                    if(pesoTotalDeTodosLosObjetosQueTieneElJugador <= pesoMaximoQuePuedeSoportarElJugador) {
                        listaDeObjetosDelJugador.add(objeto);
                        currentRoom.removeObjeto(objeto.getID());
                    }
                    else {
                        pesoTotalDeTodosLosObjetosQueTieneElJugador -= objeto.getPeso();
                        System.out.println("This item is too weight for you!");
                    }
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
        if(!listaDeObjetosDelJugador.isEmpty()) {
            for(Item objeto : listaDeObjetosDelJugador) {
                System.out.println(objeto.getCaracteristicas());
            }
            System.out.println("Total weight of all items: " + pesoTotalDeTodosLosObjetosQueTieneElJugador + " kg.");
        }
        else {
            System.out.println("You don't have any item yet!");
        }
    }
    
    /**
     * Tira el objeto que el jugador desee en la sala actual 
     * a traves del ID que tenga el objeto.
     * @param command El comando que va a ser procesado.
     */
    public void tirar(Command command) {
        if(command.hasSecondWord()) {
            Item objeto = getObjeto(command.getSecondWord());
            if(objeto != null) {  
                currentRoom.addObjeto2(objeto);
                pesoTotalDeTodosLosObjetosQueTieneElJugador -= objeto.getPeso();
                listaDeObjetosDelJugador.remove(objeto);
            }
            else {
                System.out.println("Choose the ID of one item that you have!");
            }
        }
        else {
            System.out.println("Drop what?");
        }
    }
    
    /**
     * Devuelve el objeto que tiene el jugador a traves de su ID.
     * @param id El ID del objeto del jugador a devolver.
     * @return Devuelve el objeto del jugador a traves de su ID.
     */
    private Item getObjeto(String id) {
        Item objeto = null;
        for(int contador = 0; contador < listaDeObjetosDelJugador.size() && objeto == null; contador++) {
            if(listaDeObjetosDelJugador.get(contador).getID().equals(id)) {
                objeto = listaDeObjetosDelJugador.get(contador);
            }
        }
        return objeto;
    }
    
    /**
     * El jugador bebe la bebida energetica que lleve
     * en su mochila a traves de la ID del objeto y despues 
     * la tira.
     * @param command El comando que va a ser procesado.
     */
    public void beber(Command command) {
        if(command.hasSecondWord()) {
            Item objeto = getObjeto(command.getSecondWord());
            if(objeto != null) {  
                if(objeto.getID().equals("bebida")) {
                    pesoMaximoQuePuedeSoportarElJugador += 15;
                    pesoTotalDeTodosLosObjetosQueTieneElJugador -= objeto.getPeso();
                    listaDeObjetosDelJugador.remove(objeto);
                    System.out.println("Wow you feel amazing right now! You can take items for a total weight of " + pesoMaximoQuePuedeSoportarElJugador + " kg!");
                }
                else {
                    System.out.println("You can't drink that item!");
                }
            }
            else {
                System.out.println("Choose the ID of one item that you can drink!");
            }
        }
        else {
            System.out.println("Drink what?");
        }
    }
}
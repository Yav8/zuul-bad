import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;
    private ArrayList<Item> listaDeObjetos;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<>();
        listaDeObjetos = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor) 
    {
        salidas.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Devuelve la sala asociada a la cadena que contiene la direccion que 
     * el usuario introduzca por parametro o "null" si no existe salida.
     * @param direccion El texto que contiene la direccion que podria tomar 
     * la sala (direcciones posibles: "north", "east", "south", "west", "southeast", 
     * "northwest").
     * @return La sala con la direccion de la salida correspondiente o "null" si no 
     * existe salida.
     */
    public Room getExit(String direccion) {                
        return salidas.get(direccion);
    }
    
    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString() {
        String salidasExistentes = "Exits:";
        for(String salida : salidas.keySet()) {
            salidasExistentes += " " + salida;
        }
        return salidasExistentes;
    }
    
    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription() {
        String aDevolver = "You are " + description + "\n";
        if(listaDeObjetos.size() > 0) {
            for(Item objeto : listaDeObjetos) {
                aDevolver += objeto.getCaracteristicas() + ".\n";
            }
        }
        aDevolver += getExitString();
        return aDevolver;
    }
    
    /**
     * Añade objetos a la sala.
     */
    public void addObjeto(String descripcionObjeto, int peso) {
        listaDeObjetos.add(new Item(descripcionObjeto, peso));
    }
}

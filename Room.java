import java.util.HashMap;

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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;

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
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param southEast The southEast exit.
     * @param northWest The northWest exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room southEast, Room northWest) 
    {
        if(north != null)
            salidas.put("north", north);
        if(east != null)
            salidas.put("east", east);
        if(south != null)
            salidas.put("south", south);
        if(west != null)
            salidas.put("west", west);
        if(southEast != null)
            salidas.put("southeast", southEast);
        if(northWest != null)
            salidas.put("northwest", northWest);
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
}

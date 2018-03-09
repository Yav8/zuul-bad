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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room southEastExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room southEast) 
    {
        if(north != null)
            northExit = north;
        if(east != null)
            eastExit = east;
        if(south != null)
            southExit = south;
        if(west != null)
            westExit = west;
        if(southEast != null)
            southEastExit = southEast;
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
     * la sala (direcciones posibles: "north", "east", "south", "west", "southeast").
     * @return La sala con la direccion de la salida correspondiente o "null" si no 
     * existe salida.
     */
    public Room getExit(String direccion) {        
        Room salidaElegida = null;
        if(direccion.equals("north")) {
            salidaElegida = northExit;
        }
        else if(direccion.equals("east")) {
            salidaElegida = eastExit;
        }
        else if(direccion.equals("south")) {
            salidaElegida = southExit;
        }
        else if(direccion.equals("west")) {
            salidaElegida = westExit;
        }
        else if(direccion.equals("southeast")) {
            salidaElegida = southEastExit;
        }
        return salidaElegida;
    }
    
    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString() {
        String salidasExistentes = "Exits: ";
        if(northExit != null) {
            salidasExistentes += "north ";
        }
        if(eastExit != null) {
            salidasExistentes += "east ";
        }
        if(southExit != null) {
            salidasExistentes += "south ";
        }
        if(westExit != null) {
            salidasExistentes += "west ";
        }
        if(southEastExit != null) {
            salidasExistentes += "southeast";
        }
        return salidasExistentes;
    }
}

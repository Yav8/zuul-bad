/**
 * La clase Item permite representar la informacion
 * de los objetos que tienen las habitaciones.
 * @author Javier de Cea Dominguez.
 * @version 2018.03.23
 */
public class Item {
    private String descripcion;
    private String id;
    private int peso;
    private boolean canBePickedUp;
    
    /**
     * Constructor para objetos de la clase Item.
     * @param descripcion El nombre del objeto.
     * @param id La ID del objeto.
     * @param peso El peso del objeto.
     */
    public Item(String descripcion, String id, int peso, boolean canBePickedUp) {
        this.descripcion = descripcion;
        this.id = id;
        this.peso = peso;
        this.canBePickedUp = canBePickedUp;
    }
    
    /**
     * Devuelve las caracteristicas del objeto.
     * @return Devuelve las características del objeto
     */
    public String getCaracteristicas() {
        return "Item: " + descripcion + " - ID: " + id + " - weight: " + peso + " kg";
    }
    
    /**
     * Devuelve el ID del objeto.
     * @return El ID del objeto.
     */
    public String getID() {
        return id;
    }
    
    /**
     * Devuelve si el objeto puede ser cogido o no 
     * por el jugador.
     * @return Devuelve true si el objeto puede ser 
     * cogido por el jugador y false en caso contrario.
     */
    public boolean puedeSerCogidoElObjeto() {
        return canBePickedUp;
    }
    
    /**
     * Devuelve el peso del objeto.
     * @return Devuelve un int que es el peso del objeto.
     */
    public int getPeso() {
        return peso;
    }
}
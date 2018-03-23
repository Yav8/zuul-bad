/**
 * La clase Item permite representar la informacion
 * de los objetos que tienen las habitaciones.
 */
public class Item {
    String descripcion;
    String id;
    int peso;
    
    /**
     * Constructor para objetos de la clase Item.
     * @param descripcion El nombre del objeto.
     * @param id La ID del objeto.
     * @param peso El peso del objeto.
     */
    public Item(String descripcion, String id, int peso) {
        this.descripcion = descripcion;
        this.id = id;
        this.peso = peso;
    }
    
    /**
     * Devuelve las caracteristicas del objeto.
     * @return Devuelve las características del objeto
     */
    public String getCaracteristicas() {
        return "Item: " + descripcion + " - ID: " + id + " - weight: " + peso;
    }
    
    /**
     * Devuelve el ID del objeto.
     * @return El ID del objeto.
     */
    public String getID() {
        return id;
    }
}
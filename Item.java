/**
 * La clase Item permite representar la informacion
 * de los objetos que tienen las habitaciones.
 */
public class Item {
    String descripcion;
    int peso;
    
    /**
     * Constructor para objetos de la clase Item.
     */
    public Item(String descripcion, int peso) {
        this.descripcion = descripcion;
        this.peso = peso;
    }
    
    /**
     * Devuelve las caracteristicas del objeto.
     */
    public String getCaracteristicas() {
        return "Item: " + descripcion + " weigth: " + peso;
    }
}
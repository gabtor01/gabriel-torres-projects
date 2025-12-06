/**
 * Representa un bloque dentro del tablero con una posición (x, y) y un color.
 */
public class Bloque {
    private int x;
    private int y;
    private Color color;

    /**
     * Crea un nuevo bloque en la posición dada con el color especificado.
     * 
     * @param x posición horizontal del bloque
     * @param y posición vertical del bloque
     * @param color color del bloque
     */
    public Bloque(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Obtiene la posición Y del bloque.
     * 
     * @return coordenada Y
     */
    public int getY() {
        return y;
    }

    /**
     * Obtiene la posición X del bloque.
     * 
     * @return coordenada X
     */
    public int getX() {
        return x;
    }

    /**
     * Establece una nueva coordenada y.
     *
     * @param y El valor de y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Obtiene el color del bloque.
     * 
     * @return color del bloque
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Devuelve una representación visual del bloque en formato ANSI.
     * 
     * @return texto ANSI del bloque con color
     */
    public String toString() {
        return this.color.getCodigoAnsi() + "██" + "\u001B[0m";
    }
}

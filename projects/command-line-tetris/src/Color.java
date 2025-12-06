/**
 * Enumeración que define los colores disponibles para los bloques,
 * junto con su código ANSI y valor en puntos.
 */
public enum Color{
    ROJO("\u001B[31m", 10),
    VERDE("\u001B[32m", 20),
    AZUL("\u001B[34m", 30),
    AMARILLO("\u001B[33m", 40),
    MORADO("\u001B[35m", 50),
    CIAN("\u001B[36m", 60);

    private final String codigoAnsi;
    private final int puntos;

    /**
     * Constructor del color.
     * 
     * @param codigoAnsi código ANSI asociado al color
     * @param puntos puntos que aporta este color
     */
    Color(String codigoAnsi, int puntos) {
        this.codigoAnsi = codigoAnsi;
        this.puntos = puntos;
    }

    /**
     * Obtiene el código ANSI del color.
     * 
     * @return código ANSI
     */
    public String getCodigoAnsi() {
        return codigoAnsi;
    }

    /**
     * Obtiene los puntos asociados al color.
     * 
     * @return puntos del color
     */
    public int getPuntos() {
        return puntos;
    }
}
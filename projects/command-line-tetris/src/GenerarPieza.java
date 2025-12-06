/**
 * Clase encargada de generar piezas aleatorias.
 */
public class GenerarPieza {

    /**
     * Genera una pieza aleatoria.
     * 
     * @return una nueva pieza con forma aleatoria
     */
    public Pieza generar() {
        int[][][] FORMAS = {
            // Cuadro (O)
            {
                {1, 1},
                {1, 1}
            },
            // Línea (I)
            {
                {1, 1, 1, 1}
            },
            // T
            {
                {1, 1, 1},
                {0, 1, 0}
            },
            // L
            {
                {1, 0},
                {1, 0},
                {1, 1}
            },
            // J (L invertida)
            {
                {0, 1},
                {0, 1},
                {1, 1}
            },
            // S
            {
                {0, 1, 1},
                {1, 1, 0}
            },
            // Z
            {
                {1, 1, 0},
                {0, 1, 1}
            }
        };

        int colorRandom = (int) (Math.random() * FORMAS.length);
        int[][] forma = FORMAS[colorRandom];

        // Genera una posición inicial (x centrado, y arriba)
        int posX = 4; 
        int posY = 0;

        // Crea la pieza y la devuelve
        return new Pieza(posX, posY, forma);
    }
}

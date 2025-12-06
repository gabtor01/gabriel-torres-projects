/**
 * Representa una pieza compuesta por varios bloques en el tablero.
 */
public class Pieza {

    private Bloque[] bloques;
    private int posX;
    private int posY;
    private int[][] forma;

    /**
     * Constructor de la pieza.
     * 
     * @param posX posición inicial en el eje X
     * @param posY posición inicial en el eje Y
     * @param forma matriz que define la forma de la pieza
     */
    public Pieza(int posX, int posY, int[][] forma) {
        this.posX = posX;
        this.posY = posY;
        this.forma = forma;
        this.bloques = crearBloques(); // inicializa según la forma
    }

    /**
     * Mueve la pieza dentro del tablero.
     * 
     * @param dx desplazamiento horizontal
     * @param dy desplazamiento vertical
     */
    public void mover(int dx, int dy) {
        int nuevaPosX = posX + dx;
        int nuevaPosY = posY + dy + forma.length;

        if ((nuevaPosX + forma[0].length) <= 10 && nuevaPosX >= 0 && nuevaPosY <= 20) {
            posX += dx;
            posY += dy;
            aplicarCambios();
        }
    }

    /**
     * Rota la pieza 90° en sentido horario.
     */
    public void rotar() {
        int filas = forma.length;
        int columnas = forma[0].length;
        int[][] nuevaForma = new int[columnas][filas];
        
        Color[][] mapaColores = new Color[columnas][filas];
        
        // Rotar la matriz y mapear los colores
        int bloqueIdx = 0;
        for (int y = 0; y < filas; y++) {
            for (int x = 0; x < columnas; x++) {
                if (forma[y][x] == 1) {
                    // Nueva posición después de rotar
                    int nuevoX = x;
                    int nuevoY = filas - 1 - y;
                    
                    nuevaForma[nuevoX][nuevoY] = 1;
                    mapaColores[nuevoX][nuevoY] = bloques[bloqueIdx].getColor();
                    bloqueIdx++;
                }
            }
        }

        // Verificar que no se salgan del tablero las piezas
        for (int y = 0; y < nuevaForma.length; y++) {
            for (int x = 0; x < nuevaForma[y].length; x++) {
                if (nuevaForma[y][x] == 1) {
                    int tableroX = posX + x;
                    int tableroY = posY + y;

                    // Si la rotación sale fuera del tablero cancelar la rotación
                    if (tableroX < 0 || tableroX >= 10 || tableroY < 0 || tableroY >= 20) {
                        return;
                    }
                }
            }
        }
        
        forma = nuevaForma;
        
        // Actualizar los bloques con sus colores correspondientes
        bloqueIdx = 0;
        for (int y = 0; y < forma.length; y++) {
            for (int x = 0; x < forma[y].length; x++) {
                if (forma[y][x] == 1) {
                    this.bloques[bloqueIdx] = new Bloque(
                        posX + x, 
                        posY + y, 
                        mapaColores[y][x]
                    );
                    bloqueIdx++;
                }
            }
        }
    }

    /**
     * Aplica los cambios de posición a los bloques.
     */
    private void aplicarCambios() {
        int bloqueIdx = 0;
        
        for (int y = 0; y < forma.length; y++) {
            for (int x = 0; x < forma[y].length; x++) {
                if (forma[y][x] == 1) {
                    this.bloques[bloqueIdx] = new Bloque(posX + x, posY + y, this.bloques[bloqueIdx].getColor());
                    
                    bloqueIdx ++;            
                }
            }
        }
    }

    /**
     * Crea los bloques con colores aleatorios según la forma.
     * 
     * @return arreglo de bloques
     */
    public Bloque[] crearBloques() {
        Bloque[] resultado = new Bloque[4];

        int idxBloque = 0;
        for (int y = 0; y < forma.length; y++) {
            for (int x = 0; x < forma[y].length; x++) {
                if (forma[y][x] == 1) {
                    // Selecciona un color aleatorio
                    Color[] colores = Color.values();
                    Color color = colores[(int) (Math.random() * colores.length)];

                    resultado[idxBloque] = new Bloque(posX + x, posY + y, color);
                    idxBloque ++;
                }
            }
        }

        return resultado;
    }

    /**
     * Obtiene los bloques de la pieza.
     * 
     * @return arreglo de bloques
     */
    public Bloque[] getBloques() {
        return this.bloques;
    }

    /**
     * Obtiene la posición X de la pieza.
     * 
     * @return posición X
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Obtiene la posición Y de la pieza.
     * 
     * @return posición Y
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Obtiene la forma de la pieza.
     * 
     * @return matriz que representa la forma
     */
    public int[][] getForma() {
        return forma;
    }
}

/**
 * Representa el tablero del juego y gestiona la posición de las piezas.
 */
public class Tablero {
    private int ancho;
    private int alto;
    private Bloque[][] celdas;
    private Puntaje puntaje;
    private ArbolColoresBalanceado arbolColores;

    /**
     * Crea un tablero vacío con las dimensiones dadas.
     * 
     * @param ancho número de columnas
     * @param alto  número de filas
     */
    public Tablero(int ancho, int alto) {
        this.alto = alto;
        this.ancho = ancho;
        this.celdas = new Bloque[alto][ancho];
        this.puntaje = new Puntaje();
        this.arbolColores = new ArbolColoresBalanceado();

    }

    /**
     * Verifica si una pieza colisiona con el fondo o con otros bloques.
     * 
     * @param pieza pieza a verificar
     * @return true si hay colisión, false si no
     */
    public boolean hayColision(Pieza pieza) {
        for (Bloque bloque : pieza.getBloques()) {
            int x = bloque.getX();
            int y = bloque.getY();
            // Si estuviera fuera de los límites del tablero colisiona
            if (x < 0 || x >= ancho || y < 0 || y >= alto) {
                return true;
            }
            // Si ya está en el fondo
            if (y == alto - 1) {
                return true;
            }
            // Está sobre bloque ya fijado
            else if (this.celdas[y + 1][x] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Llama a los métodos necesarios para verificar si hay que eliminar filas.
     */
    public void procesarFilas() {
        celdas = verificarColumnasConsecutivas(celdas);
        celdas = verificarFilasCompletas(celdas);
    }

    /**
     * Verifica si en una columna hay 4 bloques de un mismo color para eliminar sus
     * filas.
     * 
     * @param celdasPorVerificar el tablero por verificar
     * @return el tablero modificado
     */
    public Bloque[][] verificarColumnasConsecutivas(Bloque[][] celdasPorVerificar) {
        for (int j = 0; j < celdasPorVerificar[0].length - 1; j++) {
            Color colorActual = null;
            int cantColor = 0;
            int[] filasPorEliminar = new int[4];

            for (int i = celdasPorVerificar.length - 1; i >= 0; i--) {
                if (celdasPorVerificar[i][j] != null) {
                    if (celdasPorVerificar[i][j].getColor() == colorActual) {
                        cantColor++;
                        filasPorEliminar[cantColor - 1] = i;
                    } else {
                        cantColor = 1;
                        colorActual = celdasPorVerificar[i][j].getColor();
                        filasPorEliminar[0] = i;
                    }

                    if (cantColor == 4) {
                        eliminarFilas(filasPorEliminar[0] - 3, 4, celdasPorVerificar);

                        return verificarColumnasConsecutivas(celdasPorVerificar);
                    }
                }
            }
        }

        return celdasPorVerificar;
    }

    /**
     * Verifica si hay una fila llena de bloques para eliminarla.
     * 
     * @param celdasPorVerificar el tablero por verificar
     * @return el tablero modificado
     */
    public Bloque[][] verificarFilasCompletas(Bloque[][] celdasPorVerificar) {
        for (int i = celdasPorVerificar.length - 1; i >= 0; i--) {
            boolean filaLlena = true;

            for (int j = celdasPorVerificar[0].length - 1; j >= 0; j--) {
                if (celdasPorVerificar[i][j] == null) {
                    filaLlena = false;
                }
            }

            if (filaLlena) {
                eliminarFilas(i, 1, celdasPorVerificar);

                return verificarFilasCompletas(celdasPorVerificar);
            }
        }
        return celdasPorVerificar;

    }

    /**
     * Elimina las filas del tablero especificadas.
     * 
     * @param fila                 la fila en que se empieza a eliminar bloques
     * @param cantFilasPorEliminar la cantidad de filas a eliminar
     * @param celdasPorVerificar   el tablero por verificar
     */
    public void eliminarFilas(int fila, int cantFilasPorEliminar, Bloque[][] celdasPorVerificar) {
        // Array de contadores para saber cuánta frecuencia disminuir
        int[] contadoresColores = new int[Color.values().length];

        // Contar cuántos bloques de cada color hay
        for (int x = fila; x < fila + cantFilasPorEliminar; x++){
            for (int j = 0; j < celdasPorVerificar[0].length; j++){
                Bloque bloque = celdasPorVerificar[x][j];
                if (bloque != null){
                    // ordinal() devuelve el entero de la posición del color en el enum
                    int indiceContador = bloque.getColor().ordinal();
                    contadoresColores[indiceContador]++; // Se incrementa un contador específico
                }
            }
        }

        // Determinar el color dominante
        Color colorDominanteEnFila = null;
        int maxBloques = 0;

        for (Color color : Color.values()){
            int cantidad = contadoresColores[color.ordinal()];
            if (cantidad > maxBloques){
                maxBloques = cantidad;
                colorDominanteEnFila = color;
            }
        }
        if (colorDominanteEnFila == null){
            colorDominanteEnFila = arbolColores.getColorDominante();
        }

        // Restar las frecuencias del árbol según los contadores
        for (Color color : Color.values()){ // Values() devuelve un array con los colores
            // Los índices del enum de la clase Color y del array contadoresColores coinciden por el orden de ambos
            int cantidad = contadoresColores[color.ordinal()];
            if (cantidad > 0){
                arbolColores.reducirFrecuencia(color, cantidad);
            }
        }

        int frecuenciaDelDominante = arbolColores.getFrecuencia(colorDominanteEnFila);
        puntaje.agregarPuntaje(cantFilasPorEliminar, colorDominanteEnFila, frecuenciaDelDominante);

        // Desplazar filas hacia abajo
        for (int x = fila - 1; x >= 0; x--) {
            for (int j = celdasPorVerificar[0].length - 1; j >= 0; j--) {
                if (celdasPorVerificar[x][j] != null) {
                    celdasPorVerificar[x][j].setY(celdasPorVerificar[x][j].getY() + cantFilasPorEliminar);
                }
                celdasPorVerificar[x + cantFilasPorEliminar][j] = celdasPorVerificar[x][j];
                celdasPorVerificar[x][j] = null;
            }
        }

    }

    /**
     * Fija una pieza al tablero en su posición actual.
     * 
     * @param pieza pieza a fijar
     */
    public void fijarPieza(Pieza pieza) {
        for (Bloque bloque : pieza.getBloques()) {
            int x = bloque.getX();
            int y = bloque.getY();
            if (x >= 0 && x < ancho && y >= 0 && y < alto) {
                this.celdas[y][x] = bloque;
                arbolColores.agregarColor(bloque.getColor());
            }
        }
    }

    /**
     * Crea una copia del estado actual del tablero.
     * 
     * @return copia de las celdas del tablero
     */
    private Bloque[][] copiarCeldas() {
        Bloque[][] temp = new Bloque[celdas.length][celdas[0].length];

        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[i].length; j++) {
                temp[i][j] = celdas[i][j];
            }
        }

        return temp;
    }

    /**
     * Imprime el puntaje total acumulado y la siguiente pieza que se
     * genera aleatoriamente.
     * 
     * @param pieza pieza por mostrar
     * @return lista con la puntuación y los bloques de la pieza
     */
    public String[] mostrarProgreso(Pieza pieza) {
        int[][] tempForma = pieza.getForma();
        String[] infoString = new String[tempForma.length + 6];

        infoString[0] = "Score: " + puntaje.getTotal();
        infoString[1] = "";
        infoString[2] = "Multiplier: x" + puntaje.getComboActual();
        infoString[3] = "";
        infoString[4] = "Next:";
        infoString[5] = "";
        int indBloque = 0;
        for (int i = 0; i < tempForma.length; i++) {
            infoString[i + 6] = "  ";
            for (int j = 0; j < tempForma[0].length; j++) {
                if (tempForma[i][j] == 1) {
                    infoString[i + 6] += pieza.getBloques()[indBloque].toString();
                    indBloque++;
                } else {
                    infoString[i + 6] += "  ";
                }
            }
        }
        return infoString;
    }

    /**
     * Imprime el tablero con la pieza actual en su posición.
     * 
     * @param piezaActual pieza que se está mostrando
     */
    public void imprimir(Pieza piezaActual, Pieza siguientePieza) {
        Bloque[][] tempCeldas = copiarCeldas();

        // Se guarda el contenido del tablero en una lista para poder mostrarse junto
        // con otros elementos
        String[] tableroString = new String[celdas.length + 1];

        for (Bloque bloque : piezaActual.getBloques()) {
            tempCeldas[bloque.getY()][bloque.getX()] = bloque;
        }

        for (int i = 0; i < tempCeldas.length; i++) {
            tableroString[i] = "    │";
            for (int j = 0; j < tempCeldas[i].length; j++) {
                if (tempCeldas[i][j] != null) {
                    tableroString[i] += tempCeldas[i][j].toString();
                } else {
                    tableroString[i] += "  ";
                }
            }
            tableroString[i] += "│";
        }
        tableroString[celdas.length] = "    └────────────────────┘";

        String[] siguientePiezaString = mostrarProgreso(siguientePieza);
        for (int i = 0; i < tableroString.length; i++) {
            System.out.print(tableroString[i]);
            if (i < siguientePiezaString.length) {
                System.out.print("      " + siguientePiezaString[i]);
            }
            System.out.println();
        }
    }

    /**
     * Devuelve el bloque que está en la posición (x, y) del tablero.
     * Si la posición está fuera de los límites, devuelve null.
     * 
     * @param x columna
     * @param y fila
     * @return Bloque en la celda o null si está vacía o fuera de rango
     */
    public Bloque getCelda(int x, int y) {
        if (x < 0 || x >= ancho || y < 0 || y >= alto) {
            return null;
        }
        return celdas[y][x];
    }

    /**
     * Devuelve el objeto puntaje del tablero.
     * 
     * @return puntaje
     */
    public Puntaje getPuntaje() {
        return puntaje;
    }
}

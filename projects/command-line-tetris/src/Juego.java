import java.util.Scanner;

/**
 * Controla el flujo del juego, incluyendo el tablero, las piezas y el puntaje.
 */
public class Juego{
    private Tablero tablero;
    private Pieza piezaActual;
    private Pieza siguientePieza;
    private boolean enJuego;
    private Scanner sc;

    /**
     * Constructor del juego. Inicializa el tablero, puntaje y entrada de usuario.
     */
    public Juego(){
        this.tablero = new Tablero(10, 20);
        sc = new Scanner(System.in);
        enJuego = true;
    }

    /**
     * Despliega el menú inicial del juego, permitiendo al usuario decidir
     * si desea iniciar una nueva partida de Tetris o salir del programa.
     */
    public void titular(){
            String titulo = 
                            "\n\n\n" +
                            "████████  ██████  ████████  ██████   ██   ██████  \n" +
                            "   ██     ██         ██     ██   ██  ██  ██       \n" +
                            "   ██     ████       ██     ██████   ██   ██████  \n" +
                            "   ██     ██         ██     ██   ██  ██        ██ \n" +
                            "   ██     ██████     ██     ██   ██  ██   ██████  \n";
        System.out.println(colorASCII(titulo));
    }

    /**
     * Inicia el ciclo principal del juego.
     */
    public void iniciar(){
        int ronda = 1;
        while (enJuego){
            if (ronda == 1){
                piezaActual = new GenerarPieza().generar();
                siguientePieza = new GenerarPieza().generar();
            }
            titular();
            this.tablero.imprimir(this.piezaActual, this.siguientePieza);
            int[] movimientos = procesarEntrada();

            // Se aplican los movimientos y por cada vez se verifica si hay colisión
            for (int i = 0; i < movimientos[2]; i++) {
                piezaActual.mover(movimientos[0], movimientos[1]);
                if (this.tablero.hayColision(piezaActual)) {
                    tablero.fijarPieza(piezaActual);

                    // verifica si hay que limpiar las filas
                    tablero.procesarFilas();

                    piezaActual = siguientePieza;
                    siguientePieza = new GenerarPieza().generar();
                    break;
                }
            }
            if (esGameOver()){
                enJuego = false;
                mostrarResumenPartida();
            }
            else{
                ronda++;
                // "Limpiar consola" con códigos ANSI
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
    }

    /**
     * Procesa la entrada del jugador y devuelve el movimiento correspondiente.
     * 
     * @return arreglo con desplazamiento en X, desplazamiento en Y y cantidad de
     *         pasos
     */
    public int[] procesarEntrada() { // ---> res[0] = movimiento en x, res[1] movimiento en y, res [2] = cantidad de
                                     // veces que hay que aplicar el movimiento.
        System.out.print("Digite su siguiente movimiento(ASD): ");
        String movimiento = sc.nextLine().toUpperCase();
        int[] res = new int[3];

        switch (movimiento) {
            case "A":
                res[0] = -1;
                res[1] = 1;
                res[2] = 1;
                break;
            case "D":
                res[0] = 1;
                res[1] = 1;
                res[2] = 1;
                break;
            case "S":
                res[0] = 0;
                res[1] = 1;
                res[2] = 2;
                break;
            case "T":
                res[0] = 0;
                res[1] = 1;
                res[2] = 20;
                break;
            case "R":
                piezaActual.rotar();
                res[0] = 0;
                res[1] = 1;
                res[2] = 2;
                break;
            default:
                break;
        }
        return res;
    }

    /**
     * Revisa si el juego debe terminar. El juego termina se 
     * pasa de la altura, independientemente de la pieza actual.
     */
    private boolean esGameOver() {
        for (Bloque b : piezaActual.getBloques()) {

        // La pieza está por encima del tablero (y < 0)
        if (b.getY() < 0) {
            return true;
        }
        // La pieza aparece sobre un bloque ya existente
        if (tablero.getCelda(b.getX(), b.getY()) != null) {
            return true;
        }
    }
    return false;
}

    /**
     * Finaliza el juego mostrando el puntaje
     * y permite reiniciar o salir.
     */
    private void mostrarResumenPartida() {
        enJuego = false;
        System.out.println("\n\n======= GAME OVER =======\n");
        System.out.println("Puntaje final: " + tablero.getPuntaje().getTotal());
        System.out.println("\n=========================\n");
        System.out.println("¿Desea jugar nuevamente?");
        System.out.println("  S = Sí    |    N = No ");
        System.out.print("Digite su opción: ");

        String opcion = sc.nextLine().trim().toUpperCase();

        if (opcion.equals("S")) {
            // "Limpiar consola" con códigos ANSI
            System.out.print("\033[H\033[2J");
            System.out.flush();
            // Reiniciar valores
            tablero = new Tablero(10, 20);
            piezaActual = null;
            siguientePieza = null;
            enJuego = true;

            iniciar(); // arranca una nueva partida
        } else {
            System.out.println("Gracias por jugar Tetris.");
        }
    }

    private static final String[] COLORS = {
                                            "\u001B[31m", // ROJO
                                            "\u001B[32m", // VERDE
                                            "\u001B[34m", // AZUL
                                            "\u001B[33m", // AMARILLO
                                            "\u001B[35m", // MORADO
                                            "\u001B[36m"  // CYAN
                                            };

    private static final String RESET = "\u001B[0m";

    private static String colorRandom(){
        int i = (int) (Math.random() * COLORS.length);
        return COLORS[i];
    }

    public static String colorASCII(String ascii){
        StringBuilder out = new StringBuilder();

        for (char c : ascii.toCharArray()){
            if (c == '█'){
                out.append(colorRandom()).append(c).append(RESET);
            }
            else{
                out.append(c);
            }
        }

        return out.toString();
    }

}
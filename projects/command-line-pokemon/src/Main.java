/**
 * Clase principal del programa.
 * Contiene el método main que inicia el juego como tal.
 */
public class Main{
    /**
     * Método principal que se ejecuta al iniciar el programa.
     * Crea una instancia del juego y comienza la partida.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args){
        Juego juego = new Juego();
        juego.jugar();
    }
}
/**
 * Representa un gimnasio Pokémon. Contiene información sobre su nombre, 
 * nivel, estado y la lista de entrenadores. El último entrenador de la 
 * lista se considera el líder del gimnasio.
 */
public class Gimnasio{
    /** Indica si el gimnasio ha sido derrotado (true = derrotado, false = disponible) */
    private boolean estaDerrotado;

    /** Nivel del gimnasio, calculado a partir del nivel promedio de sus entrenadores */
    private int nivel;

    /** Nombre del gimnasio */
    private String nombre;

    /** Lista de entrenadores que pertenecen al gimnasio */
    private Entrenador[] entrenadores;

    /**
     * Constructor que inicializa un gimnasio con su nombre y lista de entrenadores.
     * Se asegura de que el último entrenador de la lista sea el líder.
     *
     * @param nombreGimnasio Nombre del gimnasio.
     * @param listaEntrenadores Array de entrenadores del gimnasio.
     */
    public Gimnasio(String nombreGimnasio, Entrenador[] listaEntrenadores){
        nombre = nombreGimnasio;
        entrenadores = listaEntrenadores;
        estaDerrotado = false;
        ordernarGimnasio();
        setNivelGimnasio();
    }

    /**
     * Obtiene el nombre del gimnasio.
     *
     * @return Nombre del gimnasio.
     */
    public String getNombre(){
        return nombre;
    }

     /**
     * Obtiene la lista de entrenadores del gimnasio.
     *
     * @return Array de entrenadores.
     */
    public Entrenador[] getEntrenadores(){
        return entrenadores;
    }

    /**
     * Obtiene el estado del gimnasio.
     *
     * @return true si el gimnasio ha sido derrotado, false si está disponible.
     */
    public boolean getEstadoGimnasio(){
        return estaDerrotado;
    }

    /**
     * Obtiene el nivel del gimnasio.
     *
     * @return Nivel calculado a partir del promedio de niveles de los entrenadores.
     */
    public int getNivelGimnasio(){
        return nivel;
    }

    /**
     * Calcula y establece el nivel promedio del gimnasio
     * basándose en los niveles de sus entrenadores.
     */
    private void setNivelGimnasio(){
        int sumaNivel = 0;
        for (Entrenador entrenador : entrenadores){
            sumaNivel += entrenador.getNivel();
        }
        nivel = sumaNivel / entrenadores.length;
    }

    /**
     * Método que coloca al líder del gimnasio (Entrenador con mayor nivel)
     * al final de la lista de entrenadores.
     */
    private void ordernarGimnasio(){
        int i, j;
        for (i = 0; i < entrenadores.length; i++){
            j = i; // contadores
            // Si j > 0  y el nivel de un entrenador en j es menor que el que esta en j - 1 (anterior)
            while ((j > 0) && (entrenadores[j].getNivel() < entrenadores[j - 1].getNivel())){
                Entrenador entrenadorTemporal = entrenadores[j]; // Crear un entrenador para guardar el que esta en j
                entrenadores[j] = entrenadores[j - 1]; // Pasar el entrenador en j - 1 a posicion j
                entrenadores[j - 1] = entrenadorTemporal; // Pasar el entrenador j a posicion j - 1
                j--; // Reducir en 1 el contador para volver a comparar
            }
        }
    }
}
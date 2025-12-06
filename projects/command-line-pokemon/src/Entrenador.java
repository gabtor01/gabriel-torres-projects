/**
 * Representa a un entrenador Pokémon con sus principales características:
 * nombre, indicador de si es el jugador y la lista de sus Pokémons.
 */
public class Entrenador {
    /** Nombre del entrenador */
    private String nombre;

    /** Indica si el entrenador es el jugador (true = jugador, false = NPC) */
    private boolean esJugador;

    /** Lista de Pokémons del entrenador (máximo 6) */
    private Pokemon[] listaPokemon;

    /**
     * Constructor que inicializa un entrenador.
     * Si es NPC, se le asigna un equipo aleatorio de 6 Pokémons.
     * Si es el jugador, se inicializa el equipo vacío (para seleccionar Pokémons luego).
     *
     * @param nombre Nombre del entrenador.
     * @param catalogo Catálogo de Pokémons disponibles para NPCs.
     * @param esJugador true si es el jugador, false si es un NPC.
     */
    public Entrenador(String nombre, Pokemon[] catalogo, boolean esJugador) {
        this.nombre = nombre;
        if (!esJugador){
            listaPokemon = elegirPokemonesRandom(catalogo);
        }else{
            listaPokemon = new Pokemon[6];
        }
        this.esJugador = esJugador;
    }

    /**
     * Obtiene el nombre del entrenador.
     *
     * @return Nombre del entrenador.
     */
    public String getNombre() {
        return nombre;
    } 

    /**
     * Establece un nuevo nombre para el entrenador.
     *
     * @param nombre Nuevo nombre del entrenador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Verifica si el entrenador es el jugador.
     *
     * @return true si es jugador, false si es NPC.
     */
    public boolean getEsJugador() {
        return esJugador;
    }

    /**
     * Obtiene la lista de Pokémons del entrenador.
     *
     * @return Array de Pokémons.
     */
    public Pokemon[] getListaPokemon() {
        return listaPokemon;
    }

    /**
     * Agrega un Pokémon en la posición indicada del equipo.
     *
     * @param indice Posición (0-5) donde se agregará el Pokémon.
     * @param pokemon Pokémon por agregar.
     */
    public void agregarPokemon(int indice, Pokemon pokemon){
        listaPokemon[indice] = pokemon;
    }

    /**
     * Calcula el nivel promedio del entrenador basado en los niveles de sus Pokémons.
     *
     * @return Nivel promedio.
     */
    public int getNivel() {
        int sumaNiveles = 0;
        for(Pokemon pokemon : listaPokemon) {
            sumaNiveles += pokemon.getNivel();
        }

        return (int) sumaNiveles / listaPokemon.length;
    }

    /**
     * Selecciona 6 Pokémons aleatorios del catálogo, evitando repeticiones.
     * Se utiliza para asignar el equipo de cada NPC.
     *
     * @param pokemones Catálogo de Pokémons disponibles.
     * @return Array de 6 Pokémons seleccionados aleatoriamente.
     */
    public Pokemon[] elegirPokemonesRandom(Pokemon[] pokemones){
        Pokemon[] equipo = new Pokemon[6]; // Misma cantidad que el jugador
        boolean[] pokemonesUsados = new boolean[pokemones.length]; 
        int i = 0;
        while (i < 6) {
            int indice = (int)(Math.random() * pokemones.length); // crear indice aleatorio en cada iteracion

            if (!pokemonesUsados[indice]) { // si no esta usado, lo agregamos
                equipo[i] = pokemones[indice];
                pokemonesUsados[indice] = true; // si se agrego se marca como usado
                i++; // solo se sigue si se logra agregar el pokemon
            }
            // si ya estaba usado, simplemente se repite el while
        }
        return equipo;
    }
    
    /**
     * Cura todos los Pokémons del entrenador restaurando su HP y PP.
     */
    public void curarPokemones(){
        for(int i = 0;i<listaPokemon.length;i++){
            if(listaPokemon[i]!=null){
                listaPokemon[i].curarPokemon();
            }
        }
    }

    /**
     * Verifica si todos los Pokémons del entrenador están debilitados (HP = 0).
     *
     * @return true si todos los Pokémons están KO, false si al menos uno está activo.
     */
    public boolean pokemonesDebilitados(){
        int contadorDebilitados = 0;
        for (Pokemon pokemon : listaPokemon){
            if (!pokemon.getEstado()){
                contadorDebilitados++;
            }
        }
        if (contadorDebilitados == listaPokemon.length){
            return true;
        }else{
            return false;
        }
    }
}
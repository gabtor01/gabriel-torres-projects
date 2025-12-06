/**
 * Clase para mostrar la información del juego en la consola.
 * Todos sus métodos son de tipo void, ya que solo realizan
 * tareas de impresión y no retornan valores.
 */
public class Vista {
    /** Constructor vacío de la clase Vista */
    public Vista(){}
    
    /**
     * Muestra la pantalla de inicio del juego.
     */
    public void mostrarInicio() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("██████╗  ██████╗ ██╗  ██╗███████╗███╗   ███╗ ██████╗ ███╗   ██╗");
        System.out.println("██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝████╗ ████║██╔═══██╗████╗  ██║");
        System.out.println("██████═╝██║   ██║█████╔╝ █████╗  ██╔████╔██║██║   ██║██╔██╗ ██║");
        System.out.println("██╔══╝  ██║   ██║██╔═██╗ ██╔══╝  ██║╚██╔╝██║██║   ██║██║╚██╗██║");
        System.out.println("██║     ╚██████╔╝██║  ██╗███████╗██║ ╚═╝ ██║╚██████╔╝██║ ╚████║");
        System.out.println("╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═══╝");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }    


    /**
     * Muestra información general de un Pokémon, incluyendo nombre,
     * nivel, estadísticas y lista de ataques.
     *
     * @param pokemon Pokémon cuya información se mostrará.
     */
    public void mostrarPokemon(Pokemon pokemon){
        System.out.println("\nNombre: " + pokemon.getNombre());
        System.out.println("Nivel: " + pokemon.getNivel());
        System.out.println("HP: " + pokemon.getStats().getHpMax() + " Atk: " + pokemon.getStats().getAtk());
        System.out.println("Def: " + pokemon.getStats().getDef() + " Spd: " + pokemon.getStats().getSpd());
        Ataque[] listaAtaquesPokemon = pokemon.getListaAtaques(); // Clon para que sea más fácil de leer
        for(int i = 0; i < listaAtaquesPokemon.length; i += 2){
            System.out.println(listaAtaquesPokemon[i].getNombre() + "  " + listaAtaquesPokemon[i + 1].getNombre());
        }
    }

    /**
     * Muestra el estado actual de los Pokémons durante una batalla.
     *
     * @param pokemonNPC Pokémon del rival.
     * @param pokemonJugador Pokémon del jugador.
     */
    public void mostrarPokemonesEnBatalla(Pokemon pokemonNPC, Pokemon pokemonJugador){
        System.out.println();
        System.out.println("==============[  B A T A L L A  ]==============");

        // BARRA DE VIDA (10 segmentos)
        java.util.function.BiFunction<Integer, Integer, String> barraHP = (hp, max) -> {
            int total = 10;
            int llenos = (int) Math.round((hp * 10.0) / max);
            int vacios = total - llenos;
            return "HP: " + "█".repeat(llenos) + "░".repeat(vacios) +
                "  (" + hp + "/" + max + ")";
        };

        // --- RIVAL (arriba) ---
        System.out.println(" RIVAL: " + pokemonNPC.getNombre() + "   (Nv " + pokemonNPC.getNivel() + ")");
        System.out.println(" " + barraHP.apply(pokemonNPC.getStats().getHpActual(),
                                            pokemonNPC.getStats().getHpMax()));
        System.out.println(" ----------------------------------------------");

        // Separador estilo GameBoy
        System.out.println("                    ▼  ▼  ▼");

        // --- JUGADOR (abajo) ---
        System.out.println(" TU POKÉMON: " + pokemonJugador.getNombre() + "   (Nv " + pokemonJugador.getNivel() + ")");
        System.out.println(" " + barraHP.apply(pokemonJugador.getStats().getHpActual(),
                                            pokemonJugador.getStats().getHpMax()));
        System.out.println(" ==============================================");
    }



    /**
     * Muestra información detallada de un ataque realizado por un Pokémon.
     *
     * @param ataque Ataque que se está mostrando.
     * @param ataqueFinal Daño final infligido por el ataque.
     */
    public void mostrarAtaque(Ataque ataque, int ataqueFinal){
        System.out.printf("%-15s | Tipo: %-10s | Poder: %-3d | Precisión: %3.0f%% | PP: %2d/%-2d | Daño: %-3d\n",
                ataque.getNombre(),
                ataque.getElementoAtaque().getNombre(),
                ataque.getPotencia(),
                ataque.getPrecision() * 100,
                ataque.getPpActual(),
                ataque.getPpMaximo(),
                ataqueFinal);
    }

    /**
     * Muestra el catálogo completo de Pokémons disponibles en formato tabla.
     *
     * @param catalogo Array de Pokémons disponibles.
     */
    public void mostrarCatalogoPokemones(Pokemon[] catalogo){
        int columnas = 5;
        int total = catalogo.length;
        int filas = (int) Math.ceil((double) total / columnas);
    
        System.out.println("\nCATALOGO DE POKEMON (" + total + " disponibles)");
        System.out.println("=====================================================================================");
    
        for (int i = 0; i < filas; i++) {
         for (int j = 0; j < columnas; j++) {
            int indice = i + j * filas;
                if (indice < total) {
                System.out.printf(" %2d. %-12s", indice, catalogo[indice].getNombre());
                } else {
                System.out.print("               ");
                }
            }
            System.out.println();
        }
        System.out.println("=====================================================================================");
    }

    /**
     * Muestra los Pokémons de un entrenador en formato tabla con sus estadísticas.
     *
     * @param entrenador Entrenador cuyo equipo se mostrará.
     */
    public void mostrarPokemones(Entrenador entrenador){
        Pokemon[] listaPokemones = entrenador.getListaPokemon();
        System.out.println("\nPOKEMONES DE " + entrenador.getNombre().toUpperCase());
        System.out.println("------------------------------------------------------------------------");
        System.out.println("| ID | Nombre          | Nivel |   HP    | Ataque | Defensa |Velocidad|");
        System.out.println("|----|-----------------|-------|---------|--------|---------|---------|");

        for (int i = 0; i < listaPokemones.length; i++) {
            Pokemon pokemon = listaPokemones[i];
            if (pokemon != null && pokemon.getEstado()) {
                Stats stats = pokemon.getStats();
                System.out.printf("| %-2d | %-15s | %-5d | %2d/%-4d | %-6d | %-7d | %-7d |\n",
                        i + 1,
                        pokemon.getNombre(),
                        pokemon.getNivel(),
                        stats.getHpActual(),
                        stats.getHpMax(),
                        stats.getAtk(),
                        stats.getDef(),
                        stats.getSpd());
            }
        }

        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * Muestra los gimnasios disponibles y sus entrenadores.
     *
     * @param gimnasios Array de gimnasios disponibles.
     */
    public void mostrarGimnasios(Gimnasio[] gimnasios){
        System.out.println("\nGIMNASIOS DISPONIBLES");
        System.out.println("================================================");
        System.out.println("| ID | Nombre Gimnasio    | Nivel | Estado     |");
        System.out.println("|----|--------------------|-------|------------|");

        int id = 1;
        for (Gimnasio gimnasio : gimnasios) {
            if (!gimnasio.getEstadoGimnasio()) {
                System.out.printf("| %-2d | %-18s | %-5d | %-10s |\n",
                        id,
                        gimnasio.getNombre(),
                        gimnasio.getNivelGimnasio(),
                        "DISPONIBLE");
                id++;
            }
        }

        System.out.println("================================================");

        // Mostrar detalles de cada gimnasio
        for (Gimnasio gimnasio : gimnasios) {
            if (!gimnasio.getEstadoGimnasio()) {
                mostrarEntrenadores(gimnasio);
            }
        }
    }

    /**
     * Muestra los entrenadores de un gimnasio en formato tabla.
     *
     * @param gimnasio Gimnasio cuyos entrenadores se mostrarán.
     */
    private void mostrarEntrenadores(Gimnasio gimnasio){
        Entrenador[] listaEntrenadores = gimnasio.getEntrenadores();
        System.out.println("\nENTRENADORES DEL GIMNASIO " + gimnasio.getNombre().toUpperCase());
        System.out.println("------------------------------------------------");
        System.out.println("| ID | Nombre              | Nivel  |    Rol   |");
        System.out.println("|----|---------------------|--------|----------|");

        for (int i = 0; i < listaEntrenadores.length; i++) {
            Entrenador entrenador = listaEntrenadores[i];
            if (entrenador != null) {
                String rol = (i == listaEntrenadores.length - 1) ? "LIDER" : "NORMAL";
                System.out.printf("| %-2d | %-19s | %-6d | %-8s |\n",
                        i + 1,
                        entrenador.getNombre(),
                        entrenador.getNivel(),
                        rol);
            }
        }
        System.out.println("------------------------------------------------");
    }

    /**
     * Muestra los ataques disponibles de un Pokémon en formato tabla.
     *
     * @param pokemon Pokémon cuyos ataques se mostrarán.
     */
    public void mostrarAtaquesDisponibles(Pokemon pokemon){
        Ataque[] ataques = pokemon.getListaAtaques();
        System.out.printf("\n ATAQUES DISPONIBLES DE %s\n", pokemon.getNombre().toUpperCase());
        System.out.println("─".repeat(65));
        System.out.println("|----|-----------------|----------|--------|-----------|--------|");
        System.out.println("| ID |     Nombre      |   Tipo   | Poder  | Precisión |   PP   |");
        System.out.println("|----|-----------------|----------|--------|-----------|--------|");
        
        for (int i = 0; i < ataques.length; i++) {
            Ataque atk = ataques[i];
            if (atk != null && atk.getPpActual() > 0) {
                System.out.printf("| %-2d | %-15s | %-8s | %-6d | %-8.0f%% | %2d/%-2d  |\n",
                        i + 1,
                        atk.getNombre(),
                        atk.getElementoAtaque().getNombre(),
                        atk.getPotencia(),
                        atk.getPrecision() * 100,
                        atk.getPpActual(),
                        atk.getPpMaximo());
            }
        }

        System.out.println("|----|-----------------|----------|--------|-----------|--------|");
        System.out.print("Seleccione un ataque (1-" + ataques.length + "): ");
    }

    /**
     * Muestra un resumen final de la partida del jugador.
     *
     * @param numeroVictorias Número de victorias obtenidas.
     * @param numeroDerrotas Número de derrotas sufridas.
     * @param duracion Duración total de la partida en segundos.
     */
    public void mostrarResumenFinal(int numeroVictorias, int numeroDerrotas, long duracion){
        System.out.println("\n" + "═".repeat(50));
        System.out.println("            RESUMEN FINAL DEL ENTRENADOR");
        System.out.println("═".repeat(50));

        System.out.printf("║ %-25s: %-20d ║\n", "Victorias obtenidas", numeroVictorias);
        System.out.printf("║ %-25s: %-20d ║\n", "Derrotas sufridas", numeroDerrotas);

        int totalCombates = numeroVictorias + numeroDerrotas;
        System.out.printf("║ %-25s: %-20d ║\n", "Total de combates", totalCombates);

        if (totalCombates > 0) {
            double porcentajeVictoria = (double) numeroVictorias / totalCombates * 100;
            System.out.printf("║ %-25s: %-19.1f%% ║\n", "Porcentaje de victorias", porcentajeVictoria);
        }

        System.out.printf("║ %-25s: %-20d ║\n", "Duración total (segundos)", duracion);

        String evaluacion;
        if (numeroVictorias >= 8) evaluacion = "MAESTRO POKÉMON";
        else if (numeroVictorias >= 5) evaluacion = "ENTRENADOR EXPERTO";
        else if (numeroVictorias >= 2) evaluacion = "ENTRENADOR NOVATO";
        else evaluacion = "APRENDIZ POKÉMON";

        System.out.printf("║ %-25s: %-20s ║\n", "Evaluación", evaluacion);
        System.out.println("═".repeat(50));
    }

    /**
     * Muestra las opciones disponibles durante el turno de batalla.
     */
    public void mostrarInformacionTurno(){
        System.out.println("\n" + "─".repeat(42));
        System.out.println("           OPCIONES DE TURNO");
        System.out.println("─".repeat(42));
        System.out.println("-------------------------------------------");
        System.out.println("|   OPCION  |          ACCIÓN             |");
        System.out.println("-------------------------------------------");
        System.out.println("|     1     |      Elegir Ataque          |");
        System.out.println("|     2     |      Cambiar Pokémon        |");
        System.out.println("-------------------------------------------");
        System.out.print("Seleccione una opción (1-2):  ");
    }

    /**
     * Muestra un mensaje en la consola.
     *
     * @param mensaje Mensaje que se mostrará.
     */
    public void mostrarMensaje(String mensaje){
        System.out.print(mensaje);
    }

}
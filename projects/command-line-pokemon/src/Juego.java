/**
 * Clase principal que controla la lógica del juego.
 * Inicializa pokemones, ataques, elementos, gimnasios y entrenadores,
 * y gestiona el flujo de combate y la interacción con el jugador.
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class Juego{
    private Pokemon[] catalogoPokemones;   // Lista de todos los pokemones disponibles
    private Gimnasio[] catalogoGimnasios;  // Lista de gimnasios
    private Entrenador jugador;             // Entrenador jugador
    private Scanner scanner;                // Scanner para entrada de usuario
    private Vista CLI;                      // Interfaz de texto para mostrar información

    /**
     * Constructor: inicializa todos los elementos del juego.
     */
    public Juego(){
        // Inicializar el objeto vista
        CLI = new Vista();

        // Inicializar el scanner
        scanner = new Scanner(System.in);

        // Declarar e inicializar los elementos
        Elemento normal = new Elemento("Normal", null, null);
        Elemento fuego = new Elemento("Fuego", null, null);
        Elemento agua = new Elemento("Agua", null, null);
        Elemento planta = new Elemento("Planta", null, null);
        Elemento electrico = new Elemento("Eléctrico", null, null);
        Elemento hielo = new Elemento("Hielo", null, null);
        Elemento lucha = new Elemento("Lucha", null, null);
        Elemento veneno = new Elemento("Veneno", null, null);
        Elemento tierra = new Elemento("Tierra", null, null);
        Elemento volador = new Elemento("Volador", null, null);
        Elemento psiquico = new Elemento("Psíquico", null, null);
        Elemento bicho = new Elemento("Bicho", null, null);
        Elemento roca = new Elemento("Roca", null, null);
        Elemento fantasma = new Elemento("Fantasma", null, null);
        Elemento dragon = new Elemento("Dragón", null, null);
        Elemento siniestro = new Elemento("Siniestro", null, null);
        Elemento acero = new Elemento("Acero", null, null);
        Elemento hada = new Elemento("Hada", null, null);

        // Inicializar las debilidades/fortalezas
        normal = new Elemento("Normal", new Elemento[]{lucha}, new Elemento[]{});
        fuego = new Elemento("Fuego", new Elemento[]{agua, roca, tierra}, new Elemento[]{planta, hielo, bicho, acero});
        agua = new Elemento("Agua", new Elemento[]{planta, electrico}, new Elemento[]{fuego, roca, tierra});
        planta = new Elemento("Planta", new Elemento[]{fuego, hielo, veneno, volador, bicho}, new Elemento[]{agua, tierra, roca});
        electrico = new Elemento("Eléctrico", new Elemento[]{tierra}, new Elemento[]{agua, volador});
        hielo = new Elemento("Hielo", new Elemento[]{fuego, lucha, roca, acero}, new Elemento[]{planta, tierra, volador, dragon});
        lucha = new Elemento("Lucha", new Elemento[]{volador, psiquico, hada}, new Elemento[]{normal, roca, acero, hielo, siniestro});
        veneno = new Elemento("Veneno", new Elemento[]{tierra, psiquico}, new Elemento[]{planta, hada});
        tierra = new Elemento("Tierra", new Elemento[]{agua, planta, hielo}, new Elemento[]{fuego, electrico, roca, acero});
        volador = new Elemento("Volador", new Elemento[]{electrico, hielo, roca}, new Elemento[]{planta, lucha, bicho});
        psiquico = new Elemento("Psíquico", new Elemento[]{bicho, fantasma, siniestro}, new Elemento[]{lucha, veneno});
        bicho = new Elemento("Bicho", new Elemento[]{fuego, volador, roca}, new Elemento[]{planta, psiquico, siniestro});
        roca = new Elemento("Roca", new Elemento[]{agua, planta, lucha, tierra, acero}, new Elemento[]{fuego, hielo, volador, bicho});
        fantasma = new Elemento("Fantasma", new Elemento[]{fantasma, siniestro}, new Elemento[]{psiquico, fantasma});
        dragon = new Elemento("Dragón", new Elemento[]{hielo, dragon, hada}, new Elemento[]{dragon});
        siniestro = new Elemento("Siniestro", new Elemento[]{lucha, bicho, hada}, new Elemento[]{psiquico, fantasma});
        acero = new Elemento("Acero", new Elemento[]{fuego, lucha, tierra}, new Elemento[]{hielo, roca, hada});
        hada = new Elemento("Hada", new Elemento[]{acero, veneno}, new Elemento[]{lucha, dragon, siniestro});

        // Declarar e inicializar los ataques
        // Fuego
        Ataque ascuas = new Ataque("Ascuas", fuego, 40, 25, 1.0);
        Ataque lanzallamas = new Ataque("Lanzallamas", fuego, 90, 15, 0.9);

        // Agua
        Ataque pistolaAgua = new Ataque("Pistola Agua", agua, 40, 25, 1.0);
        Ataque surf = new Ataque("Surf", agua, 90, 15, 1.0);

        // Planta
        Ataque latigoCepa = new Ataque("Látigo Cepa", planta, 45, 25, 1.0);
        Ataque hojaAfilada = new Ataque("Hoja Afilada", planta, 55, 25, 0.95);

        // Eléctricos
        Ataque impactrueno = new Ataque("Impactrueno", electrico, 40, 30, 1.0);
        Ataque rayo = new Ataque("Rayo", electrico, 90, 15, 1.0);

        // Hielo
        Ataque vientoHielo = new Ataque("Viento Hielo", hielo, 55, 15, 0.95);
        Ataque rayoHielo = new Ataque("Rayo Hielo", hielo, 90, 10, 1.0);

        // Lucha
        Ataque golpeKarate = new Ataque("Golpe Karate", lucha, 50, 25, 1.0);
        Ataque ondaVacio = new Ataque("Onda Vacío", lucha, 40, 30, 1.0);

        // Veneno
        Ataque picotazoVeneno = new Ataque("Picotazo Veneno", veneno, 35, 35, 1.0);
        Ataque bombaLodo = new Ataque("Bomba Lodo", veneno, 90, 10, 1.0);

        // Tierra
        Ataque magnitud = new Ataque("Magnitud", tierra, 70, 20, 1.0);
        Ataque terremoto = new Ataque("Terremoto", tierra, 100, 10, 1.0);

        // Volador
        Ataque tornados = new Ataque("Tornado", volador, 40, 35, 1.0);
        Ataque ataqueAla = new Ataque("Ataque Ala", volador, 60, 20, 1.0);

        // Psíquicos
        Ataque confusion = new Ataque("Confusión", psiquico, 50, 25, 1.0);
        Ataque psiquicoAtk = new Ataque("Psíquico", psiquico, 90, 10, 1.0);

        // Bicho
        Ataque picadura = new Ataque("Picadura", bicho, 50, 25, 1.0);
        Ataque zumbido = new Ataque("Zumbido", bicho, 90, 10, 1.0);

        // Roca
        Ataque avalancha = new Ataque("Avalancha", roca, 75, 10, 0.9);
        Ataque rocaAfilada = new Ataque("Roca Afilada", roca, 100, 5, 0.8);

        // Fantasma
        Ataque tinieblas = new Ataque("Tinieblas", fantasma, 50, 15, 1.0);
        Ataque bolaSombra = new Ataque("Bola Sombra", fantasma, 80, 15, 1.0);

        // Dragón
        Ataque garraDragon = new Ataque("Garra Dragón", dragon, 80, 15, 1.0);
        Ataque enfado = new Ataque("Enfado", dragon, 120, 10, 0.9);
        Ataque colaDragon = new Ataque("Cola Dragón", dragon, 90, 15, 1.0);

        // Siniestro
        Ataque mordisco = new Ataque("Mordisco", siniestro, 60, 25, 1.0);
        Ataque triturar = new Ataque("Triturar", siniestro, 80, 15, 1.0);

        // Acero
        Ataque garraMetal = new Ataque("Garra Metal", acero, 50, 35, 1.0);
        Ataque cabezaHierro = new Ataque("Cabeza de Hierro", acero, 80, 15, 1.0);

        // Hada
        Ataque brilloMagico = new Ataque("Brillo Mágico", hada, 80, 10, 1.0);
        Ataque fuerzaLunar = new Ataque("Fuerza Lunar", hada, 95, 10, 1.0);

        // Normal
        Ataque placaje = new Ataque("Placaje", normal, 40, 35, 1.0);
        Ataque golpeCuerpo = new Ataque("Golpe Cuerpo", normal, 85, 15, 1.0);

        // Inicializar los pokemones y asignarlos al catálogo
        catalogoPokemones = new Pokemon[]{
        // Fuego
        new Pokemon("Charmander", 12, new Stats(39,52,43,65), new Elemento[]{fuego}, new Ataque[]{ascuas, lanzallamas, placaje, mordisco}),
        new Pokemon("Charmeleon", 20, new Stats(58,64,58,80), new Elemento[]{fuego}, new Ataque[]{ascuas, lanzallamas, garraMetal, mordisco}),
        new Pokemon("Charizard", 36, new Stats(78,84,78,100), new Elemento[]{fuego, volador}, new Ataque[]{lanzallamas, garraDragon, placaje, ascuas}),
        new Pokemon("Flareon", 35, new Stats(65,130,60,65), new Elemento[]{fuego}, new Ataque[]{lanzallamas, ascuas, mordisco, placaje}),
        new Pokemon("Moltres", 50, new Stats(90,100,90,90), new Elemento[]{fuego, volador}, new Ataque[]{lanzallamas, ascuas, ataqueAla, tornados}),
        new Pokemon("Arcanine", 45, new Stats(90,110,80,95), new Elemento[]{fuego}, new Ataque[]{lanzallamas, mordisco, placaje, garraMetal}),

        // Agua
        new Pokemon("Squirtle", 12, new Stats(44,48,65,43), new Elemento[]{agua}, new Ataque[]{surf, pistolaAgua, placaje, mordisco}),
        new Pokemon("Wartortle", 20, new Stats(59,63,80,58), new Elemento[]{agua}, new Ataque[]{surf, mordisco, placaje, pistolaAgua}),
        new Pokemon("Blastoise", 36, new Stats(79,83,100,78), new Elemento[]{agua}, new Ataque[]{surf, garraMetal, mordisco, pistolaAgua}),
        new Pokemon("Vaporeon", 35, new Stats(130,65,60,65), new Elemento[]{agua}, new Ataque[]{surf, pistolaAgua, placaje, mordisco}),
        new Pokemon("Lapras", 42, new Stats(130,85,80,60), new Elemento[]{agua, hielo}, new Ataque[]{surf, vientoHielo, mordisco, placaje}),
        new Pokemon("Gyarados", 40, new Stats(95,125,79,81), new Elemento[]{agua, volador}, new Ataque[]{mordisco, colaDragon, placaje, surf}),

        // Planta
        new Pokemon("Bulbasaur", 12, new Stats(45,49,49,45), new Elemento[]{planta, veneno}, new Ataque[]{latigoCepa, hojaAfilada, placaje, mordisco}),
        new Pokemon("Ivysaur", 20, new Stats(60,62,63,60), new Elemento[]{planta, veneno}, new Ataque[]{hojaAfilada, latigoCepa, placaje, mordisco}),
        new Pokemon("Venusaur", 36, new Stats(80,82,83,80), new Elemento[]{planta, veneno}, new Ataque[]{hojaAfilada, latigoCepa, placaje, garraMetal}),
        new Pokemon("Scyther", 32, new Stats(70,110,80,105), new Elemento[]{planta, volador}, new Ataque[]{hojaAfilada, ataqueAla, mordisco, placaje}),

        // Eléctrico
        new Pokemon("Pikachu", 15, new Stats(35,55,40,90), new Elemento[]{electrico}, new Ataque[]{impactrueno, rayo, placaje, mordisco}),
        new Pokemon("Raichu", 25, new Stats(60,90,55,110), new Elemento[]{electrico}, new Ataque[]{impactrueno, rayo, placaje, garraMetal}),
        new Pokemon("Jolteon", 35, new Stats(65,65,60,130), new Elemento[]{electrico}, new Ataque[]{impactrueno, rayo, placaje, mordisco}),
        new Pokemon("Zapdos", 50, new Stats(90,90,85,100), new Elemento[]{electrico, volador}, new Ataque[]{impactrueno, rayo, ataqueAla, tornados}),

        // Hielo
        new Pokemon("Articuno", 50, new Stats(90,85,100,85), new Elemento[]{hielo, volador}, new Ataque[]{vientoHielo, rayoHielo, ataqueAla, tornados}),
        new Pokemon("Lapras", 42, new Stats(130,85,80,60), new Elemento[]{agua, hielo}, new Ataque[]{vientoHielo, rayoHielo, surf, placaje}),

        // Lucha
        new Pokemon("Machop", 15, new Stats(70,80,50,35), new Elemento[]{lucha}, new Ataque[]{golpeKarate, ondaVacio, placaje, mordisco}),
        new Pokemon("Machoke", 30, new Stats(80,100,70,45), new Elemento[]{lucha}, new Ataque[]{golpeKarate, ondaVacio, placaje, mordisco}),
        new Pokemon("Machamp", 40, new Stats(90,130,80,55), new Elemento[]{lucha}, new Ataque[]{golpeKarate, ondaVacio, placaje, garraMetal}),
        new Pokemon("Lucario", 50, new Stats(70,110,70,90), new Elemento[]{lucha, acero}, new Ataque[]{golpeKarate, garraMetal, placaje, mordisco}),
        new Pokemon("ZapdosGalar", 50, new Stats(90,125,90,100), new Elemento[]{lucha, volador}, new Ataque[]{golpeKarate, placaje, mordisco, ataqueAla}),

        // Veneno
        new Pokemon("Nidoran", 12, new Stats(55,47,52,41), new Elemento[]{veneno}, new Ataque[]{picotazoVeneno, bombaLodo, placaje, mordisco}),
        new Pokemon("Nidoking", 40, new Stats(81,92,77,85), new Elemento[]{veneno, tierra}, new Ataque[]{picotazoVeneno, bombaLodo, placaje, terremoto}),

        // Tierra
        new Pokemon("Sandshrew", 12, new Stats(50,75,85,40), new Elemento[]{tierra}, new Ataque[]{magnitud, terremoto, placaje, mordisco}),
        new Pokemon("Garchomp", 55, new Stats(108,130,95,102), new Elemento[]{dragon, tierra}, new Ataque[]{garraDragon, terremoto, mordisco, placaje}),

        // Volador
        new Pokemon("Pidgey", 10, new Stats(40,45,40,56), new Elemento[]{volador}, new Ataque[]{tornados, ataqueAla, placaje, mordisco}),
        new Pokemon("Togekiss", 50, new Stats(85,50,95,120), new Elemento[]{hada, volador}, new Ataque[]{brilloMagico, fuerzaLunar, ataqueAla, tornados}),

        // Psíquico
        new Pokemon("Alakazam", 45, new Stats(55,50,45,120), new Elemento[]{psiquico}, new Ataque[]{psiquicoAtk, confusion, placaje, mordisco}),
        new Pokemon("Mewtwo", 70, new Stats(106,110,90,130), new Elemento[]{psiquico}, new Ataque[]{psiquicoAtk, placaje, mordisco, garraDragon}),
        new Pokemon("Mew", 60, new Stats(100,100,100,100), new Elemento[]{psiquico}, new Ataque[]{psiquicoAtk, placaje, mordisco, garraMetal}),

        // Bicho
        new Pokemon("Scyther", 32, new Stats(70,110,80,105), new Elemento[]{bicho, volador}, new Ataque[]{picadura, zumbido, ataqueAla, placaje}),
        new Pokemon("Scizor", 45, new Stats(70,130,100,65), new Elemento[]{bicho, acero}, new Ataque[]{garraMetal, picadura, zumbido, golpeKarate}),

        // Roca
        new Pokemon("Onix", 20, new Stats(35,45,160,70), new Elemento[]{roca, tierra}, new Ataque[]{avalancha, rocaAfilada, placaje, mordisco}),
        new Pokemon("Kabutops", 35, new Stats(60,115,105,80), new Elemento[]{roca, agua}, new Ataque[]{avalancha, rocaAfilada, mordisco, garraMetal}),
        new Pokemon("Tyranitar", 55, new Stats(100,134,110,61), new Elemento[]{roca, dragon}, new Ataque[]{avalancha, rocaAfilada, garraDragon, mordisco}),

        // Fantasma
        new Pokemon("Gengar", 40, new Stats(60,65,60,110), new Elemento[]{fantasma, veneno}, new Ataque[]{bolaSombra, tinieblas, mordisco, placaje}),
        new Pokemon("Haunter", 25, new Stats(45,50,45,95), new Elemento[]{fantasma, veneno}, new Ataque[]{bolaSombra, tinieblas, placaje, mordisco}),
        new Pokemon("Gastly", 12, new Stats(30,35,30,80), new Elemento[]{fantasma, veneno}, new Ataque[]{bolaSombra, tinieblas, mordisco, placaje}),

        // Dragón
        new Pokemon("Dragonite", 55, new Stats(91,134,95,80), new Elemento[]{dragon, volador}, new Ataque[]{garraDragon, enfado, colaDragon, mordisco}),
        new Pokemon("Dragonair", 30, new Stats(61,84,65,70), new Elemento[]{dragon}, new Ataque[]{garraDragon, colaDragon, surf, impactrueno}),

        // Siniestro
        new Pokemon("Umbreon", 35, new Stats(95,65,110,65), new Elemento[]{siniestro}, new Ataque[]{mordisco, triturar, placaje, garraMetal}),

        // Acero
        new Pokemon("Metagross", 55, new Stats(80,135,130,70), new Elemento[]{acero, psiquico}, new Ataque[]{garraMetal, cabezaHierro, psiquicoAtk, placaje}),

        // Hada
        new Pokemon("Clefable", 45, new Stats(95,70,73,60), new Elemento[]{hada}, new Ataque[]{brilloMagico, fuerzaLunar, placaje, golpeCuerpo}),

        // Normal
        new Pokemon("Snorlax", 40, new Stats(160,110,65,30), new Elemento[]{normal}, new Ataque[]{placaje, golpeCuerpo, mordisco, garraMetal}),
        new Pokemon("Eevee", 10, new Stats(55,55,50,55), new Elemento[]{normal}, new Ataque[]{placaje, mordisco, ascuas, impactrueno})
    };

    // Declarar e inicializar gimnasios y entrenadores
    Gimnasio gimnasioCiuPla = new Gimnasio(
    "Ciudad Plateada",
    new Entrenador[]{
        new Entrenador("Camper Liam", catalogoPokemones, false),
        new Entrenador("Picnicker Ana", catalogoPokemones, false),
        new Entrenador("Brock", catalogoPokemones, false)
        }
    );

    Gimnasio gimnasioCiuCel = new Gimnasio(
        "Ciudad Celeste",
        new Entrenador[]{
            new Entrenador("Swimmer Joel", catalogoPokemones, false),
            new Entrenador("Lass Iris", catalogoPokemones, false),
            new Entrenador("Misty", catalogoPokemones, false)
        }
    );

    Gimnasio gimnasioCiuCar = new Gimnasio(
        "Ciudad Carmin", 
        new Entrenador[]{
            new Entrenador("Sailor Huey", catalogoPokemones, false),
            new Entrenador("Engineer Bailey", catalogoPokemones, false),
            new Entrenador("Lt. Surge", catalogoPokemones, false)
        }
    );

    Gimnasio gimnasioCiuAzu = new Gimnasio(
        "Ciudad Azulona",
        new Entrenador[]{
            new Entrenador("Beauty Lola", catalogoPokemones, false),
            new Entrenador("Cooltrainer May", catalogoPokemones, false),
            new Entrenador("Erika", catalogoPokemones, false)
        }
    );

    // Guardarlos en su catalogo 
    catalogoGimnasios = new Gimnasio[] {gimnasioCiuPla, gimnasioCiuCel, gimnasioCiuCar, gimnasioCiuAzu};
    }

    
    /**
     * Inicia el juego, pidiendo al jugador su nombre y seleccionando pokemones.
     */
    private void iniciarJuego(){
        jugador = new Entrenador("", null, true);
        int indicePokemon = 0;
        int contadorPokemon = 0;
        boolean indiceEsEntero;
        Pokemon[] pokemonesJugador = jugador.getListaPokemon(); // Clon temporal
        CLI.mostrarInicio();
        CLI.mostrarMensaje("Ingrese su nombre: ");
        jugador.setNombre(scanner.nextLine());

        CLI.mostrarCatalogoPokemones(catalogoPokemones);

        while (contadorPokemon < 6){
            CLI.mostrarMensaje("Pokemon <" + (contadorPokemon + 1) + ">: ");
            try{
                indicePokemon = scanner.nextInt();

                if ((indicePokemon < 0) || indicePokemon >= catalogoPokemones.length){
                CLI.mostrarMensaje("Selección fuera de rango. Intente otra vez.");
                continue;
            }
            jugador.agregarPokemon(contadorPokemon, catalogoPokemones[indicePokemon]);
            CLI.mostrarMensaje("< Pokémon agregado con éxito >");
            CLI.mostrarPokemon(catalogoPokemones[indicePokemon]);
            contadorPokemon++;

            }catch(InputMismatchException errorIngresandoPokemon){
                CLI.mostrarMensaje("Solo puede ingresar un número entero asociado a un pokemon.");
                scanner.nextLine(); 
                continue;
                
            }
        }
    }

    /**
     * Método principal de juego.
     * Controla el avance por gimnasios y combates.
     */
    public void jugar(){
        // Contadores para guardar estadisticas
        int contadorVictorias = 0;
        int contadorDerrotas = 0;
        // Variables para tomar el tiempo de juego
        long tiempoInicial = 0;
        long tiempoFinal = 0;
        // Bandera para saber cuando hubo una derrota o victoria
        boolean jugadorGana = false; // true -> gano el jugador | false -> perdio el jugador

        iniciarJuego();
        CLI.mostrarGimnasios(catalogoGimnasios);
        tiempoInicial = System.currentTimeMillis(); // Tomar tiempo inicial
        for (Gimnasio gimnasio : catalogoGimnasios){
            boolean gimnasioSuperado = false;   
            while(!gimnasioSuperado){
                for (Entrenador npc : gimnasio.getEntrenadores()){
                    if (jugador.pokemonesDebilitados()){
                        CLI.mostrarMensaje("Todos tus Pokémon están debilitados.");
                        contadorDerrotas++;
                        jugador.curarPokemones();
                        for (Entrenador e : gimnasio.getEntrenadores()) {
                            e.curarPokemones();
                        }
                        break;
                    }
                    jugadorGana = combatir(npc); // jugador ya es un atributo no hace falta pasarlo como parametro
                    if (jugadorGana){
                        contadorVictorias++;
                    }else{
                        contadorDerrotas++;
                        jugador.curarPokemones();
                        CLI.mostrarMensaje("Has perdido contra " + npc.getNombre() + ". Inténtalo de nuevo.");
                        break;
                    }
                }
                if(!jugador.pokemonesDebilitados()){
                    gimnasioSuperado = true;
                    CLI.mostrarMensaje("Has vencido al líder del gimnasio " + gimnasio.getNombre());
                    jugador.curarPokemones(); // Despues de cada gimnasio derrotado se curan los pokemones
                }
            }
        }
        tiempoFinal = System.currentTimeMillis(); // Tomar tiempo final
        CLI.mostrarMensaje("¡Conquistaste todos los gimnasios!");
        CLI.mostrarResumenFinal(contadorVictorias, contadorDerrotas, (tiempoFinal - tiempoInicial)/1000);
    }

    /**
     * Controla el combate entre el jugador y un entrenador NPC.
     * @param npc Entrenador rival
     * @return true si gana el jugador, false si pierde
     */
    private boolean combatir(Entrenador npc){
        while (!jugador.pokemonesDebilitados() && !npc.pokemonesDebilitados()){
            // Pokemon actual siempre en la posicion 0
            // El mas rapido agarra turno primero
            int velocidadJugador = jugador.getListaPokemon()[0].getStats().getSpd();
            int velocidadNPC = npc.getListaPokemon()[0].getStats().getSpd();

            // se verifica si los pokemones de los entrenadores fueron debilitados para realizar el cambio antes del turno
            if (!npc.getListaPokemon()[0].getEstado()) {
                cambiarPokemon(npc);
            }
            if (!jugador.getListaPokemon()[0].getEstado()) {
                cambiarPokemon(jugador);
            }

            CLI.mostrarPokemonesEnBatalla(npc.getListaPokemon()[0], jugador.getListaPokemon()[0]);

            if (velocidadJugador > velocidadNPC) {
                turnoJugador(npc);
                if (npc.getListaPokemon()[0].getEstado()) { // se salta el turno del npc si el ataque del jugador debilitó al pokemon
                    turnoNPC(npc);
                }
            } else {
                turnoNPC(npc);
                if (jugador.getListaPokemon()[0].getEstado()) { // se salta el turno del jugador si el ataque del npc debilitó al pokemon
                    turnoJugador(npc);
                }
            }
        }
        if (jugador.pokemonesDebilitados()){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Controla el turno del jugador: atacar o cambiar de pokémon.
     * @param npc Entrenador rival
     */
    private void turnoJugador(Entrenador npc){
        int opcionTurno = 0; // 1 para elegir ataque 2 para cambiar pokemon
        boolean opcionTurnoValida = false; // para salir del while
        CLI.mostrarMensaje("\n====> Turno del entrenador: " + jugador.getNombre() + "\n");
        CLI.mostrarInformacionTurno();

        while (opcionTurnoValida != true){
            try{
                opcionTurno = scanner.nextInt();
            }catch(InputMismatchException errorIngresandoOpcionTurno){
                CLI.mostrarMensaje("Debe ingresar un número entero.");
                opcionTurnoValida = false;
                scanner.nextLine();
            }if (opcionTurno > 3 || opcionTurno < 1){
                CLI.mostrarMensaje("Selección fuera de rango. Intente otra vez.");
            }else{
                opcionTurnoValida = true;
            }
        }
        // Si el jugador decide escoger ataque
        if (opcionTurno == 1){
            int indiceAtaque = 0; // el ataque que escoja el jugador
            boolean ataqueValido = false; // para salir del while
            Ataque[] ataquesPokemonActual = jugador.getListaPokemon()[0].getListaAtaques(); // clon para evitar mucho texto
            // todo filtrar los ataques que tienen un pp igual a 0
            CLI.mostrarAtaquesDisponibles(jugador.getListaPokemon()[0]); // Le paso el pokemon actual
            while (ataqueValido != true){
                CLI.mostrarMensaje("Seleccione un ataque: ");
                try{
                    indiceAtaque = scanner.nextInt();
                }catch(InputMismatchException errorIngresandoIndiceAtaque){
                    CLI.mostrarMensaje("Debe ingresar un número entero.");
                    scanner.nextLine();
                }if (indiceAtaque > ataquesPokemonActual.length || indiceAtaque < 1){
                    CLI.mostrarMensaje("Selección fuera de rango. Intente otra vez.");
                    scanner.nextLine();
                }else{
                    ataqueValido = true;
                }
            }
            procesarAtaque(ataquesPokemonActual[indiceAtaque - 1], jugador.getListaPokemon()[0], npc.getListaPokemon()[0]);

        }else if (opcionTurno == 2){
            // Hacer el swap
            cambiarPokemon(jugador);
        }
    }
    
    /**
     * Cambia el pokémon actual de un entrenador por otro disponible.
     * @param entrenador Entrenador que cambiará de pokémon
     */
    private void cambiarPokemon(Entrenador entrenador) {
        int indicePokemon;

        if (entrenador.getEsJugador()) {
            indicePokemon = 0; // el pokemon que escoja el jugador como actual
            boolean pokemonValido = false; // para salir del while
            CLI.mostrarPokemones(jugador);
            while (!pokemonValido){
                CLI.mostrarMensaje("Seleccione un pokémon: ");
                try{
                    indicePokemon = scanner.nextInt();
                }catch(InputMismatchException errorIngresandoIndicePokemon){
                    CLI.mostrarMensaje("Debe ingresar un número entero.");
                    scanner.nextLine();
                }
                if (indicePokemon > jugador.getListaPokemon().length || indicePokemon < 1){
                    CLI.mostrarMensaje("Selección fuera de rango. Intente otra vez.");
                    scanner.nextLine();
                }else if (!jugador.getListaPokemon()[indicePokemon - 1].getEstado()){
                    CLI.mostrarMensaje("El Pokémon seleccionado está debilitado y no puede ser seleccionado.");
                    scanner.nextLine();
                }else{
                    pokemonValido = true;
                    indicePokemon --;
                }
            }
        } else {
            String indicesPokemonesFiltrados = ""; 
            for (int i = 0; i < entrenador.getListaPokemon().length; i++) {
                if (entrenador.getListaPokemon()[i].getEstado()) {
                    indicesPokemonesFiltrados += Integer.toString(i);
                }
            }

            int indiceRandomFiltrado = (int) Math.random() * indicesPokemonesFiltrados.length();
            indicePokemon = Character.getNumericValue(indicesPokemonesFiltrados.charAt(indiceRandomFiltrado));
        }

        Pokemon pokemonActualViejo = entrenador.getListaPokemon()[0]; // Guardar el actual anterior
        Pokemon pokemonActualNuevo = entrenador.getListaPokemon()[indicePokemon];
        entrenador.agregarPokemon(0, pokemonActualNuevo); // Poner el actual nuevo en la pos 0
        entrenador.agregarPokemon(indicePokemon, pokemonActualViejo); // Poner el actual viejo en la pos indicePokemon
        this.CLI.mostrarMensaje("\n==========> " + entrenador.getNombre() + " ha cambiado a " + pokemonActualViejo.getNombre() + " por " + pokemonActualNuevo.getNombre() +"\n");
    }

    /**
     * Controla el turno de un NPC: atacar o cambiar pokémon aleatoriamente.
     * @param npc Entrenador rival
     */
    private void turnoNPC(Entrenador npc){
        CLI.mostrarMensaje("\n====> Turno del entrenador: " + npc.getNombre() + "\n"); 
        Pokemon pokemonActual = npc.getListaPokemon()[0];
        if (pokemonActual.getEstado()){
            double probabilidadCambiarPokemon = Math.random();

            if (probabilidadCambiarPokemon < 0.2) { // probabilidad random de que el npc cambie de pokemon
                cambiarPokemon(npc);

            } else { // el npc eligen un ataque aleatoreo
                int indiceAtaque = generarDecisionIA(2);
                procesarAtaque(npc.getListaPokemon()[0].getListaAtaques()[indiceAtaque], npc.getListaPokemon()[0], jugador.getListaPokemon()[0]);
            }
        }
    }

    /**
     * Genera una decisión aleatoria para el NPC.
     * @param accion 1=Cambiar Pokémon, 2=Atacar
     * @return índice elegido
     */
    private int generarDecisionIA(int accion){
        if (accion == 1){ //Cambiar Pokemon
            return (int)(Math.random() * 6);
        }else if (accion == 2){ // Elegir Ataque
            return (int)(Math.random() * 4);
        }else{
            // Aqui se podria agregar otra funcionalidad
            return 0;
        }
    }
    
    /**
     * Procesa un ataque de un Pokémon sobre otro, aplicando daño y mensajes.
     * @param ataquePorAplicar Ataque que se aplicará
     * @param pokemonAliado Pokémon que realiza el ataque
     * @param pokemonRival Pokémon que recibe el ataque
     */
    private void procesarAtaque(Ataque ataquePorAplicar, Pokemon pokemonAliado, Pokemon pokemonRival){
        // calcular los danos segun los tipos de los pokemones y probabilidades de critico
        ataquePorAplicar.setPpActual(ataquePorAplicar.getPpActual() - 1);

        int ataqueTotal = (ataquePorAplicar.getPotencia() + pokemonAliado.getStats().getAtk()) - pokemonRival.getStats().getDef();
        double probabilidadAtaque = Math.random();

        if (probabilidadAtaque > ataquePorAplicar.getPrecision()) {
            // todo mover a vista
            CLI.mostrarMensaje("Ataque fallido\n");
        } else {
            double multiplicadorAtaque = 1;
            for (Elemento elemento : pokemonRival.getElementos()) { // se multiplica el daño del ataque contra elementos más fuertes
                for (Elemento debilidad : elemento.getDebilidades()) {
                    if (debilidad.getNombre() == ataquePorAplicar.getElementoAtaque().getNombre()) {
                        multiplicadorAtaque *= 2;
                        CLI.mostrarMensaje("Fue muy eficaz\n");
                    }
                }
            }

            for (Elemento elemento : pokemonRival.getElementos()) { // se multiplica el daño del ataque contra elementos más debiles
                for (Elemento fortaleza : elemento.getFortalezas()) {
                    if (fortaleza.getNombre() == ataquePorAplicar.getElementoAtaque().getNombre()) {
                        multiplicadorAtaque *= 0.5;
                        CLI.mostrarMensaje("Fue poco eficaz\n");
                    }
                }
            }

            double probabilidadCritico = Math.random();
            if (probabilidadCritico < 0.33) {
                multiplicadorAtaque *= 2;
                CLI.mostrarMensaje("Ataque critico\n");
            }

            this.CLI.mostrarAtaque(ataquePorAplicar, (int)(ataqueTotal * multiplicadorAtaque));
            pokemonRival.aplicarAtaque(pokemonRival.getStats().getHpActual() - (int)(ataqueTotal * multiplicadorAtaque));
            if (pokemonRival.getStats().getHpActual() == 0) {
                this.CLI.mostrarMensaje("\n=======> El Pokémon " + pokemonRival.getNombre() + " ha sido debilitado\n");
            }
        }

    }
    
    /**
     * Cierra el scanner usado para entrada de usuario.
     */
    private void cerrarScanner(){
        scanner.close();
    }
    
}
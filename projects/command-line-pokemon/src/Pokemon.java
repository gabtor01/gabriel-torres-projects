/**
 * Representa un Pokémon con sus características básicas:
 * nombre, nivel, estadísticas, tipos y ataques.
 */
public class Pokemon {
    /** Nombre del Pokémon */
    private String nombre;

    /** Nivel del Pokémon */
    private int nivel;

    /** Estadísticas base del Pokémon (HP, ataque, defensa, etc.) */
    private Stats stats;

    /** Tipos elementales del Pokémon */
    private Elemento[] elementos;

    /** Lista de ataques que el Pokémon puede usar */
    private Ataque[] listaAtaques;

    /** Estado del Pokémon: true = activo, false = debilitado */
    private boolean estado = true;

    /**
     * Constructor para crear un nuevo Pokémon con sus atributos.
     *
     * @param nombre Nombre del Pokémon.
     * @param nivel Nivel del Pokémon.
     * @param stats Estadísticas base del Pokémon.
     * @param elementos Tipos elementales del Pokémon.
     * @param listaAtaques Lista de ataques que el Pokémon puede usar.
     */
    public Pokemon(String nombre,int nivel,Stats stats,Elemento[] elementos,Ataque[] listaAtaques){
        this.nombre = nombre;
        this.nivel = nivel;
        this.stats = stats;
        this.elementos = elementos;
        this.listaAtaques = listaAtaques;
    }

    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return Nombre del Pokémon.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene las estadísticas del Pokémon.
     *
     * @return Objeto Stats con las estadísticas del Pokémon.
     */
    public Stats getStats(){
        return stats;
    }

    /**
     * Obtiene el nivel del Pokémon.
     *
     * @return Nivel del Pokémon.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Obtiene los tipos elementales del Pokémon.
     *
     * @return Array de objetos Elemento que representan los tipos.
     */
    public Elemento[] getElementos() {
        return elementos;
    }

    /**
     * Obtiene la lista de ataques que el Pokémon puede usar.
     *
     * @return Array de objetos Ataque.
     */
    public Ataque[] getListaAtaques() {
        return listaAtaques;
    }

    /**
     * Obtiene el estado actual del Pokémon.
     *
     * @return true si el Pokémon tiene HP > 0, false si está debilitado.
     */
    public boolean getEstado(){
        return this.stats.getHpActual() > 0;
    }

    /**
     * Establece el estado del Pokémon.
     *
     * @param estado true = activo, false = debilitado.
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * Aplica un ataque al Pokémon, reduciendo sus puntos de vida.
     *
     * @param ataque Cantidad de daño que se aplicará al HP del Pokémon.
     */
    public void aplicarAtaque(int ataque) {
        this.stats.setHpActual(ataque);
    }

    /**
     * Cura al Pokémon restaurando su HP al máximo y recargando los PP de sus ataques.
     */
    public void curarPokemon(){
        stats.setHpActual(stats.getHpMax());
        for(int i = 0; i<listaAtaques.length;i++){
            if(listaAtaques[i]!=null){
                listaAtaques[i].setPpActual(listaAtaques[i].getPpMaximo());
            }
        }
    }
}

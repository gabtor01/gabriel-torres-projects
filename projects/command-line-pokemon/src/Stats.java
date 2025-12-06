/**
 * Representa las estadísticas base de un Pokémon:
 * vida máxima y actual, ataque, defensa y velocidad.
 */
public class Stats {

    /** Vida máxima del Pokémon */
    private int hpMax;

    /** Vida actual del Pokémon */
    private int hpActual;

    /** Valor de ataque del Pokémon */
    private int atk;

    /** Valor de defensa del Pokémon */
    private int def;

    /** Velocidad del Pokémon */
    private int spd;

    /**
     * Constructor para inicializar las estadísticas de un Pokémon.
     * La vida actual se inicializa al valor máximo de HP.
     *
     * @param hpMax Vida máxima del Pokémon.
     * @param atk Valor de ataque.
     * @param def Valor de defensa.
     * @param spd Velocidad del Pokémon.
     */
    public Stats(int hpMax,int atk,int def,int spd){
        this.hpMax = hpMax;
        hpActual = hpMax;
        this.atk = atk;
        this.def = def;
        this.spd = spd;
    }

    /**
     * Obtiene la vida máxima del Pokémon.
     *
     * @return Valor de HP máximo.
     */
    public int getHpMax() {
        return hpMax;
    }

    /**
     * Establece la vida actual del Pokémon.
     * Si el valor proporcionado es menor a 0, se ajusta a 0.
     *
     * @param hpNuevo Nueva cantidad de HP actual.
     */
    public void setHpActual(int hpNuevo) {
        if (hpNuevo < 0) {
            this.hpActual = 0;
        } else {
            this.hpActual = hpNuevo;
        }
    }

    /**
     * Obtiene la vida actual del Pokémon.
     *
     * @return HP actual.
     */
    public int getHpActual() {
        return hpActual;
    }

    /**
     * Obtiene el valor de ataque del Pokémon.
     *
     * @return Valor de ataque.
     */
    public int getAtk() {
        return atk;
    }

    /**
     * Obtiene el valor de defensa del Pokémon.
     *
     * @return Valor de defensa.
     */
    public int getDef() {
        return def;
    }

    
    /**
     * Obtiene la velocidad del Pokémon.
     *
     * @return Valor de velocidad.
     */
    public int getSpd() {
        return spd;
    }
}

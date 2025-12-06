/**
 * Clase que gestiona el puntaje total y los combos por color.
 */
public class Puntaje {
    private int total;
    private int comboActual;
    private Color ultimoColorDominante;
    
    /**
     * Agrega puntaje según el color dominante y gestiona el combo actual.
     * 
     * @param filas número de filas eliminadas
     * @param dominante color dominante en las filas
     */
    public void agregarPuntaje(int filas, Color dominante, int frecuenciaEnArbol){
        if(dominante==ultimoColorDominante){
            comboActual++;
        }
        else{
            comboActual = 1;
        } 
        total += (filas + dominante.getPuntos() + frecuenciaEnArbol) * comboActual;
        ultimoColorDominante = dominante;
    }

    /**
     * Devuelve el puntaje total acumulado.
     * 
     * @return puntaje total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Devuelve el combo actual acumulado.
     * 
     * @return combo actual
     */
    public int getComboActual() {
        return comboActual;
    }
}

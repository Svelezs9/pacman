

/**
 * En esta clase se guarda la información de una celda y su contenido
 * @author Helmuth Trefftz
 */
public class Celda {
    boolean esMuro;             // True si esta celda es muro (pared)
    boolean esSalida;           // True si esta celda es una salida del laberinto
    boolean tieneArepita;       // True si esta celda tiene una arepita
    boolean arepaMala; 
    Caracter caracter;          // Referenia al caracter que ocupa la celda o null si no hay caracter
    
     
    /**
     * Constructor
     * @param esMuro true si esta celda es muro, false de lo contrario
     * @param esSalida true si esta celda es Salida, false de lo contrario
     * @param tieneArepita true si esta celda tiene una arepita, false de lo contrario
     * @param arepaMala true si esta celda tiene una arepita y ademas es mal, del contrario es falsa
     * @param caracter  referencia al caracter que ocupa la celda, o null si no hay caracter
     */
    public Celda(boolean esMuro, boolean esSalida, boolean tieneArepita,boolean arepaMala, Caracter caracter) {
        this.esMuro = esMuro;
        this.esSalida = esSalida;
        this.tieneArepita = tieneArepita;
        this.arepaMala = arepaMala; 
        this.caracter = caracter;
    }
    
    /**
     * Retorna el caracter con el que se representa esta celda
     * @return caracter que representa el contenido de la celda
     */
    public char caracterCelda() {
        if(esMuro) {
            return '*';
        } else if(tieneArepita) {
            return '.';
        } else if(caracter != null) {
            return caracter.representacion;
        } else if (esSalida) {
            return 'O';
        } else {
            return ' ';
        }
    }
    
}
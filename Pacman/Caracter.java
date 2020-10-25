/**
 * Información acerca del Caracter
 * Tanto el Pacman como los Fantasmas son caracteres
 * @author Helmuth Trefftz
 */
public class Caracter {
    // Constantes para definir los tipos de caracteres
    public static final int PACMAN = 1;
    public static final int FANTASMA = 2;
    
    protected int tipo;
    protected Posicion posicion;
    protected char representacion;

    /**
     * Constructor
     * @param tipo tipo de caraccter (ver las constantes al comienzo de la clase Caracter)
     * @param posicion posición actual del caracter en fila y columna
     * @param representacion  caracter que representa a este caracter
     */
    public Caracter(int tipo, Posicion posicion, char representacion) {
        this.tipo = tipo;
        this.posicion = posicion;
        this.representacion = representacion;
    }
    
}


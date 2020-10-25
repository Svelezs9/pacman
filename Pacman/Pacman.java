

/**
 * En esta clase se guarda la información acerca del Pacman
 * El Pacman extiende la clase Caracter, y por lo tanto hereda sus
 * atributos y métodos
 * @author Helmuth Trefftz
 */
public class Pacman extends Caracter {
    // Cuántos puntos de vida tiene el Pacman
    int puntosVida;

    public Pacman(int tipo, Posicion posicion, char representacion, int puntosVida) {
        super(tipo, posicion, representacion);
        this.puntosVida = puntosVida;
    }

}

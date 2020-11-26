
/**
 * Esta clase maneja el juego
 * Se tiene una referencia al tablero y al pacman
 * En esta clase se hace la interacción con el usuario
 * @author Helmuth Trefftz
 */
import java.util.Scanner;
import java.util.Random; 
public class Juego {

    /**
     * El número de puntos iniciales de vida del pacman
     */
    public static final int PUNTOS_VIDA_INICIALES = 10;
    Tablero tablero;
    Pacman pacman;
    Fantasma[] fantasmas;
    int turnos;

    /**
     * Constructor
     * Se crea un tablero
     */
    public Juego() {
        tablero = new Tablero(this);
        turnos = 0; 

    }

    /**
     * Interacción con el usuario
     */
    public void jugar() {
        boolean ganaElJuego = false;
        tablero.dibujarTablero();
        Scanner in = new Scanner(System.in);
        String linea = in.nextLine();

        while (!linea.equals("q") && !ganaElJuego) {
            int fila = pacman.posicion.fila;
            int col = pacman.posicion.col;
            int nuevaFila = fila;
            int nuevaCol = col;
            turnos=turnos+1;
            if(turnos==10){
                pacman.puntosVida=pacman.puntosVida-1; 
                turnos=0; 
            }
            switch (linea) {
                // En este punto se inserta el código para las teclas
                // "a" y "d"
                case "w":
                nuevaFila = fila - 1;
                break;
                case "s":
                nuevaFila = fila + 1;
                break;
                case "d": 
                nuevaCol=col+1; 
                break; 
                case "a": 
                nuevaCol=col-1;
                break; 
            }

            if (validarCasilla(nuevaFila, nuevaCol)) {
                Celda anterior = tablero.tablero[fila][col];
                Celda nueva = tablero.tablero[nuevaFila][nuevaCol];
                if(nueva.caracter != null){
                    System.out.println("Has perdido el juego!");
                    break; 
                }
                if(validarCasilla(nuevaFila, nuevaCol)){
                    nueva.caracter = pacman;
                    anterior.caracter = null;
                    pacman.posicion = new Posicion(nuevaFila, nuevaCol);  
                }

                // Aquí hay que verificar si el jugador ganó el juego
                // Esto es, si llega a una parte del laberinto
                // que es una salida

                if(nueva.esSalida){
                    ganaElJuego = true; 
                    tablero.dibujarTablero();
                    break; 
                }
                if(nueva.tieneArepita && nueva.arepaMala){
                    pacman.puntosVida=pacman.puntosVida-5; 
                    nueva.tieneArepita=false; 
                } else if(nueva.tieneArepita){
                    pacman.puntosVida=pacman.puntosVida+1;
                    nueva.tieneArepita=false;
                }

            }
            if(pacman.puntosVida <= 0){
                System.out.println("Has perdido el juego!");
                break; 
            }
            boolean seMovieron = moverFantasmas();
            tablero.dibujarTablero();
            if(seMovieron){
                System.out.println("puntos de vida " + pacman.puntosVida);
                linea = in.nextLine();
            } else{ 
                System.out.println("Has perdido el juego!");
                break; 
            }
            
        }
        if(ganaElJuego) {
            System.out.println("Has ganado el juego, ¡felicitaciones!");

        }

    }

    /**
     * movimiento de los fantasmas 
     */
    public boolean moverFantasmas(){
        for(int i = 0; i < fantasmas.length; i++){
            int fila = fantasmas[i].posicion.fila;
            int col = fantasmas[i].posicion.col;
            boolean movimientoValido = false; 
            int nuevaCol = col;
            int nuevaFila = fila;
            int filaInt = fila; 
            int colInt = col; 
            if(fila == pacman.posicion.fila){
                if(col > pacman.posicion.col){
                    nuevaCol = col-2;
                    colInt = col-1;
                } else { 
                    nuevaCol = col+2;
                    colInt = col +1; 
                } 
                int movimiento = moverFantasma(fantasmas[i],fila,col,nuevaFila,nuevaCol,filaInt,colInt); 
                if (movimiento == 1) {
                    movimientoValido= true; 
                } else if (movimiento == 2){
                    return false; 
                }
            }
            else if(col == pacman.posicion.col){
                if(fila > pacman.posicion.fila){
                    nuevaFila = fila-2;
                    filaInt = fila-1;
                } else { 
                    nuevaFila = fila+2;
                    filaInt = fila+1; 
                }
                int movimiento = moverFantasma(fantasmas[i],fila,col,nuevaFila,nuevaCol,filaInt,colInt); 
                if (movimiento == 1) {
                    movimientoValido= true; 
                } else if (movimiento == 2){
                    return false; 
                }
            }

            while(!movimientoValido){
                int direccion = new Random().nextInt(4);
                nuevaFila = fila;
                filaInt = fila;
                nuevaCol = col;
                colInt = col; 

                switch(direccion){
                    case 0: 
                    nuevaFila-=2; 
                    filaInt-=1;
                    break; 
                    case 1: 
                    nuevaFila+=2;  
                    filaInt+=1;
                    break; 
                    case 2: 
                    nuevaCol-=2; 
                    colInt-=1;
                    break; 
                    case 3: 
                    nuevaCol+=2; 
                    colInt+=1;
                }
                int movimiento = moverFantasma(fantasmas[i],fila,col,nuevaFila,nuevaCol,filaInt,colInt); 
                if (movimiento == 1) {
                    movimientoValido= true; 
                } else if (movimiento == 2){
                    return false; 
                }
            }
        }
        return true; 
    }

    private int moverFantasma(Fantasma fantasma,int fila,int col, int nuevaFila, int nuevaCol, int filaInt, int colInt ){
        
        if (validarCasilla(nuevaFila, nuevaCol) && validarCasilla(filaInt, colInt)) {
            Celda anterior = tablero.tablero[fila][col];
            Celda nueva = tablero.tablero[nuevaFila][nuevaCol];
            Celda intermedia = tablero.tablero[filaInt][colInt];
            if((nueva.caracter != null && nueva.caracter.tipo == Caracter.PACMAN)
            || (intermedia.caracter != null && intermedia.caracter.tipo == Caracter.PACMAN)){
                return 2; 
            }
            nueva.caracter = fantasma;
            anterior.caracter = null;
            fantasma.posicion = new Posicion(nuevaFila, nuevaCol);
            return 1; 
        }
        return 0; 
    }

    /**
     * En este metodo se debe chequear las siguientes condiciones:
     * (i) Que el usuario no se salga de las filas del tablero
     * (ii) Que el usuario no se salga de las columnas del tablero
     * (iii) Que la posición no sea un muro
     * (iv) Que no haya un caracter en esa posición
     * 
     * @param nuevaFila Fila hacia donde se quiere mover el usuario
     * @param nuevaCol Columna hacia donde se quiere mover el usuario
     * @return true si es una jugada válida, false de lo contrario
     */
    private boolean validarCasilla(int nuevaFila, int nuevaCol) {
        // Aquí hay que verificar que sea un movimiento válido
        // Ver los comentarios del método

        //tablero.tablero.length es el numero de filas 
        if(nuevaFila>=tablero.tablero.length || nuevaFila<0){
            return false;
        }

        //la longitud de la fila que representa el numero de columnas 
        if(nuevaCol>=tablero.tablero[0].length || nuevaCol<0){
            return false;
        }

        Celda nueva = tablero.tablero[nuevaFila][nuevaCol];

        if(nueva.esMuro){
            return false; 
        }

        return true;
    }
}

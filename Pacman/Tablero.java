

/**
 * En esta clase se mantiene la información del tablero
 * El tablero es una matriz (arreglo de dos dimensiones) de Celdas
 * Es necesario tener una referencia a juego para poder acceder 
 * a la información del pacman
 * @author User
 */
import java.util.Scanner;
import java.util.Random; 

public class Tablero {
    
    Juego juego;
    Celda[][] tablero;
    int numFilas;
    int numCols;
    String[] archivo = { 
        "15 17",
        "*****************",
        "*               *",
        "* ****** ****** *",
        "* *    * *    * *",
        "*               *",
        "* *    * *    * *",
        "* ****** ****** *",
        "*               *",
        "* ****** ****** *",
        "* *    * *    * *",
        "*               *",
        "* *    * *    * *",
        "* ****** ****** *",
        "*               *",
        "*****************",
        "P 1 1",
        "O 13 15",
        "A 5"
    };

    /**
     * Constructor
     * Se recibe la referencia al juego, para poder acceder al pacman
     * Se lee el "archivo" con la información del laberinto, la posición
     * del pacman y la salida
     * @param juego 
     */
    public Tablero(Juego juego) {
        this.juego = juego;
        leerArchivo();
    }

    /**
     * En este método se lee el laberinto.
     * Cuando aprendamos archivos, se leerá la información de un archivo
     * texto.
     */
    private void leerArchivo() {
        int i = 0;
        String linea = archivo[i];
        i++;
        Scanner lineScan = new Scanner(linea);
        // Leer el tamaño del tablero en filas y columnas
        numFilas = lineScan.nextInt();
        numCols = lineScan.nextInt();
        // Definir el tamaño del tablero
        tablero = new Celda[numFilas][numCols];
        // Leer cada una de las filas que conforman el laberinto
        for (int fila = 0; fila < numFilas; fila++) {
            linea = archivo[i];
            i++;
            for (int col = 0; col < numCols; col++) {
                char c = linea.charAt(col);
                // esMuro, esSalida, tienearepita, caracter
                if (c == '*') {
                    tablero[fila][col] = new Celda(true, false, false, null);
                }
                if (c == ' ') {
                    tablero[fila][col] = new Celda(false, false, false, null);
                }
            }
        }
        // Leer la información adicional. Esto es:
        // (i) La posición del Pacman (empieza por P)
        // (ii) La posición de la salida (empieza por O)
        // En una versión futura se podrían leer las posiciones de los
        // fantasmas.
        while (i < archivo.length) {
            linea = archivo[i];
            i++;
            lineScan = new Scanner(linea.substring(1));
            if (linea.charAt(0) == 'P') {
                // La información del Pacman
                int fila = lineScan.nextInt();
                int col = lineScan.nextInt();
                Posicion posicion = new Posicion(fila, col);
                juego.pacman = new Pacman(Caracter.PACMAN, posicion, '^', Juego.PUNTOS_VIDA_INICIALES);
                tablero[posicion.fila][posicion.col].caracter = juego.pacman;
            } else if (linea.charAt(0) == 'O') {
                // La información de la salida del laberinto
                int fila = lineScan.nextInt();
                int col = lineScan.nextInt();
                tablero[fila][col].esSalida = true;
            } else if(linea.charAt(0) == 'A'){
                int na = lineScan.nextInt();
                for(int j=0;j<na;j++){
                    boolean arepitaValida = false; 
                    while(!arepitaValida){
                        int fila = new Random().nextInt(numFilas);
                        int col = new Random().nextInt(numCols) ; 
                        Celda cel = tablero[fila][col] ; 
                        if(!cel.esMuro && !cel.esSalida && !cel.tieneArepita && cel.caracter == null){
                            tablero[fila][col] = new Celda(false, false, true, null);
                            arepitaValida=true; 
                        }
                    }
                    
                }
            }
            
        }
        
        
    }

    /**
     * En este método se dibuja el tablero.
     * A cada celda se le invoca el métoco "caracterCelda", que devuelve
     * un caracter que representa el contenido de la celda.
     */
    public void dibujarTablero() {
        String s = "";
        for (int fila = 0; fila < numFilas; fila++) {
            for (int col = 0; col < numCols; col++) {
                s += tablero[fila][col].caracterCelda();
            }
            s += "\n";
        }
        System.out.println(s);
    }

}

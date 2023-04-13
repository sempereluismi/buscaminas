package org.example;

import java.util.ArrayList;

public class Juego {
    private int dificultad, largo, nMinas;
    private int[] coordenadas = new int[2];

    public Juego(int dificultad) {
        this.dificultad = dificultad;
        switch (dificultad) {
            case 1:
                this.largo = this.nMinas = 6;
                break;
            case 2:
                this.largo = this.nMinas = 10;
                break;
            case 3:
                this.largo = this.nMinas = 15;
                break;
            default:
                this.largo = this.nMinas = 6;
        }
    }

    public void main() {
        int opcion = 0;
        boolean seguir;
        Tablero t = new Tablero(largo, nMinas);
        t.crearTablero();
        ArrayList<Celda> celda = new ArrayList<Celda>();


        do {
            t.abrirTodasMinas();
            t.mostrarTablero();
            opcion = t.mostrarOpciones();
            coordenadas = t.selecionarCelda();
            seguir = t.modificarCelda(opcion, t.getCelda(coordenadas[0], coordenadas[1]));
            if (!seguir) {
                System.out.println("Has perdido");
                t.abrirTodasMinas();
                t.mostrarTablero();
                break;
            }
        } while( true );
    }
}

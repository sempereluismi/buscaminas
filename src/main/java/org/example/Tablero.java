package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Tablero {
    private int largo, nMinas;
    private Celda[][] celdas;
    private final int nMaxBanderas;
    private int nBanderas;

    public Tablero(int largo, int nMinas) {
        this.nMinas = nMinas;
        this.largo = largo;
        this.nMaxBanderas = nMinas;
        nBanderas = 0;
        celdas = new Celda[largo][largo];
    }

    public void crearTablero() {

        for (int i = 0; i < largo; i++) {
            for (int j = 0; j < largo; j++) {
                celdas[i][j] = new Celda(Estado.TAPADA, false, i, j);
            }
        }
        insertarMinas();
    }

    private void insertarMinas() {
        int x, y;
        for (int i = 0; i < nMinas; i++) {
            x = (int) (Math.random() * largo);
            y = (int) (Math.random() * largo);

            celdas[y][x].setMinada(true);
        }
    }

    public int mostrarOpciones() {
        int opt;
        Scanner s = new Scanner(System.in);

        System.out.println("1.- mostrar casilla");
        System.out.println("2.- poner bandera");
        opt = s.nextInt();

        return opt;
    }

    public void mostrarTablero() {

        System.out.print("  ");
        for( int i = 0; i < largo; i++ ) {
            System.out.print(i+1 + " ");
        }
        System.out.println();

        for (int i = 0; i < largo; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < largo; j++) {
                if (celdas[i][j].getEstado() == Estado.LIBRE) {
                    if (celdas[i][j].getMinada()) {
                        System.out.print("*" + " ");
                    } else if (getMinasAdyacentes(celdas[i][j]) != 0) {
                        System.out.print(getMinasAdyacentes(celdas[i][j]) + " ");
                    } else {
                        System.out.print(" " + " ");
                    }
                } else if(celdas[i][j].getEstado() == Estado.BANDERA) {
                    System.out.print("B" + " ");
                } else {
                    System.out.print("#" + " ");
                }
            }
            System.out.println();
        }

    }

    public int [] selecionarCelda() {
        int x, y;
        int [] coordenadas = new int [2];
        Scanner s = new Scanner(System.in);
        System.out.println("inserta la posición de la fila");
        x = s.nextInt();
        System.out.println("inserta la posición de la columna");
        y = s.nextInt();

        x--;
        y--;
        if (x < 0) {
            x = 0;
        }
        if (x > largo) {
            x = largo;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > largo) {
            y = largo;
        }

        coordenadas[0] = x;
        coordenadas[1] = y;

        return coordenadas;
    }

    public Celda getCelda(int x, int y) {
        return celdas[x][y];
    }

    public boolean abrirCelda(Celda c) {
        if (c.getMinada()) {
            return false;
        }

        if (getMinasAdyacentes(c) != 0) {
            c.setEstado(Estado.LIBRE);
            return true;
        }

        if (c.getEstado() == Estado.LIBRE) {
            return true;
        }

        c.setEstado(Estado.LIBRE);
        ArrayList<Celda> celdas = getCeldasAdyacentes(c);

        for ( Celda celda : celdas ) {
            abrirCelda(celda);
        }

        return true;
    }

    public void abrirTodasMinas() {
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[1].length; j++) {
                if (celdas[i][j].getMinada()) {
                    celdas[i][j].setEstado(Estado.LIBRE);
                }
            }
        }
    }

    public boolean comprobarCelasAbertas() {
        int celdasSinMina = (largo * largo) - nMinas;
        int contCeldasLibres = 0;
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[1].length; j++) {
                if (celdas[i][j].getEstado() == Estado.LIBRE && !(celdas[i][j].getMinada())) {
                    contCeldasLibres++;
                }
            }
        }

        return (celdasSinMina == contCeldasLibres);
    }

    public boolean modificarCelda(int opt, Celda c) {

        switch (opt) {
            case 1:
                return abrirCelda(c);
            case 2:
                if (c.getEstado() != Estado.BANDERA) {
                    if (nBanderas >= nMaxBanderas) {
                        System.out.println("El numero de banderas ya es el máximo");
                    } else {
                        c.setEstado(Estado.BANDERA);
                        nBanderas++;
                    }
                    return true;
                } else {
                    c.setEstado(Estado.TAPADA);
                    return true;
                }
            default:
                System.err.println("La opcion es invalida");
                int menuOpt = mostrarOpciones();
                modificarCelda(menuOpt, c);
                return true;
        }


    }

    private ArrayList<Celda> getCeldasAdyacentes(Celda c) {
        ArrayList<Celda> celdasAdyacentes = new ArrayList<>();
        int filaInicial = c.getCoordenadas()[0] - 1;
        int colInicial = c.getCoordenadas()[1] - 1;
        int filaCentral = c.getCoordenadas()[0];
        int colCentral = c.getCoordenadas()[1];

        for (int f = filaInicial; f < filaInicial + 3; f++) {
            for (int col = colInicial; col < colInicial + 3; col++) {
                if (f >= 0 && col >= 0 && f < largo && col < largo && !(f == filaCentral && col == colCentral)) {
                    celdasAdyacentes.add(getCelda(f, col));
                }
            }
        }

        return celdasAdyacentes;
    }

    public int getMinasAdyacentes(Celda c) {
        ArrayList<Celda> celdas;
        int count = 0;

        celdas = getCeldasAdyacentes(c);

        for (Celda celda : celdas) {
            if (celda.getMinada()) {
                count++;
            }
        }

        return count;
    }

    public boolean comprobarBanderas() {

        int count = 0;

        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[1].length; j++) {
                if ( celdas[i][j].getMinada() && celdas[i][j].getEstado() == Estado.BANDERA) {
                    count++;
                }
            }
        }
        System.out.println(count);
        return (count == nMaxBanderas);
    }

}

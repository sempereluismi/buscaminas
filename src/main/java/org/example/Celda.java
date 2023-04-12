package org.example;

public class Celda {
    private Estado estado;
    private boolean minada;
    private int x, y;

    public Celda(Estado estado,boolean minada,int x,int y) {
        this.estado = estado;
        this.minada = minada;
        this.x = x;
        this.y = y;
    }

    public Estado getEstado() {
        return estado;
    }

    public boolean getMinada() {
        return minada;
    }

    public int [] getCoordenadas () {
        return new int [] {x, y};
    }

    public void setMinada(boolean minada) {
        this.minada = minada;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}

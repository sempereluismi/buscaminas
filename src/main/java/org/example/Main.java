package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int opt;
        System.out.println("--- BUSCAMINAS ---");
        System.out.println("Elige la dificultad");
        System.out.println("1.- 6 minas 6x6");
        System.out.println("2.- 10 minas 10x10");
        System.out.println("3.- 15 minas 15x15");

        opt = s.nextInt();

        Juego j = new Juego(opt);
        j.main();
    }
}
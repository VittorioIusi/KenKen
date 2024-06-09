package main.griglia;

import main.griglia.componenti.Cell;

import java.util.Arrays;

public class Memento {
    private Cell[][] griglia;

    public Memento(Cell[][] m){
        this.griglia = m;
    }

    public Cell[][] getGriglia(){
        return this.griglia;
    }

    public void setGriglia(Cell[][] griglia){
        this.griglia = griglia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < griglia.length; i++) {
            sb.append("\n");
            for (int j = 0; j < griglia.length; j++) {
                sb.append(griglia[i][j].toString() + "\s");
            }
        }
        return sb.toString();
    }
}//Memento

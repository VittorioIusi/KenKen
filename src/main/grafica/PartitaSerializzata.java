package main.grafica;

import main.griglia.componenti.Cell;

import java.io.Serializable;

public class PartitaSerializzata implements Serializable {
    private int dim;
    private Cell[][] grid;

    public PartitaSerializzata(int dim, Cell[][] grid) {
        this.dim = dim;
        this.grid = grid;
    }


    public int getDim() {
        return dim;
    }

    public Cell[][] getGrid() {
        return grid;
    }


}//partitaSerializzato

package main.griglia;

import main.griglia.interfacce.CellIF;

public class Memento {
    private CellIF[][] griglia;

    public Memento(CellIF[][] m){
        this.griglia = m;
    }

    public CellIF[][] getGriglia(){
        return this.griglia;
    }

    public void setGriglia(CellIF[][] griglia){
        this.griglia = griglia;
    }
}//Memento

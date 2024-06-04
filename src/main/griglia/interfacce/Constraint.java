package main.griglia.interfacce;

public interface Constraint {

    //modifica
    void addCell(CellIF ec);
    void setValues();
    boolean verify();
}//Constraint

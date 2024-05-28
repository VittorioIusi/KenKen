package griglia.interfacce;

import griglia.Memento;

public interface GridIF {

    void addValue(int val,int x, int y);
    int getValue(int x, int y);
    boolean getState(int x, int y);
    void removeValue(int x, int y);
    void clean();

    void setDimension(int size);
    int getDimension();
    void setConstraint(Constraint c, int x, int y);
    Constraint getConstraint(int x,int y);
    CellIF getCell(int x, int y);
    //void changeReferenceTable(CellIF[][] table);
    //CellIF[][] getReferenceTable();
    //void setRules(Rules rules);

    boolean isLegal(int val, int x, int y);
    boolean isCompleted();


    //util
    //void switchRow(int i, int j);
    //void switchColumn(int i, int j);
    //String constrString();
    //List<Constraint> listOfConstraint();
    //Generator getGenerator();
    //Backtracking getBacktracking();
    Memento createMemento();
    void setMemento(Memento memento);
}//GridIf

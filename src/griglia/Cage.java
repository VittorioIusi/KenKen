package griglia;

import griglia.interfacce.CellIF;
import griglia.interfacce.Constraint;

import java.util.LinkedList;
import java.util.Set;

public class Cage implements Constraint {
    private LinkedList<CellIF> cells;
    private Operazione op;
    private int target;

    public Cage(){
        cells = new LinkedList<>();
    }//costruttore

    @Override
    public void addCell(CellIF ec) {
        this.cells.add(ec);
    }//addCell

    @Override
    public void setValues(int t) {
        this.target = t;
    }//setValues
}

package griglia.componenti;

import griglia.Operazione;
import griglia.interfacce.CellIF;
import griglia.interfacce.Constraint;

import java.util.LinkedList;
import java.util.List;

public class Cage implements Constraint {
    private LinkedList<CellIF> cells;
    private Operazione op;
    private int target;

    public Cage(){
        cells = new LinkedList<CellIF>();
    }//costruttore

    @Override
    public void addCell(CellIF ec) {
        this.cells.add(ec);
    }//addCell

    @Override
    public void setValues(int t) {
        this.target = t;
        this.op = Operazione.Addizione;
    }//setValues

    @Override
    public boolean verify(){
        List<Integer> l = new LinkedList<>();
        for(CellIF ec : cells){
            l.add(ec.getValue());
        }
        return op.doOp(l) == target;
    }//verify

}

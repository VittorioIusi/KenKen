package main.griglia.componenti;

import main.griglia.interfacce.CellIF;
import main.griglia.interfacce.Constraint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Cell implements CellIF {
    private int x;
    private int y;
    private int val;
    private Constraint cage;
    private boolean okCage;
    private boolean okRowCol;
    private LinkedList<CellIF> conflict;



    public Cell(int x,int y){
        this.conflict = new LinkedList<>();
        this.x = x;
        this.y = y;
        this.val = 0;
        this.okCage = false;
        this.okRowCol = false;
    }//costruttore

    public Cell(Cell c){
        this.x = c.getX();
        this.y = c.getY();
        this.val = c.getValue();
    }//costruttore

    @Override
    public void setValue(int value) {
        this.val = value;
        LinkedList<CellIF> c = Grid.verifyRowCol(this); //mi restituisce le celle in contrasto con corrente
        //System.out.println(c);
        for(CellIF cell : c){
            if(!(conflict.contains(cell))){
                conflict.add(cell);
                cell.addInContrast(this);
                cell.setRulesState(false);
            }//if
        }//for
        Iterator<CellIF> i = conflict.iterator();
        while(i.hasNext()){
            CellIF cell = i.next();
            if(!(c.contains(cell))) {
                cell.removeInContrast(this);
                cell.setRulesState(cell.getInContrast().isEmpty());
                conflict.remove(cell);
            }
        }
        //for(CellIF cell : conflict){
          //if(!(c.contains(cell))){
            //cell.removeInContrast(this);
            //cell.setRulesState(cell.getInContrast().isEmpty());
            //conflict.remove(cell);
          //}//if
        //}//for
        //System.out.println("verifico conflitti");
        this.okRowCol = Grid.verifyPositive(this) && conflict.isEmpty();
        this.okCage = true;
        if(this.cage!=null)
            this.okCage = cage.verify();
    }//setValue

    @Override
    public void setValueNC(int value) {
        this.val = value;
    }

    @Override
    public int getValue() {
        return val;
    }//getValue

    @Override
    public void clean() {
        for(CellIF cell: conflict){
            cell.removeInContrast(this);
            cell.setRulesState(cell.getInContrast().isEmpty());
        }
        conflict.clear();
        this.val = 0;
        okCage = false;
        okRowCol = false;
    }//clean

    @Override
    public int getX() {
        return x;
    }//getX

    @Override
    public int getY() {
        return y;
    }//getY

    @Override
    public void setConstraint(Constraint c) {
        //System.out.println("Sto aggiungendo un vncolo");
        this.cage = c;
        this.cage.addCell(this);
    }//setConstraint

    @Override
    public Constraint getConstraint() {
        return cage;
    }//getConstraint

    @Override
    public boolean hasConstraint() {
        return cage!=null;
    }//hasConstraint

    @Override
    public void switchValue(CellIF c){
        int tmp = this.val;
        this.val = c.getValue();
        c.setValueNC(tmp);
    }//switchValue


    @Override
    public void setCageState(boolean state) {
        this.okCage = state;
    }//setCageState

    @Override
    public void setRulesState(boolean state) {
        this.okRowCol = state;
    }//set

    @Override
    public boolean getState() {
        return okCage && okRowCol;
    }//getState


    @Override
    public void removeInContrast(CellIF c) {
        conflict.remove(c);
    }//removeInContrast

    @Override
    public void addInContrast(CellIF c) {
        conflict.add(c);
    }//addInConstrast

    @Override
    public List<CellIF> getInContrast() {
        return conflict;
    }//getInConstrast


    public String toString(){
        return String.valueOf(val);
    }//toString

}//CellIf

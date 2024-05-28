package griglia;

import griglia.interfacce.CellIF;
import griglia.interfacce.Constraint;

import java.util.List;

public class Cell implements CellIF {
    private int x;
    private int y;
    private int val;
    private Constraint cage;



    public Cell(int x,int y){
        this.x = x;
        this.y = y;
        this.val = 0;
    }//costruttore

    public Cell(Cell c){
        this.x = c.getX();
        this.y = c.getY();
        this.val = c.getValue();
    }//costruttore

    @Override
    public void setValue(int value) {
        this.val = value;
    }//setValue

    @Override
    public int getValue() {
        return val;
    }

    @Override
    public void clean() {
        this.val = 0;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setConstraint(Constraint c) {
        this.cage = c;
        this.cage.addCell(this);
    }

    @Override
    public Constraint getConstraint() {
        return cage;
    }

    @Override
    public boolean hasConstraint() {
        return cage!=null;
    }






    @Override
    public void setCageState(boolean state) {

    }

    @Override
    public void setRulesState(boolean state) {

    }

    @Override
    public boolean getState() {
        return false;
    }



    @Override
    public void removeInContrast(CellIF c) {

    }

    @Override
    public void addInContrast(CellIF c) {

    }

    @Override
    public List<CellIF> getInContrast() {
        return List.of();
    }


    public String toString(){
        return String.valueOf(val);
    }
}

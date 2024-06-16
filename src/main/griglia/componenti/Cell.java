package main.griglia.componenti;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Cell {
    private int x;
    private int y;
    private int val;
    private Cage cage;
    private boolean okCage;
    private boolean okRowCol;
    private LinkedList<Cell> conflict;



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


    public void setValue(int value) {
        //System.out.println("ho settato per il back la cella"+x+y+"con val :"+value);
        this.val = value;
        LinkedList<Cell> c = Grid.verifyRowCol(this); //mi restituisce le celle in contrasto con corrente
        //System.out.println(c);
        for(Cell cell : c){
            if(!(conflict.contains(cell))){
                conflict.add(cell);
                cell.addInContrast(this);
                cell.setRulesState(false);
            }//if
        }//for
        LinkedList<Cell> dE = new LinkedList<>();
        for(Cell cell: conflict){
            if(!(c.contains(cell))){
                dE.add(cell);
                cell.setRulesState(cell.getInContrast().isEmpty());
                cell.removeInContrast(this);
            }
        }
        conflict.removeAll(dE);
        if(!Grid.verifyPositive(this))
            this.val = 0;
        this.okRowCol = Grid.verifyPositive(this) && conflict.isEmpty();
        this.okCage = true;
        if(this.cage!=null)
            this.okCage = cage.verify();
    }//setValue


    public void setValueNC(int value) {
        this.val = value;
    }


    public int getValue() {
        return val;
    }//getValue


    public void clean() {
        for(Cell cell: conflict){
            cell.removeInContrast(this);
            cell.setRulesState(cell.getInContrast().isEmpty());
        }
        conflict.clear();
        this.val = 0;
        okCage = false;
        okRowCol = false;
    }//clean


    public int getX() {
        return x;
    }//getX


    public int getY() {
        return y;
    }//getY


    public void setConstraint(Cage c) {
        //System.out.println("Sto aggiungendo un vncolo");
        this.cage = c;
        this.cage.addCell(this);
    }//setConstraint


    public Cage getConstraint() {
        return cage;
    }//getConstraint


    public boolean hasConstraint() {
        return cage!=null;
    }//hasConstraint


    public void switchValue(Cell c){
        int tmp = this.val;
        this.val = c.getValue();
        c.setValueNC(tmp);
    }//switchValue



    public void setCageState(boolean state) {
        this.okCage = state;
    }//setCageState


    public void setRulesState(boolean state) {
        this.okRowCol = state;
    }//set


    public boolean getState() {
        return okCage && okRowCol;
    }//getState



    public void removeInContrast(Cell c) {
        conflict.remove(c);
    }//removeInContrast


    public void addInContrast(Cell c) {
        conflict.add(c);
    }//addInConstrast


    public List<Cell> getInContrast() {
        return conflict;
    }//getInConstrast


    public String toString(){
        return String.valueOf(val);
    }//toString

}//CellIf

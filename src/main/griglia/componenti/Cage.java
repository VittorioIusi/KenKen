package main.griglia.componenti;

import main.griglia.Operazione;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Cage {
    private LinkedList<Cell> cells;
    private Operazione op;
    private int target;

    public Cage(){
        cells = new LinkedList<Cell>();
    }//costruttore


    public void addCell(Cell ec) {
        this.cells.add(ec);
    }//addCell


    public void setValues() {
        setRandomOp();
        LinkedList<Integer> val = getValue();
        target=op.doOp(val);
    }//setValues



    public boolean verify(){
        LinkedList<Integer> val = getValue();
        if(!arePositive(val)||(arePositive(val)&& target==op.doOp(val)))//pienezza del cage, se ce ne sono =0 e parzialmente pieno
            validaCelle(true);
        else if(arePositive(val) && !(target==op.doOp(val)))
            validaCelle(false);
        return !arePositive(val) || op.doOp(val) == target;
    }//verify

    private boolean arePositive(List<Integer> l){
        for(Integer i: l){
            if(i<1) return false;
        }
        return true;
    }

    private void validaCelle(boolean state){
        for(Cell cell: cells) {
            cell.setCageState(state);
        }
    }



    public LinkedList<Integer> getValue(){
        LinkedList<Integer> ret = new LinkedList<>();
        for(Cell c: cells){
            ret.add(c.getValue());
        }
        return ret;
    }//getValue

    private void setRandomOp() {
        LinkedList<Operazione> op = new LinkedList<>();
        op.add(Operazione.Addizione);
        op.add(Operazione.Moltiplicazione);
        if(cells.size()==2 &&(cells.get(0).getValue()-cells.get(1).getValue())>0){
            op.add(Operazione.Sottrazione);
            if(cells.get(0).getValue()%cells.get(1).getValue()==0){
                op.add(Operazione.Divisione);
            }
        }
        int r = new Random().nextInt(op.size());
        this.op = op.get(r);
    }//setRandomOp


    public LinkedList<Cell> getCells() {
        return cells;
    }

    public Operazione getOp(){
        return op;
    }

    public int getTarget() {
        return target;
    }


    public String toString(){
        if(op.equals(Operazione.Addizione)){
            return target+"+";
        }
        if(op.equals(Operazione.Sottrazione)){
            return target+"-";
        }
        if(op.equals(Operazione.Moltiplicazione)){
            return target+"x";
        }
        return target+"/";
    }





    public boolean isRigaUnica() {
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Cell cell : cells) {
            if (cell.getY() < minY) {
                minY = cell.getY();
            }
            if (cell.getY() > maxY) {
                maxY = cell.getY();
            }
        }

        return minY == maxY;
    }

    public boolean isColonnaUnica() {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        for (Cell cell : cells) {
            if (cell.getX() < minX) {
                minX = cell.getX();
            }
            if (cell.getX() > maxX) {
                maxX = cell.getX();
            }
        }

        return minX == maxX;
    }

}

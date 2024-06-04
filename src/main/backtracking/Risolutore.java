package main.backtracking;

import main.griglia.componenti.Grid;
import main.griglia.interfacce.CellIF;
import main.griglia.interfacce.GridIF;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Risolutore extends Backtracking<CellIF,Integer> {
    private static GridIF gg;
    private int numSol = 0;


    public Risolutore(GridIF gg) {
        this.gg = gg;
    }

    @Override
    protected boolean assegnabile(CellIF cell, Integer integer) {
        boolean result = gg.isLegal(integer,cell.getX(),cell.getY());
        //System.out.println("Assegnabile " + integer + " a cella (" + cell.getX() + "," + cell.getY() + "): " + result);
        return result;
    }

    @Override
    protected void assegna(CellIF ps, Integer integer) {
        gg.addValue(integer,ps.getX(),ps.getY());
        //System.out.println("Assegna " + integer + " a cella (" + ps.getX() + "," + ps.getY() + ")");
    }

    @Override
    protected void deassegna(CellIF ps, Integer integer) {
        gg.removeValue(ps.getX(),ps.getY());
    }

    @Override
    protected void scriviSoluzione(CellIF cellIF) {
        System.out.println("AAA: ecco una soluzione");
        System.out.println(gg);
    }

    @Override
    protected boolean esisteSoluzione(CellIF cellIF) {
        if(gg.isCompleted()){
            numSol++;
            return true;
        }
        return false;
    }

    @Override
    protected boolean ultimaSoluzione(CellIF cellIF) {
        return numSol==1;
    }

    @Override
    protected List<CellIF> puntiDiScelta() {
        List<CellIF> ret = new LinkedList<>();
        for(int i=0;i<gg.getDimension();i++){
            for(int j=0;j<gg.getDimension();j++){
                ret.add(gg.getCell(i,j));
            }
        }
        return ret;
    }

    @Override
    protected Collection<Integer> scelte(CellIF cell) {
        LinkedList<Integer> ret = new LinkedList<>();
        for(int i=1; i<=gg.getDimension();i++){
            gg.isLegal(i, cell.getX(), cell.getY());
            ret.add(i);
        }
        //System.out.println("Scelte per cella (" + cell.getX() + "," + cell.getY() + "): " + ret);
        return ret;
    }
}//Risolutore

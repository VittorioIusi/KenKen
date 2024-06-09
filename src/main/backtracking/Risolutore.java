package main.backtracking;

import main.griglia.Memento;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Risolutore extends Backtracking<Cell,Integer> {
    private static Risolutore INSTANCE = null;
    private static Grid gg;
    private int numSol = 0;
    private int maxSol;
    private LinkedList<Memento> soluzioni;
    private ListIterator<Memento> lit;


    private Risolutore(Grid gg) {
        maxSol=4;
        this.gg = gg;
        soluzioni = new LinkedList<>();
        lit = soluzioni.listIterator();
    }

    public static synchronized Risolutore getInstance(Grid gg){
        if(INSTANCE==null){
            INSTANCE=new Risolutore(gg);
        }
        return INSTANCE;
    }

    @Override
    protected boolean assegnabile(Cell cell, Integer integer) {
        boolean result = gg.isLegal(integer,cell.getX(),cell.getY());
        //System.out.println("Assegnabile " + integer + " a cella (" + cell.getX() + "," + cell.getY() + "): " + result);
        return result;
    }

    @Override
    protected void assegna(Cell ps, Integer integer) {
        gg.addValue(integer,ps.getX(),ps.getY());
        //System.out.println("Assegna " + integer + " a cella (" + ps.getX() + "," + ps.getY() + ")");
    }

    @Override
    protected void deassegna(Cell ps, Integer integer) {
        gg.removeValue(ps.getX(),ps.getY());
    }

    @Override
    protected void scriviSoluzione(Cell cell) {
        System.out.println("AAA: ecco una soluzione");
        System.out.println(gg);
    }

    @Override
    protected boolean esisteSoluzione(Cell cell) {
        if(gg.isCompleted()){
            numSol++;
            Memento m = gg.createMemento();//creo un menmento con la soluzione corrente
            soluzioni.add(m);
            return true;
        }
        return false;
    }

    @Override
    protected boolean ultimaSoluzione(Cell cell) {
        return numSol==maxSol;
    }

    @Override
    protected List<Cell> puntiDiScelta() {
        List<Cell> ret = new LinkedList<>();
        for(int i=0;i<gg.getDimension();i++){
            for(int j=0;j<gg.getDimension();j++){
                ret.add(gg.getCell(i,j));
            }
        }
        return ret;
    }

    @Override
    protected Collection<Integer> scelte(Cell cell) {
        LinkedList<Integer> ret = new LinkedList<>();
        for(int i=1; i<=gg.getDimension();i++){
            gg.isLegal(i, cell.getX(), cell.getY());
            ret.add(i);
        }
        //System.out.println("Scelte per cella (" + cell.getX() + "," + cell.getY() + "): " + ret);
        return ret;
    }


    @Override
    public void risolvi(){
        soluzioni= new LinkedList<>();
        numSol=0;
        gg.clean();
        ps=puntiDiScelta();
        tentativo( primoPuntoDiScelta() );
        lit=soluzioni.listIterator();
    }//risolvi


    public void setMaxSol(int maxSol) {
        this.maxSol = maxSol;
    }

    public Memento nextSol(){
        if(lit.hasNext()){
            return lit.next();
        }
        return null;
    }//nextSol

    public Memento prevSol(){
        if(lit.hasPrevious()){
            return lit.previous();
        }
        return null;
    }//prevSol


}//Risolutore

package main.griglia.componenti;

import main.griglia.Memento;
import main.griglia.interfacce.CellIF;
import main.griglia.interfacce.Constraint;
import main.griglia.interfacce.GridIF;

import java.util.LinkedList;

public class Grid implements GridIF {
   private static CellIF[][] grid;
   private static int dimensione;
   private static Grid INSTANCE = null;

   private Grid() {}

   public static Grid getInstance() {
    if (INSTANCE == null) {
     INSTANCE = new Grid();
    }
    return INSTANCE;
   }//getInstance



    @Override
    public void addValue(int val, int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            grid[x][y].setValue(val);
    }//addValue


    @Override
    public void addValueNC(int val, int x, int y) {
       grid[x][y].setValueNC(val);
    }

    @Override
    public int getValue(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return grid[x][y].getValue();
        return -1;
    }//getValue


    @Override
    public void switchColumn(int x, int y){
        for(int i=0; i<dimensione; i++){
            grid[i][x].switchValue(grid[i][y]);
        }
    }//switchColumn

    @Override
    public void switchRow(int x,int y){
       for(int i=0; i<dimensione; i++){
           grid[x][i].switchValue(grid[y][i]);
       }
    }//switchRow



    @Override
    public boolean getState(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return grid[x][y].getState();
        return false;
    }//getState

    @Override
    public void removeValue(int x, int y) {
        grid[x][y].clean();
    }

    @Override
    public void clean() {
        for(int i = 0 ; i < dimensione ; i++){
            for(int j = 0 ; j < dimensione ; j++)
                grid[i][j].clean();
        }
    }//clean

    @Override
    public void setDimension(int size) {
        this.dimensione = size;
        grid = new CellIF[dimensione][dimensione];
        for(int i = 0 ; i < dimensione ; i++){
            for(int j = 0 ; j < dimensione ; j++)
                grid[i][j] = new Cell(i,j);
        }
    }

    @Override
    public int getDimension() {
        return dimensione;
    }

    @Override
    public void setConstraint(Constraint c, int x, int y) {
        grid[x][y].setConstraint(c);
    }

    @Override
    public Constraint getConstraint(int x,int y) {
        return grid[x][y].getConstraint();
    }

    @Override
    public CellIF getCell(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return grid[x][y];
        return null;
    }

    @Override
    public boolean isLegal(int val, int x, int y) {
       if(x>=0 && x<dimensione && y>=0 && y<dimensione) {
           addValue(val, x, y);
           boolean ret = grid[x][y].getState();
           removeValue(x, y);
           return ret;
       }
       return false;
    }

    @Override
    public boolean isCompleted() {
        for(int i=0; i<dimensione; i++){
            for(int j=0; j<dimensione; j++){
                if(!grid[i][j].getState())
                    return false;
            }
        }
        return true;
    }//isCompleted


    public static boolean verifyPositive(Cell c){
       return c.getValue()>=1 && c.getValue()<=dimensione;
    }//verifyPositive


    public static LinkedList<CellIF> verifyRowCol(Cell c){
       LinkedList<CellIF> ret = new LinkedList<>();
        for (int i = 0; i < dimensione; i++) {
            // Controllo sulla stessa riga
            if (i != c.getY() && c.getValue() == grid[c.getX()][i].getValue()) {
                ret.add(grid[c.getX()][i]);
            }
            // Controllo sulla stessa colonna
            if (i != c.getX() && c.getValue() == grid[i][c.getY()].getValue()) {
                ret.add(grid[i][c.getY()]);
            }
        }
       return ret;
    }//verifyOnGrid

    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimensione; i++) {
            sb.append("\n");
            for (int j = 0; j < dimensione; j++) {
                sb.append(grid[i][j].toString() + "\s");
            }
        }
        return sb.toString();
    }//toStirng

    @Override
    public Memento createMemento() {
        CellIF[][]ret = new CellIF[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j <  dimensione; j++) {
                ret[i][j] = grid[i][j];
            }
        }
        return new Memento(ret);
    }

    @Override
    public void setMemento(Memento memento) {
        clean();
        CellIF[][] g = memento.getGriglia();
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                addValue(g[i][j].getValue(),g[i][j].getX(),g[i][j].getY());
            }
        }
    }//setMemento

}//Grid

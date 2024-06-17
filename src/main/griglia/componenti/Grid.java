package main.griglia.componenti;
import main.backtracking.Risolutore;
import main.griglia.GeneraGriglia;
import main.griglia.Memento;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Grid implements Serializable {
   private static Cell[][] grid;
   private static int dimensione;
   private static Grid INSTANCE;

   private Grid() {}

   public static Grid getInstance() {
    if (INSTANCE == null) {
     INSTANCE = new Grid();
    }
    return INSTANCE;
   }//getInstance


    public Cell[][] getTable(){
        Cell[][] ret = new Cell[dimensione][dimensione];
        for(int i=0; i<dimensione; i++){
            for(int j=0; j<dimensione; j++)
                ret[i][j] = new Cell(grid[i][j]);
        }
        return ret;
    }



    public void addValue(int val, int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            grid[x][y].setValue(val);
    }//addValue


    public void addValueNC(int val, int x, int y) {
       grid[x][y].setValueNC(val);
    }


    public int getValue(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return grid[x][y].getValue();
        return -1;
    }//getValue



    public void switchColumn(int x, int y){
        for(int i=0; i<dimensione; i++){
            grid[i][x].switchValue(grid[i][y]);
        }
    }//switchColumn


    public void switchRow(int x,int y){
       for(int i=0; i<dimensione; i++){
           grid[x][i].switchValue(grid[y][i]);
       }
    }//switchRow




    public boolean getState(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return grid[x][y].getState();
        return false;
    }//getState


    public void removeValue(int x, int y) {
        grid[x][y].clean();
    }


    public void clean() {
       System.out.println("clean");
        for(int i = 0 ; i < dimensione ; i++){
            for(int j = 0 ; j < dimensione ; j++)
                grid[i][j].clean();
        }
    }//clean


    public void setDimension(int size) {
        this.dimensione = size;
        grid = new Cell[dimensione][dimensione];
        for(int i = 0 ; i < dimensione ; i++){
            for(int j = 0 ; j < dimensione ; j++)
                grid[i][j] = new Cell(i,j);
        }
    }


    public int getDimension() {
        return dimensione;
    }


    public void setConstraint(Cage c, int x, int y) {
        grid[x][y].setConstraint(c);
    }


    public Cage getConstraint(int x,int y) {
        return grid[x][y].getConstraint();
    }


    public Cell getCell(int x, int y) {
        if(x>=0 && x<dimensione && y>=0 && y<dimensione)
            return grid[x][y];
        return null;
    }

    public boolean isLegal(int val, int x, int y) {
       if(x>=0 && x<dimensione && y>=0 && y<dimensione) {
           addValue(val, x, y);
           boolean ret = grid[x][y].getState();
           removeValue(x, y);
           return ret;
       }
       return false;
    }


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


    public static LinkedList<Cell> verifyRowCol(Cell c){
       LinkedList<Cell> ret = new LinkedList<>();
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

    public List<Cage> listOfConstraint(){
        List<Cage> list = new LinkedList<>();
        for(int i=0; i<dimensione; i++){
            for(int j=0; j<dimensione; j++){
                if(!(list.contains(getConstraint(i,j))))
                    list.add(getConstraint(i,j));
            }
        }
        return list;
    }

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

    public GeneraGriglia getGeneratore(){
       return GeneraGriglia.getInstance(this);
    }

    public Risolutore getRisolutore(){
        return Risolutore.getInstance(this);
    }

    public Cell[][] getGridSerializzazione(){
       return grid;
    }

    public void cambiaRiferimento(Cell[][]t){
       grid=t;
    }


    public Memento createMemento() {
        Cell[][]ret = new Cell[dimensione][dimensione];
        System.out.println("sto creando un memento da aggiungere alla lista");
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j <  dimensione; j++) {
                ret[i][j] = new Cell(grid[i][j]);
            }
        }
        //System.out.println(ret);
        return new Memento(ret);
    }


    public void setMemento(Memento memento) {
        clean();
        Cell[][] g = memento.getGriglia();
        for (int i = 0; i < dimensione; i++) {
            for (int j = 0; j < dimensione; j++) {
                addValue(g[i][j].getValue(),g[i][j].getX(),g[i][j].getY());
            }
        }
    }//setMemento

}//Grid

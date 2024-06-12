package main.griglia;

import main.griglia.componenti.Cage;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import java.util.*;


public class GeneraGriglia {
    private static GeneraGriglia INSTANCE = null;
    private static Grid gg;
    private Map<Cage,LinkedList<Cell>> cageCell = new HashMap<>();

    private GeneraGriglia(Grid g){
        this.gg = g;
    }

    public static synchronized GeneraGriglia getInstance(Grid g){
        if(INSTANCE == null){
            INSTANCE = new GeneraGriglia(g);
        }
        return INSTANCE;
    }


    public void genera(){
        System.out.println("Generando...");
        riempiGriglia();
        shuffleGriglia();
        //System.out.println("griglia shufflata"+gg);
        while(true){
            Cell c = trovaCella();
            //System.out.println("inizio partendo dalla cella: "+c);
            if(c == null)
                break;
            Cage cage = new Cage();
            LinkedList<Cell> celle = new LinkedList<>();
            celle.add(c);
            gg.setConstraint(cage,c.getX(),c.getY());
            int dim = randomSize();
            //System.out.println("dimensione:"+dim);
            for(int i=0; i<dim; i++){
                Cell nextCell = trovaVicino(c);
                if(nextCell == null) {
                    //System.out.println("trova vicino ha restituito null");
                    break;
                }
                celle.add(nextCell);
                gg.setConstraint(cage,nextCell.getX(),nextCell.getY());
            }
            cageCell.put(cage,celle);
            cage.setValues();

        }//while
        //System.out.println(gg);
        gg.clean();

    }//genera


    private Cell trovaVicino(Cell c) {
        List<Esplorazione> espl = getEspl();
        Collections.shuffle(espl); // Mescola le direzioni di esplorazione
        for (Esplorazione e : espl) {
            Cell vicino = e.esplora(c);
            if (vicino != null) {
                return vicino;
            }
        }
        return null;
    }//trovaVicino







        private void riempiGriglia() {
        for(int i=0; i<gg.getDimension(); i++){
            for(int j=0; j<gg.getDimension(); j++) {
                //System.out.println("Sto riempendo");
                gg.addValueNC((i + j) % gg.getDimension()+1, i, j);
            }
        }//for
    }//riempiGriglia

    private void shuffleGriglia() {
        Random random = new Random();
        for(int i=0; i<gg.getDimension(); i++){
            int x = random.nextInt(gg.getDimension());
            int y = random.nextInt(gg.getDimension());
            if(x!=y) gg.switchRow(x,y);
            int z = random.nextInt(gg.getDimension());
            int k = random.nextInt(gg.getDimension());
            if(z!=k) gg.switchColumn(z,k);
        }
    }//shuffleGriglia

    private Cell trovaCella(){
        for(int i=0; i< gg.getDimension(); i++){
            for(int j=0; j< gg.getDimension(); j++){
                if(!gg.getCell(i,j).hasConstraint()){
                    return gg.getCell(i,j);
                }
            }
        }
        return null;
    }//trovaCella

    private int randomSize() {
        if(gg.getDimension()<4){
            return (int)(Math.random()*gg.getDimension());
        }
        return (int)(Math.random() * gg.getDimension())+2;
    }//randomSize


    private LinkedList<Esplorazione> getEspl() {
        LinkedList<Esplorazione> espl = new LinkedList<>();
        for(Esplorazione e : Esplorazione.values()){
            espl.add(e);
        }
        return espl;
    }

    public Map<Cage, LinkedList<Cell>> getCageCell() {
        return cageCell;
    }

    private enum Esplorazione {
        SOPRA{
            public Cell esplora(Cell c){
                //System.out.println("entro");
                int x = c.getX()-1;
                if(x>=0 && !(gg.getCell(x,c.getY()).hasConstraint())){
                    //System.out.println("Trovato sopra");
                    return gg.getCell(x,c.getY());
                }
                return null;
            }
        },
        SOTTO{
            public Cell esplora(Cell c){
                int x = c.getX()+1;
                if(x<gg.getDimension() && !(gg.getCell(x,c.getY()).hasConstraint())){
                    //System.out.println("Trovato sotto");
                    return gg.getCell(x,c.getY());
                }
                return null;
            }
        },
        DESTRA{
            public Cell esplora(Cell c){
                int y = c.getY()+1;
                if(y<gg.getDimension() && !(gg.getCell(c.getX(),y).hasConstraint())){
                    //System.out.println("Trovato destra");
                    return gg.getCell(c.getX(), y);
                }
                return null;
            }
        },
        SINISTRA{
            public Cell esplora(Cell c){
                int y = c.getX()-1;
                if(y>=0 && !(gg.getCell(c.getX(),y).hasConstraint())){
                    //System.out.println("Trovato sinistra");
                    return gg.getCell(c.getX(),y);
                }
                return null;
            }
        };
        public abstract Cell esplora(Cell c);
    }//Esplorazione


}//GeneraGriglia

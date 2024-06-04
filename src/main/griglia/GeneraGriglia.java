package main.griglia;

import main.griglia.componenti.Cage;
import main.griglia.componenti.Grid;
import main.griglia.interfacce.CellIF;
import main.griglia.interfacce.GridIF;

import java.util.LinkedList;
import java.util.Random;


public class GeneraGriglia {
    private static GridIF gg;

    public GeneraGriglia(GridIF g){
        this.gg = g;
    }


    public void genera(){
        riempiGriglia();
        shuffleGriglia();
        //System.out.println("griglia shufflata"+gg);
        while(true){
            CellIF c = trovaCella();
            //System.out.println("inizio partendo dalla cella: "+c);
            if(c == null)
                break;
            Cage cage = new Cage();
            gg.setConstraint(cage,c.getX(),c.getY());
            int dim = randomSize();
            //System.out.println("dimensione:"+dim);
            for(int i=0; i<dim; i++){
                CellIF nextCell = trovaVicino(c);
                if(nextCell == null) {
                    //System.out.println("trova vicino ha restituito null");
                    break;
                }
                gg.setConstraint(cage,nextCell.getX(),nextCell.getY());
            }
            cage.setValues();

        }//while
        gg.clean();

    }//genera

    private CellIF trovaVicino(CellIF c) {
        CellIF ret = null;
        LinkedList<Esplorazione> espl = getEspl();
        LinkedList<CellIF> vicini = ((Cage)c.getConstraint()).getCells();
        //System.out.println("ecco i vicini"+vicini);
        for(CellIF cell: vicini){
            for (Esplorazione e : espl) {
                CellIF vicino = e.esplora(cell);
                if (vicino != null) {
                    //System.out.println("ho trovato un vicino");
                    ret = vicino;
                    break;
                }
            }
            if (ret != null) {
                return ret;
            }
        }
        return ret;
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

    private CellIF trovaCella(){
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


    private enum Esplorazione {
        SOPRA{
            public CellIF esplora(CellIF c){
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
            public CellIF esplora(CellIF c){
                int x = c.getX()+1;
                if(x<gg.getDimension() && !(gg.getCell(x,c.getY()).hasConstraint())){
                    //System.out.println("Trovato sotto");
                    return gg.getCell(x,c.getY());
                }
                return null;
            }
        },
        DESTRA{
            public CellIF esplora(CellIF c){
                int y = c.getY()+1;
                if(y<gg.getDimension() && !(gg.getCell(c.getX(),y).hasConstraint())){
                    //System.out.println("Trovato destra");
                    return gg.getCell(c.getX(), y);
                }
                return null;
            }
        },
        SINISTRA{
            public CellIF esplora(CellIF c){
                int y = c.getX()-1;
                if(y>=0 && !(gg.getCell(c.getX(),y).hasConstraint())){
                    //System.out.println("Trovato sinistra");
                    return gg.getCell(c.getX(),y);
                }
                return null;
            }
        };
        public abstract CellIF esplora(CellIF c);
    }//Esplorazione


}//GeneraGriglia

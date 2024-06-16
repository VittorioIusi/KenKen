package test;
import main.backtracking.Risolutore;
import main.griglia.GeneraGriglia;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;
import org.junit.jupiter.api.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KenKenTest {
    private static Grid griglia;


    @BeforeEach
    public void setUp() {
        System.out.println("Generazione KenKen...");
        griglia = Grid.getInstance();
        griglia.setDimension(5);
        GeneraGriglia generatore = GeneraGriglia.getInstance(griglia);
        generatore.genera();
        System.out.println("KenKen generato!!!");
    }//setUp

    @Test
    public void inserisciValoreOk(){
        griglia.addValue(2,0,0);
        assertSame(2,griglia.getValue(0,0),"il valore inserito è 2");
        griglia.addValue(3,1,0);
        assertSame(3,griglia.getValue(1,0),"il valore inserito è 3");
    }


    @Test
    public void inserisciValoreNok(){
        griglia.addValue(-2,0,0);
        assertNotSame(-2,griglia.getValue(0,0),"numero negativo");
        griglia.addValue(7,1,1);
        assertNotSame(7,griglia.getValue(1,1),"numero grande");
    }

    @Test
    public void generatore(){
        for(int i=0; i<griglia.getDimension(); i++){
            for(int j=0; j<griglia.getDimension(); j++){
                assertTrue(griglia.getCell(i,j).hasConstraint(),"la cella: "+i+" "+j+" ha il vincolo");
            }
        }
    }


    @Test
    public void risolutore(){
        Risolutore r = Risolutore.getInstance(griglia);
        r.risolvi();
        LinkedList<Cell[][]> sol = r.getSol();
        assertFalse(sol.isEmpty(), "La lista delle soluzioni non dovrebbe essere vuota"); //non deve essere vuota
        for(Cell[][] c : sol){
            for(int i=0; i<c.length; i++){
                for(int j=0; j<c[i].length; j++){
                    assertTrue(griglia.isLegal(c[i][j].getValue(), i, j), "Ogni cella della soluzione dovrebbe essere legale");
                }
            }
        }
    }//risolutore




}//KenKenTest

package main.grafica.command;

import main.backtracking.Risolutore;
import main.grafica.mediator.ConcreteMediator;
import main.grafica.mediator.Mediator;
import main.griglia.Memento;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;

public class RisolviKenKenCommand implements Command {
    private ConcreteMediator m;

    public RisolviKenKenCommand(ConcreteMediator m) {
        this.m = m;
    }

    @Override
    public void execute() {
        System.out.println("RisolviKeKenCommand ha preso il controllo");
        Risolutore risolutore = Risolutore.getInstance(Grid.getInstance());
        //Memento mem = Grid.getInstance().createMemento();

        risolutore.risolvi();
        m.getFinestraGioco().getNextButton().setEnabled(true);
        m.getFinestraGioco().getPrevButton().setEnabled(true);
        m.getFinestraGioco().getRiprendiButton().setEnabled(true);
        m.getFinestraGioco().getRisolviButton().setEnabled(false);
        aggiornaValori();

    }

    private void aggiornaValori() {
        JTextField[][] c = m.getFinestraGioco().getCelle();
        Cell[][] g = Risolutore.getInstance(Grid.getInstance()).getSol().get(0);

        for(int i=0; i<g.length; i++){
            for(int j=0; j<g.length; j++){
                int value = g[i][j].getValue();
                if(value==0){
                    c[i][j].setText("");
                }
                else
                    c[i][j].setText(Integer.toString(value));
            }
        }
    }
}//risolviKenKenCommand

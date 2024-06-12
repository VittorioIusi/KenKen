package main.grafica.command;

import main.backtracking.Risolutore;
import main.grafica.mediator.ConcreteMediator;
import main.griglia.Memento;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;

public class RiprendiPartitaCommand implements Command {
    private ConcreteMediator mediator;

    public RiprendiPartitaCommand(ConcreteMediator mesiator) {
        this.mediator = mesiator;
    }

    @Override
    public void execute() {
        System.out.println("RiprendiPartitaCommand ha preso il controllo");
        aggiornaValori();
        mediator.getFinestraGioco().getRisolviButton().setEnabled(true);
        mediator.getFinestraGioco().getRiprendiButton().setEnabled(false);
        mediator.getFinestraGioco().getPrevButton().setEnabled(false);
        mediator.getFinestraGioco().getNextButton().setEnabled(false);


    }


    private void aggiornaValori() {
        JTextField[][] c = mediator.getFinestraGioco().getCelle();
        Grid g = Grid.getInstance();
        for(int i=0; i<g.getDimension(); i++){
            for(int j=0; j<g.getDimension(); j++){
                int value = g.getValue(i,j);
                if(value==0){
                    c[i][j].setText("");
                }
                else
                    c[i][j].setText(Integer.toString(value));
                }
        }
    }
}//riprendiPartitaCommand

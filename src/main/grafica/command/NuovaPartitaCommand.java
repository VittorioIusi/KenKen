package main.grafica.command;

import main.grafica.mediator.ConcreteMediator;
import main.grafica.mediator.Mediator;
import main.grafica.pannelli.FinestraIniziale;
import main.grafica.pannelli.FinestraSetting;
import main.griglia.componenti.Grid;

import javax.swing.*;

public class NuovaPartitaCommand implements Command {
    private ConcreteMediator m;

    public NuovaPartitaCommand(ConcreteMediator m) {
        this.m = m;
    }



    @Override
    public void execute() {
        System.out.println("NuovaParitaCommand ha preso il controllo");
        FinestraSetting finestraSetting = new FinestraSetting();
        m.setFinestraSetting(finestraSetting);
        m.getFinestraSetting().getFrame().setVisible(true);
        m.getFinestraIniziale().getFrame().setVisible(false);
        finestraSetting.setMediator(m);
        Grid g = Grid.getInstance();
    }//execute
}//nuovaPartitaCommand

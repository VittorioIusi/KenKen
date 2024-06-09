package main.grafica.command;

import main.grafica.mediator.ConcreteMediator;
import main.grafica.pannelli.FinestraGioco;
import main.griglia.componenti.Grid;

public class ConfermaImpostazioniCommand implements Command {
    private ConcreteMediator m;

    public ConfermaImpostazioniCommand(ConcreteMediator m) {
        this.m = m;
    }

    @Override
    public void execute() {
        System.out.println("Conferma impostazioniCommand ha preso il controllo");
        Grid g = Grid.getInstance();
        int dim = Integer.parseInt(m.getFinestraSetting().getDimensioneGrigliaText().getText());
        System.out.println("Dimensione griglia: " + dim);
        int sol = Integer.parseInt(m.getFinestraSetting().getNumSolBacktrackingText().getText());
        System.out.println("Num Sol: " + sol);
        g.setDimension(dim);
        g.getGeneratore().genera();
        g.getRisolutore().setMaxSol(sol);
        FinestraGioco finestraGioco = new FinestraGioco();
        m.setFinestraGioco(finestraGioco);
        m.getFinestraSetting().getFrame().setVisible(false);
        m.getFinestraGioco().getFrame().setVisible(true);
        finestraGioco.setMediator(m);
    }
}

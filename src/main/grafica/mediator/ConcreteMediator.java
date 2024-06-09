package main.grafica.mediator;

import main.grafica.command.ConfermaImpostazioniCommand;
import main.grafica.command.NuovaPartitaCommand;
import main.grafica.pannelli.FinestraGioco;
import main.grafica.pannelli.FinestraIniziale;
import main.grafica.pannelli.FinestraSetting;

public class ConcreteMediator implements Mediator {
    private static ConcreteMediator INSTANCE;
    private FinestraIniziale finestraIniziale;
    private FinestraSetting finestraSetting;
    private FinestraGioco finestraGioco;



    private ConcreteMediator(FinestraIniziale f) {
        this.finestraIniziale = f;
    }

    public static synchronized ConcreteMediator getConcreteMediator(FinestraIniziale f){
        if(INSTANCE==null)
            INSTANCE=new ConcreteMediator(f);
        return INSTANCE;
    }


    @Override
    public void notify(String sender) {
        switch (sender) {
            case "NuovaPartita":
                System.out.println("controllore nuova partita");
                new NuovaPartitaCommand(this).execute();

                break;
            case "CaricaPartita":
                // Logica per caricare una partita
                break;
            case "ConfermaImpostazioni":
                new ConfermaImpostazioniCommand(this).execute();

        }

    }//notify



    public FinestraIniziale getFinestraIniziale() {
        return finestraIniziale;
    }


    public FinestraSetting getFinestraSetting() {
        return finestraSetting;
    }


    public FinestraGioco getFinestraGioco() {
        return finestraGioco;
    }


    public void setFinestraIniziale(FinestraIniziale finestraIniziale) {
        this.finestraIniziale = finestraIniziale;
    }


    public void setFinestraSetting(FinestraSetting finestraSetting) {
        this.finestraSetting = finestraSetting;
    }


    public void setFinestraGioco(FinestraGioco finestraGioco) {
        this.finestraGioco = finestraGioco;
    }
}//ConcreteMediator

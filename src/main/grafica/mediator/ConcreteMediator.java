package main.grafica.mediator;

import main.grafica.command.*;
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
                break;
            case "Risolvi":
                new RisolviKenKenCommand(this).execute();
                break;
            case "Riprendi":
                new RiprendiPartitaCommand(this).execute();
                break;
            case "NextSol":
                new IteraSoluzioniCommand(this,"Next").execute();
                break;
            case "PrevSol":
                new IteraSoluzioniCommand(this,"Prev").execute();
                break;
            case "ControllaVincoli":
                new ControllaVincoliCommand(this).execute();
                break;
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

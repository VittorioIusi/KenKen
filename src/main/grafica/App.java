package main.grafica;

import main.grafica.mediator.ConcreteMediator;
import main.grafica.mediator.Mediator;
import main.grafica.pannelli.FinestraIniziale;

public class App {
    public static void main(String[] args) {
        FinestraIniziale f = new FinestraIniziale();
        Mediator m = ConcreteMediator.getConcreteMediator(f);
        f.setMediator(m);


    }
}

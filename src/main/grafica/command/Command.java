package main.grafica.command;

/**
 * utilizzo il pattern command che mi consente di non eseguire direttamente un operazione
 * ma creo un oggetto che sa come eseguire quell'operazione
 */

public interface Command {
    void execute();
}//Command

package main.grafica.command;

import main.grafica.mediator.ConcreteMediator;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.List;

public class ControllaVincoliCommand implements Command{
    private ConcreteMediator m;

    public ControllaVincoliCommand(ConcreteMediator m) {
        this.m = m;
    }

    public void execute() {
        System.out.println("ControllaVincoli ha preso il controllo");
        Grid g = Grid.getInstance();
        coloraCelle();
        /*
        if(g.isCompleted()){
            m.getFinestraGioco().getControllaVincoliButton().setBackground(Color.GREEN);

        }
        else{
            m.getFinestraGioco().getControllaVincoliButton().setBackground(Color.RED);
        }

         */



    }//execute

    private void coloraCelle() {
        JTextField[][] text = m.getFinestraGioco().getCelle();
        Grid g = Grid.getInstance();
        for(int i=0;i<g.getDimension();i++){
            for(int j=0;j< g.getDimension();j++){
                List<Cell> contrast = g.getCell(i,j).getInContrast();
                if(!contrast.isEmpty() && g.getCell(i,j).getValue()!=0){
                    text[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                }
                else{
                    text[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                }
            }
        }

    }//coloraCelle
}//ControllaVincoliCommand

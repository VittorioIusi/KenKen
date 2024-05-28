package griglia.interfacce;

import java.util.List;

public interface CellIF {

    void setValue(int value);
    int getValue();
    void clean();

    int getX();
    int getY();
    void setConstraint(Constraint c);
    Constraint getConstraint();
    void setCageState(boolean state);
    void setRulesState(boolean state);
    boolean getState();


    boolean hasConstraint();
    void removeInContrast(CellIF c);
    void addInContrast(CellIF c);
    List<CellIF> getInContrast();

}//CellIN

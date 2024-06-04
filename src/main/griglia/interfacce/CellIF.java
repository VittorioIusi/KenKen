package main.griglia.interfacce;

import java.util.List;

public interface CellIF {

    void setValue(int value);
    int getValue();
    void clean();
    void setValueNC(int value);

    int getX();
    int getY();
    void setConstraint(Constraint c);
    Constraint getConstraint();
    void setCageState(boolean state);
    void setRulesState(boolean state);
    boolean getState();

    void switchValue(CellIF c);
    boolean hasConstraint();
    void removeInContrast(CellIF c);
    void addInContrast(CellIF c);
    List<CellIF> getInContrast();

}//CellIN

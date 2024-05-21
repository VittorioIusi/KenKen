package griglia;

import java.util.Arrays;

public class Cage {
    private final int target;
    private final Point[] cagePoint;
    private final Operazione cageOperation;

    public Cage(int target, Point[] cagePoint, Operazione cageOperation) {
        if(cagePoint == null || cagePoint.length == 0)
            throw new IllegalArgumentException("inseirre almeno un punto nel cage");
        if((cageOperation == Operazione.Divisione || cageOperation == Operazione.Sottrazione)&&(cagePoint.length!=2))
            throw new IllegalArgumentException("Divisione e sottrazione accetano solo 2 punti");
        if(target<1)
            throw new IllegalArgumentException("Il target deve essere maggiore o uguale ad 1");
        this.target = target;
        this.cagePoint = cagePoint;
        this.cageOperation = cageOperation;
    }//costruttore

    public int getTarget() {
        return target;
    }

    public Point[] getCagePoint() {
        return cagePoint;
    }

    public Operazione getCageOperation() {
        return cageOperation;
    }

    public boolean equals(Object o){
        if(this == o)return true;
        if(!(o instanceof Cage))return false;
        Cage cage = (Cage) o;
        if(target != cage.target)return false;
        if(cageOperation != cage.cageOperation)return false;
        return Arrays.equals(cagePoint, cage.cagePoint);
    }//equals

    public int hashCode() {
        int result = target;
        result = 31 * result + (cageOperation != null ? cageOperation.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(cagePoint);
        return result;
    }

    @Override
    public String toString() {
        return "Cage{" +
                "target=" + target +
                ", cagePoint=" + Arrays.toString(cagePoint) +
                ", cageOperation=" + cageOperation +
                '}';
    }
}//cage

package griglia;

import java.util.Map;
import java.util.Set;

public class Puzzle {
    private final int size;
    private final Set<Cage> cages;
    private final Map<Point,Cage> pointToCage;

    private Puzzle(PuzzleBuilder builder) {
        if(builder == null)
            throw new NullPointerException("builder is null");
        this.size = builder.size;
        this.cages = builder.cages;
        this.pointToCage = builder.pointToCage;
    }//costruttore

    public int getSize() { return size; }
    public Set<Cage> getCages() { return cages; }
    public Map<Point,Cage> getPointToCage() { return pointToCage; }

    public static class PuzzleBuilder{
        private int size;
        private Set<Cage> cages;
        private Map<Point,Cage> pointToCage;

        public PuzzleBuilder(int size){
            this.size = size;
        }

        public PuzzleBuilder addCage(int target, Operazione op, Point... points){
            Cage cage = new Cage(target,points,op);
            cages.add(cage);
            for(Point p: points){
                if(pointToCage.containsKey(p))
                    throw new RuntimeException("Il puzzle contine glia un cage nel punto"+ p);
                pointToCage.put(p,cage);
            }
            return this;
        }//addCage

        public PuzzleBuilder addCage(Cage cage){
            cages.add(cage);
            for(Point p : cage.getCagePoint()){
                if(pointToCage.containsKey(p))
                    throw new RuntimeException("Il puzzle contine glia un cage nel punto"+ p);
                pointToCage.put(p,cage);
            }
            return this;
         }//addCage

        public Puzzle build(){
            checkAllPointAreInCage();
            return new Puzzle(this);
        }

        private void checkAllPointAreInCage() {
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    Point point = new Point(i, j);
                    if (!pointToCage.containsKey(point))
                        throw new RuntimeException("Alcune celle non sono state inserite nei cage");
                }
            }
        }

    }//builder

    @Override
    public String toString() {
        return "Puzzle{" +
                "size=" + size +
                ", cages=" + cages +
                ", pointToCage=" + pointToCage +
                '}';
    }
}//puzzle

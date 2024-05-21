package griglia;

import java.util.Objects;

public class Point implements Comparable<Point> {
    private int x;
    private int y;

    public Point(int x, int y) {
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinate errate");
        this.x = x;
        this.y = y;
    }//costruttore

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }//equals

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int compareTo(Point o) {
        int cmp = this.x - o.x;
        if (cmp != 0) {
            return cmp;
        }
        return this.y - o.y;
    }//compareTo
}//Point

import java.util.Objects;

public class Node {
    private int G;
    private int H;
    private int F;

    @Override
    public String toString() {
        return "Node{" +
                "G=" + G +
                ", H=" + H +
                ", F=" + F +
                ", location=" + location +
                '}';
    }

    private Point location;

    public Node(int G, int H, int F, Point location)
    {
        this.G = G;
        this.H = H;
        this.F = F;
        this.location = location;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getH() {
        return H;
    }

    public int getF() {
        return F;
    }

    public Point getLocation() {
        return location;
    }


    public int compareTo(Node n)
    {
        return Integer.compare(F, n.F);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return Objects.equals(location, node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}

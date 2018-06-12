package fibHeap;

import domain.Film;


import java.util.ArrayList;
import java.util.LinkedList;

public class FibHeap {
    public FibHeap() {
        this.roots = new LinkedList<>();
    }

    public LinkedList<Node> getRoots() {
        return roots;
    }

    public void setRoots(LinkedList<Node> roots) {
        this.roots = roots;
    }

    public Node min() {
        if (this.roots.isEmpty())
            return null;
        else return this.roots.get(0);
    }

    public int size() {
        return this.n;
    }

    public void insert(Node x){
        if (this.roots.isEmpty())
            this.roots.addLast(x);
        else if (x.compareTo(this.roots.get(0)) < 0)
            this.roots.addFirst(x);
        else
            this.roots.addLast(x);
        n++;
    }

    static FibHeap union(FibHeap h1, FibHeap h2) {
        FibHeap union = new FibHeap();
        for (Node e : h1.roots) {
            union.insert(e);
        }
        for (Node e : h2.roots) {
            union.insert(e);
        }
        return union;
    }

    public Node extractMin() {
        Node min = this.min();
        if (min != null) {
            for (int i = 0; i < min.children.size(); i++) {
                this.roots.addLast(min.children.get(i));
                min.children.get(i).p = null;
            }
            this.roots.remove(0);
            consolidate();
            n--;
        }
        return min;
    }

    private LinkedList<Node> roots;
    private int n;

    private void consolidate() {
        int maxPossibleNodeDegree = (int) Math.floor(Math.log(this.n) / Math.log(1.618));
        ArrayList<Node> A = new ArrayList<>();
        for (int i = 0; i <= maxPossibleNodeDegree; i++) {
            A.add(null);
        }
        for (int i = 0; i < this.roots.size(); i++) {
            Node w = this.roots.get(i);
            int degree = w.children.size();
            while (A.get(degree) != null) {
                Node y = A.get(degree);
                if (w.compareTo(y) > 0) {
                    Film wFilm = w.film;
                    w.film = y.film;
                    y.film = wFilm;
                }
                this.roots.remove(y);
                w.children.addLast(y);
                y.mark = false;
                A.set(degree, null);
                if (degree <= i)
                    i--;
                degree++;

            }
            A.set(degree, w);
        }
        this.roots.clear();
        for (int i = 0; i < A.size(); i++) {
            if (A.get(i) != null) {
                this.insert(A.get(i));
            }
        }
    }

    public void decreaseKey(Node x, double k) {
        if (k > x.getFilm().getHypeLevel())
            return;
        x.getFilm().setHypeLevel(k);
        Node y = x.p;
        if (y != null && x.compareTo(y) < 0) {
            cut(x, y);
            cascadingCut(y);
        }
    }

    private void cut(Node x, Node y) {
        y.children.remove(x);
        this.insert(x);
        x.p = null;
        x.mark = false;
    }

    private void cascadingCut(Node y) {
        Node z = y.p;
        if (z != null) {
            if (y.mark == Boolean.FALSE)
                y.mark = Boolean.TRUE;
            else {
                cut(y, z);
                cascadingCut(z);
            }

        }
    }

    public ArrayList<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<>(this.n);
        for (int i = 0; i < this.roots.size(); i++) {
            appendTree(this.roots.get(i), nodes);
        }
        return nodes;
    }

    public void appendTree(Node root, ArrayList<Node> nodes) {
        nodes.add(root);
        for (int i = 0; i < root.children.size(); i++) {
            appendTree(root.children.get(i), nodes);
        }
    }

    public void deleteNode(Node node) {
        this.decreaseKey(node, Double.MIN_VALUE);
        this.extractMin();
    }
}

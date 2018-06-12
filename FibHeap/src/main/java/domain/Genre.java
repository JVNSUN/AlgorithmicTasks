package domain;

import fibHeap.FibHeap;
import fibHeap.Node;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Genre {
    private String name;

    private FibHeap fibHeap = new FibHeap();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FibHeap getFibHeap() {
        return fibHeap;
    }

    public void setFibHeap(FibHeap fibHeap) {
        this.fibHeap = fibHeap;
    }

    public void addFilm(Film film) {
        Node node = new Node(film);
        fibHeap.insert(node);
    }

    public Film extractReceded() {
        Node node = fibHeap.extractMin();
        if (node != null)
            return node.getFilm();
        else return null;
    }

    public int size() {
        return this.fibHeap.size();
    }

    public ArrayList<Film> availableFilms() {
        ArrayList<Node> nodes = fibHeap.getNodes();
        ArrayList<Film> films = new ArrayList<>(this.fibHeap.size());
        for (int i = 0; i < nodes.size(); i++) {
            films.add(nodes.get(i).getFilm());
        }
        return films;
    }

    public void decreaseHypeLevel(Node node, double newLevel) {
        fibHeap.decreaseKey(node, newLevel);
    }

    public void deleteNode(Node node) {
        fibHeap.deleteNode(node);
    }
}

package fibHeap;

import domain.Film;

import java.util.LinkedList;

public class Node implements Comparable<Node> {
    public Node(Film film) {
        this.children = new LinkedList<>();
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }

    @Override
    public int compareTo(Node o) {
        return this.film.compareTo(o.film);
    }

    Node p;
    LinkedList<Node> children;
    boolean mark;
    Film film;
}

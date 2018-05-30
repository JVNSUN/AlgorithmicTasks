package domain;

import OrderStatisticTree.OrderStatisticTree;
import OrderStatisticTree.OSTNode;

public class Genre {
    private String name;

    private OrderStatisticTree<Film> tree = new OrderStatisticTree<Film>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OSTNode<Film> getFilmNode(String title) {
        OSTNode<Film> node = tree.find(Film.getFilmWithTitle(title));
        if (node == null || node.isEmpty())
            return null;
        else
            return node;

    }

    public void addFilm(Film film) {
        tree.insert(film);
    }

    public void delete(Film film) {
        tree.remove(film);
    }

    public Film osSelect(int i) {
        return tree.osSelect(i);
    }

    public int osRank(OSTNode<Film> x) {
        return tree.osRank(x);
    }

    public void printFilms() {
        tree.print();
    }

    public int size() {
        return this.tree.size();
    }

    public void printTreeToFile() {
        this.tree.printTreeToFile();
    }
}

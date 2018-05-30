package domain;

import optimalBST.Node;
import optimalBST.OptimalBST;

public class Genre {
    private String name;

    private OptimalBST tree = new OptimalBST();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void addFilm(Film film) {
        Node node = new Node(film);
        tree.data.add(node);
    }

    public void printFilms() {
        for (int i = 0; i < tree.data.size(); i++) {
            if (i%2 == 1) {
                System.out.println(tree.data.get(i).data);
            }
        }
    }

    public int size() {
        return this.tree.size();
    }

    public Film getFilmWithTitle(String title) {
        Film film = Film.getFilmWithTitle(title);
        return tree.find(film);
    }

    public void build() {
        this.tree.build();
    }
}

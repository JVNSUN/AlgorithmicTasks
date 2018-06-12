package domain;

import java.util.ArrayList;

public class VideoStore {
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    private ArrayList<Genre> genres = new ArrayList<>();
}

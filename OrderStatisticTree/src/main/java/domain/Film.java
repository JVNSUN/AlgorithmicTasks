package domain;

public class Film implements Comparable<Film> {
    private String title;
    private Integer year;
    private Double rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int compareTo(Film o) {
        return this.title.compareTo(o.title);
    }

    public static Film getFilmWithTitle(String title) {
        Film film = new Film();
        film.setTitle(title);
        return film;
    }

    @Override
    public String toString() {
        return title;
    }
}

package com.jansun.domain;
import java.util.Objects;

public class Film {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title) &&
                Objects.equals(year, film.year) &&
                Objects.equals(rating, film.rating);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
        //return (hash==Integer.MAX_VALUE)?(Integer.MAX_VALUE-1):hash;
    }
}

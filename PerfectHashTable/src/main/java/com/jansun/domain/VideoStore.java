package com.jansun.domain;

import com.jansun.hash.PerfectHashTable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class VideoStore {
    @XmlElementWrapper(name = "genres")
    @XmlElement(name = "genre")
    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public void createTablesForGenres() {
        for (Genre genre : genres) {
            genre.filmsHashTable = new PerfectHashTable<>(genre.getFilms());
        }
    }

    private ArrayList<Genre> genres;
}

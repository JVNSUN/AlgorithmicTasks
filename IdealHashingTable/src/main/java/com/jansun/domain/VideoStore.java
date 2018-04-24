package com.jansun.domain;

import com.jansun.hash.PerfectHashTable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;

@XmlRootElement
public class VideoStore {
    @XmlElementWrapper(name = "genres")
    @XmlElement(name = "genre")
    public LinkedList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(LinkedList<Genre> genres) {
        this.genres = genres;
    }

    public void createTablesForGenres() {
        for (Genre genre : genres) {
            genre.filmsHashTable = new PerfectHashTable<>(genre.getFilms());
        }
    }

    private LinkedList<Genre> genres;
}

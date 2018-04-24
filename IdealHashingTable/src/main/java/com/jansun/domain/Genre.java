package com.jansun.domain;

import com.jansun.hash.PerfectHashTable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedList;

public class Genre {
    private String name;

    private LinkedList<Film> films;

    @XmlTransient
    public PerfectHashTable<Film> filmsHashTable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    public LinkedList<Film> getFilms() {
        return films;
    }

    public void setFilms(LinkedList<Film> films) {
        this.films = films;
    }

    public Film getFilm(String title) {
        return filmsHashTable.find(title);
    }
}

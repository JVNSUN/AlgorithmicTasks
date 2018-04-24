package com.jansun.domain;

import com.jansun.hash.PerfectHashTable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;

public class Genre {
    private String name;

    private ArrayList<Film> films;

    @XmlTransient
    PerfectHashTable<Film> filmsHashTable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "films")
    @XmlElement(name = "film")
    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    public Film getFilm(String title) {
        return filmsHashTable.find(title);
    }
}

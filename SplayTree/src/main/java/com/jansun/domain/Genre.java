package com.jansun.domain;


import com.jansun.tree.SplayTree;
import com.jansun.tree.SplayTreeNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedList;

public class Genre {
    private String name;

    private LinkedList<Film> films;

    @XmlTransient
    SplayTree<Film> filmsSplayTree;

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
        SplayTreeNode<Film> node = filmsSplayTree.search(Film.getFilmWithTitle(title));
        if (node == null)
            return null;
        else
            return node.getKey();

    }

    public void addFilm(Film film) {
        filmsSplayTree.insert(film);
    }

    public void fullfillSplayTree() {
        filmsSplayTree = new SplayTree<Film>();
        Film film;
        while ((film = films.poll()) != null)
            filmsSplayTree.insert(film);

    }

    public void delete(Film film) {
        this.filmsSplayTree.delete(film);
    }

    public void printFilms() {
        this.filmsSplayTree.print();
    }
}

package com.jansun.domain;

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

    public void createTreesForGenres() {
        for (Genre genre : genres) {
            genre.fullfillSplayTree();
        }
    }

    private ArrayList<Genre> genres;
}

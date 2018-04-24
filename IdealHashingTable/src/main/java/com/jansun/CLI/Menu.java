package com.jansun.CLI;

import com.jansun.dataService.DataService;
import com.jansun.dataService.XMLDataService;
import com.jansun.domain.Film;
import com.jansun.domain.Genre;
import com.jansun.domain.VideoStore;

import java.util.LinkedList;

public class Menu {
    public static void start() {
        if (init()) {
            run();
        }
    }

    private static boolean init() {
        printMainMenu();
        int choice1 = CLIUtil.readChoice();
        if (choice1 != 1) {
            if (choice1 != 0)
                System.out.println("Invalid input!");
            return false;
        } else {
            CLIUtil.printChooseFileMenu();
            String fileName = CLIUtil.readWanted();
            DataService dataService = new XMLDataService();
            videoStore = dataService.loadData(fileName);
            videoStore.createTablesForGenres();
            return true;
        }
    }
    private static void run() {
        CLIUtil.clearScreen();
        printDataMenu();
        int choice1 = CLIUtil.readChoice();
        if (choice1 != 1) {
            if (choice1 != 0)
                System.out.println("Invalid input!");
                CLIUtil.waitEnterPress();
        } else {
            int nGenres = printGenres();
            int choice2 = CLIUtil.readChoice();
            if (choice2 > nGenres || choice2 < 1) {
                System.out.println("Invalid input!");
                CLIUtil.waitEnterPress();
            } else {
                printFilms(choice2);
                String filmTitle = CLIUtil.readWanted();
                printFilm(choice2, filmTitle);
                CLIUtil.waitEnterPress();
                run();
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("1. Load data from xml");
        System.out.println("0. Quit");
        System.out.print("Enter the number of the option above: ");
    }

    private static void printDataMenu() {
        System.out.println("1. Find data");
        System.out.println("0. Quit");
        System.out.print("Enter the number of the option above: ");
    }

    private static int printGenres() {
        CLIUtil.clearScreen();
        LinkedList<Genre> genres = videoStore.getGenres();
        int counter;
        for (counter = 0; counter < genres.size(); counter++) {
            System.out.println(String.valueOf(counter + 1) + ". " + genres.get(counter).getName());
        }
        System.out.print("Enter the number of the genre above: ");
        return counter;
    }

    private static void printFilms(int genreIndex) {
        CLIUtil.clearScreen();
        LinkedList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        LinkedList<Film> films = genre.getFilms();
        int counter;
        for (counter = 0; counter < films.size(); counter++) {
            System.out.println(String.valueOf(counter + 1) + ". " + films.get(counter).getTitle());
        }
        System.out.print("Enter the title of the film above: ");
    }

    private static void printFilm(int genreIndex, String filmTitle) {
        CLIUtil.clearScreen();
        LinkedList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        Film film = genre.getFilm(filmTitle);
        if (film == null) {
            System.out.println("No such film is present");
        }
        else {
            System.out.println("Title:   " + film.getTitle());
            System.out.println("Rating:   " + film.getRating());
            System.out.println("Year:   " + film.getYear());
        }
    }

    private static VideoStore videoStore;

}

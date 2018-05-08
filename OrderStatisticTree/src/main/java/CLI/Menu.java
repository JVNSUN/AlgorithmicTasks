package CLI;

import data.DataService;
import data.XMLDataService;
import domain.Film;
import domain.Genre;
import domain.VideoStore;

import java.util.ArrayList;

public class Menu {
    public static void start() {
        CLIUtil.clearScreen();
        if (init()) {
            run();
        } else {
            CLIUtil.waitEnterPress();
            start();
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
            if (videoStore == null) {
                return false;
            }
            //videoStore.createTreesForGenres();
            return true;
        }
    }
    private static void run() {
        CLIUtil.clearScreen();
        int nGenres = printGenres();
        int genreChoice = CLIUtil.readChoice();
        if (genreChoice > nGenres || genreChoice < 1) {
            System.out.println("Invalid input!");
            CLIUtil.waitEnterPress();
        } else {
            Genre genre = videoStore.getGenres().get(genreChoice-1);
            printDataMenu();
            int actionChoice = CLIUtil.readChoice();
            switch (actionChoice) {
                case 1: {
                    printFilms(genreChoice);
                    String title = CLIUtil.readWanted();
                    printFilm(genreChoice, title);
                    break;
                }
                case 2: {
                    System.out.print("Enter the film title: ");
                    String title = CLIUtil.readWanted();
                    System.out.print("Enter the film year: ");
                    int year = CLIUtil.readChoice();
                    System.out.print("Enter the film rating: ");
                    double rating = CLIUtil.readDouble();
                    Film film = new Film();
                    film.setTitle(title);
                    film.setRating(rating);
                    film.setYear(year);
                    //genre.addFilm(film);
                    System.out.println("Added");
                    break;
                }
                case 3: {
                    printFilms(genreChoice);
                    String title = CLIUtil.readWanted();
                    deleteFilm(genreChoice, title);
                    System.out.println("Deleted");
                    break;
                }
            }
            CLIUtil.waitEnterPress();
            run();
        }
    }

    private static void printMainMenu() {
        System.out.println("1. Load data from xml");
        System.out.println("0. Quit");
        System.out.print("Enter the number of the option above: ");
    }

    private static void printDataMenu() {
        System.out.println("1. Find data");
        System.out.println("2. Insert data");
        System.out.println("3. Delete data");
        System.out.println("0. Quit");
        System.out.print("Enter the number of the option above: ");
    }

    private static int printGenres() {
        CLIUtil.clearScreen();
        ArrayList<Genre> genres = videoStore.getGenres();
        int counter;
        for (counter = 0; counter < genres.size(); counter++) {
            System.out.println(String.valueOf(counter + 1) + ". " + genres.get(counter).getName());
        }
        System.out.print("Enter the number of the genre above: ");
        return counter;
    }

    private static void printFilms(int genreIndex) {
        CLIUtil.clearScreen();
        ArrayList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        //genre.printFilms();
        System.out.print("Enter the title of the film above: ");
    }

    private static void printFilm(int genreIndex, String filmTitle) {
        CLIUtil.clearScreen();
        ArrayList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        /*Film film = genre.getFilm(filmTitle);
        if (film == null) {
            System.out.println("No such film is present");
        }
        else {
            System.out.println("Title:   " + film.getTitle());
            System.out.println("Rating:   " + film.getRating());
            System.out.println("Year:   " + film.getYear());
        }*/
    }

    private static void deleteFilm(int genreIndex, String filmTitle) {
        CLIUtil.clearScreen();
        ArrayList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        Film film = Film.getFilmWithTitle(filmTitle);
        //genre.delete(film);
    }

    private static VideoStore videoStore;

}

package CLI;

import data.CSVDataService;
import data.DataService;
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
            DataService dataService = new CSVDataService();
            videoStore = dataService.loadData(fileName);
            return true;
        }
    }

    private static void run() {
        CLIUtil.clearScreen();
        int nGenres = printGenres();
        int genreChoice = CLIUtil.readChoice();
        if (genreChoice > nGenres || genreChoice < 0) {
            System.out.println("Invalid input!");
        }
        else if (genreChoice != 0) {
            CLIUtil.clearScreen();
            Genre genre = videoStore.getGenres().get(genreChoice-1);
            printChosenGenre(genre);
            printDataMenu();
            int actionChoice = CLIUtil.readChoice();
            if (actionChoice == 1) {
                genre.printFilms();
                System.out.println("Enter the title from above: ");
                String theTitle = CLIUtil.readWanted();
                Film theFilm = genre.getFilmWithTitle(theTitle);
                printFilm(theFilm);
            }
            CLIUtil.waitEnterPress();
            run();
        }
    }

    private static void printMainMenu() {
        System.out.println("1. Load data from csv");
        System.out.println("0. Quit");
        System.out.print("Enter the number of the option above: ");
    }

    private static void printDataMenu() {
        System.out.println("1. Find film by title");
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
        System.out.println("0. Exit");
        System.out.print("Enter the number of the genre above: ");
        return counter;
    }

    private static void printFilm(Film film) {
        if (film == null) {
            System.out.println("No such film is present");
        }
        else {
            System.out.println("Title:   " + film.getTitle());
            System.out.println("Rating:   " + film.getRating());
            System.out.println("Year:   " + film.getYear());
        }
    }

    private static void printChosenGenre(Genre genre) {
        System.out.println("Chosen genre : " + genre.getName());
    }

    private static VideoStore videoStore;

}

package CLI;

import OrderStatisticTree.OSTNode;
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
            switch (actionChoice) {
                case 1: {
                    System.out.print("Enter integer " + genre.size() + " >= i >= 1: ");
                    int i = CLIUtil.readChoice();
                    if (i < 1 || i > genre.size()) {
                        System.out.println("Invalid value!");
                    }
                    else {
                        Film film = genre.osSelect(i);
                        printFilm(film);
                    }
                    break;
                }
                case 2: {
                    genre.printFilms();
                    System.out.print("Enter the title from the list above: ");
                    String title = CLIUtil.readWanted();
                    OSTNode<Film> filmNode = genre.getFilmNode(title);
                    if (filmNode == null) {
                        System.out.println("Invalid title");
                    }
                    else {
                        int rank = genre.osRank(filmNode);
                        System.out.println("Current film's rank is " + rank);
                    }
                    break;
                }
                case 3: {
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
                    genre.addFilm(film);
                    System.out.println("Added");
                    break;
                }
                case 4: {
                    printFilms(genreChoice);
                    String title = CLIUtil.readWanted();
                    deleteFilm(genreChoice, title);
                    System.out.println("Deleted");
                    break;
                }
                case 5: {
                    printFilms(genreChoice);
                    break;
                }
                case 6: {
                    genre.printTreeToFile();
                    break;
                }
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
        System.out.println("1. Find i-th order statistic");
        System.out.println("2. Find film's rank");
        System.out.println("3. Insert film");
        System.out.println("4. Delete film");
        System.out.println("5. Print films");
        System.out.println("6. Display tree schema to file tree.txt");
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

    private static void printFilms(int genreIndex) {
        CLIUtil.clearScreen();
        ArrayList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        genre.printFilms();
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

    private static void deleteFilm(int genreIndex, String filmTitle) {
        CLIUtil.clearScreen();
        ArrayList<Genre> genres = videoStore.getGenres();
        Genre genre = genres.get(genreIndex-1);
        Film film = Film.getFilmWithTitle(filmTitle);
        genre.delete(film);
    }

    private static void printChosenGenre(Genre genre) {
        System.out.println("Chosen genre : " + genre.getName());
    }

    private static VideoStore videoStore;

}

package CLI;

import data.CSVDataService;
import data.DataService;
import domain.Film;
import domain.Genre;
import domain.VideoStore;
import fibHeap.Node;

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
                    genre.addFilm(readFilmInput());
                    System.out.println("Added");
                    break;
                }
                case 2: {
                    Film film = genre.extractReceded();
                    System.out.println("Receded film: ");
                    printFilm(film);
                    break;
                }
                case 3: {
                    Node chosen = getFilm(genre);
                    if (chosen != null) {
                        System.out.print("Enter new hype level: ");
                        double newHype = CLIUtil.readDouble();
                        genre.decreaseHypeLevel(chosen, newHype);
                    }
                    break;
                }
                case 4: {
                    Node chosen = getFilm(genre);
                    if (chosen != null) {
                        genre.deleteNode(chosen);
                    }
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
        System.out.println("1. Insert a new film");
        System.out.println("2. Extract the receded film");
        System.out.println("3. Decrease film's hype level");
        System.out.println("4. Delete a film");
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
            System.out.println("Hype level:   " + film.getHypeLevel());
        }
    }

    private static void printChosenGenre(Genre genre) {
        System.out.println("Chosen genre : " + genre.getName());
    }

    private static Film readFilmInput() {
        System.out.print("Enter the film title: ");
        String title = CLIUtil.readWanted();
        System.out.print("Enter the film year: ");
        int year = CLIUtil.readChoice();
        System.out.print("Enter the film rating: ");
        double rating = CLIUtil.readDouble();
        System.out.print("Enter the film hype level: ");
        double hype = CLIUtil.readDouble();
        Film film = new Film();
        film.setTitle(title);
        film.setRating(rating);
        film.setYear(year);
        film.setHypeLevel(hype);
        return film;
    }

    private static int printFilms(ArrayList<Film> films) {
        int counter;
        for (counter = 0; counter < films.size(); counter++) {
            System.out.println(String.valueOf(counter + 1) + ". " + films.get(counter).toString() + " " + films.get(counter).getHypeLevel());
        }
        System.out.println("0. Exit");
        System.out.print("Enter the number of the genre above: ");
        return counter;
    }

    private static Node getFilm(Genre genre) {
        ArrayList<Film> availableFilms = genre.availableFilms();
        int nAvailableFilms = printFilms(availableFilms);
        int filmChoiceIndex = CLIUtil.readChoice();
        if (filmChoiceIndex > nAvailableFilms || filmChoiceIndex < 0) {
            System.out.println("Invalid input!");

        }
        else if (filmChoiceIndex != 0){
            Node filmChoice = genre.getFibHeap().getNodes().get(filmChoiceIndex-1);
            return filmChoice;
        }
        return null;
    }

    private static VideoStore videoStore;

}

package data;

import domain.Film;
import domain.Genre;
import domain.VideoStore;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVDataService implements DataService{
    @Override
    public VideoStore loadData(String resource) {
        VideoStore videoStore = new VideoStore();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(resource))) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
            String currentGenreName = "";
            Genre currentGenre = new Genre();

            for (CSVRecord csvRecord : parser) {
                String genreName = csvRecord.get(0);
                if (!genreName.equals(currentGenreName)) {
                    if (!currentGenreName.equals("")) {
                        videoStore.addGenre(currentGenre);
                    }
                    currentGenre = new Genre();
                    currentGenre.setName(genreName);
                    currentGenreName = genreName;
                }
                Film film = new Film();
                String title = csvRecord.get(1);
                film.setTitle(title);
                int year = Integer.parseInt(csvRecord.get(2));
                film.setYear(year);
                double rating = Double.parseDouble(csvRecord.get(3));
                film.setRating(rating);
                double hype = Double.parseDouble(csvRecord.get(4));
                film.setHypeLevel(hype);
                currentGenre.addFilm(film);
            }
            if (!currentGenreName.equals("")) {
                videoStore.addGenre(currentGenre);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return videoStore;
    }
}

package data;

import domain.VideoStore;

public interface DataService {
    VideoStore loadData(String resource);
}

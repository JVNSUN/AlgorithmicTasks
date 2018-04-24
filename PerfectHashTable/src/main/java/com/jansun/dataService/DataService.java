package com.jansun.dataService;

import com.jansun.domain.VideoStore;

public interface DataService {
    VideoStore loadData(String resource);
}

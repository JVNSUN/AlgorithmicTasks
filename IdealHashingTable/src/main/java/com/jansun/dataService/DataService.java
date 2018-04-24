package com.jansun.dataService;

import com.jansun.domain.VideoStore;

import java.io.File;

public interface DataService {
    VideoStore loadData(String resource);
}

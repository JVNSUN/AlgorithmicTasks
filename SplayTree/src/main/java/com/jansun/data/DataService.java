package com.jansun.data;

import com.jansun.domain.VideoStore;

public interface DataService {
    VideoStore loadData(String resource);
}

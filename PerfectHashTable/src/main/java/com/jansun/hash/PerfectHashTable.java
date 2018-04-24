package com.jansun.hash;

import java.util.ArrayList;

public class PerfectHashTable<T> {
    public PerfectHashTable(ArrayList<T> list) {
        this.list = list;
        createTable();
        hashFunction = new UniversalHashFunction(list.size());
        hashItems();
        for (HashTable<T> e : tables) {
            e.hashItems();
        }
    }

    public T find(String s) {
        int hash = hashFunction.computeHash(s.hashCode());
        return tables.get(hash).find(s);
    }

    public ArrayList<T> getList() {
        return this.list;
    }

    private void createTable() {
        tables = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            tables.add(new HashTable<>());
        }
    }

    private void hashItems() {
        for (T e : list) {
            int hash = hashFunction.computeHash(e.hashCode());
            tables.get(hash).getList().add(e);
        }
    }

    private ArrayList<HashTable<T>> tables;
    private UniversalHashFunction hashFunction;
    private ArrayList<T> list;
}

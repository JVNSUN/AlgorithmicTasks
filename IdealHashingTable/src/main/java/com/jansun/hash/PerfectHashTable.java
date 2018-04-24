package com.jansun.hash;

import java.util.ArrayList;
import java.util.LinkedList;

public class PerfectHashTable<T> {
    public PerfectHashTable(LinkedList<T> list) {
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

    private void createTable() {
        tables = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            tables.add(new HashTable<T>());
        }
    }

    private void hashItems() {
        T e;
        while ((e = list.poll()) != null) {
            int hash = hashFunction.computeHash(e.hashCode());
            tables.get(hash).getList().add(e);
        }
    }

    private ArrayList<HashTable<T>> tables;
    private UniversalHashFunction hashFunction;
    private int m;
    private LinkedList<T> list;
}

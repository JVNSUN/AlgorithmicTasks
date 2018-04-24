package com.jansun.hash;

import java.util.ArrayList;
import java.util.LinkedList;

class HashTable<T> {
    HashTable() {
        list = new LinkedList<>();
    }

    void hashItems() {
        if (!createTable())
            return;
        hashFunction = new UniversalHashFunction(size);
        T e;
        while ((e = list.poll()) != null) {
            int hash = hashFunction.computeHash(e.hashCode());
            table.get(hash).add(e);
        }
    }

    private boolean createTable() {
        size = list.size() * list.size();
        if (size == 0)
            return false;
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<>());
        }
        return true;
    }

    T find(String s) {
        int hash = hashFunction.computeHash(s.hashCode());
        if (hash < table.size()) {
            LinkedList<T> bucket = table.get(hash);
            for (T e : bucket) {
                if (e.hashCode() == s.hashCode())
                    return e;
            }
        }
        return null;
    }

      LinkedList<T> getList() {
        return list;
    }

    private ArrayList<LinkedList<T>> table;
    private LinkedList<T> list;
    private int size;
    private UniversalHashFunction hashFunction;
}

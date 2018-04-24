package com.jansun.hash;

import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable<T> {
    public HashTable() {
        list = new LinkedList<T>();
    }

    protected void hashItems() {
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
        table = new ArrayList<LinkedList<T>>(size);
        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<T>());
        }
        return true;
    }

    public T find(String s) {
        int hash = hashFunction.computeHash(s.hashCode());
        LinkedList<T> bucket = table.get(hash);
        for (T e : bucket) {
            if (e.hashCode() == s.hashCode())
                return e;
        }
        return null;
    }

    public ArrayList<LinkedList<T>> getTable() {
        return table;
    }

    public void setTable(ArrayList<LinkedList<T>> table) {
        this.table = table;
    }

    public LinkedList<T> getList() {
        return list;
    }

    public void setList(LinkedList<T> list) {
        this.list = list;
    }

    public UniversalHashFunction getHashFunction() {
        return hashFunction;
    }

    public void setHashFunction(UniversalHashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    private ArrayList<LinkedList<T>> table;
    private LinkedList<T> list;
    private int size;
    private UniversalHashFunction hashFunction;
}

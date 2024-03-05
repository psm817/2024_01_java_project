package org.example.dao;

public abstract class Dao {

    // 자식에게만 lastId 변수를 물려주겠다. (public, private를 쓰지않고)
    protected int lastId;

    Dao() {
        lastId = 0;
    }

    public int getLastId() {
        return lastId;
    }

    public int getNewId() {
        return lastId + 1;
    }
}

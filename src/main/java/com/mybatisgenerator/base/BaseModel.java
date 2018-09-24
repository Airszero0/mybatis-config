package com.mybatisgenerator.base;


public abstract class BaseModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuid() {
        return guid;
    }

    public void setGuid(int guid) {
        this.guid = guid;
    }

    private int id;
    private int guid;
}

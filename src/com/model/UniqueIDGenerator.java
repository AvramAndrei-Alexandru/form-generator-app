package com.model;


public class UniqueIDGenerator {

    private int currentId;

    private  UniqueIDGenerator() {
        currentId = 0;
    }
    private static UniqueIDGenerator instance;
    public static UniqueIDGenerator getUniqueIDGenerator() {
        if(instance == null) {
            instance = new UniqueIDGenerator();
            return instance;
        }else {
            return instance;
        }
    }

    public int generateUniqueID() {
        currentId += 1;
        return currentId;
    }

    public void resetId() {
        currentId = 0;
    }

}

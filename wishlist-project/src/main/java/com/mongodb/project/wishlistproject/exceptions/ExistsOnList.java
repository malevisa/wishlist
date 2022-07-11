package com.mongodb.project.wishlistproject.exceptions;

public class ExistsOnList extends RuntimeException{

    public ExistsOnList(String message) {
        super(message);
    }
}

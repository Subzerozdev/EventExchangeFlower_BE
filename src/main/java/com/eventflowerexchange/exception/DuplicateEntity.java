package com.eventflowerexchange.exception;

public class DuplicateEntity extends  RuntimeException{

    public DuplicateEntity (String message) {
        super (message);
    }
}

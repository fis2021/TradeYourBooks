package org.loose.tyb.exceptions;

public class UsernameAlreadyExistsException extends Exception {

    private final String username;

    public UsernameAlreadyExistsException(String username) {
        super(String.format("An account with the username %s already exists!", username));
        this.username = username;
    }

}

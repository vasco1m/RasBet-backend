package net.java.rasbetbackend.exception;

public class AgeException extends Exception {

    public AgeException() {
        super("You should have more than 18 years!");
    }

    public AgeException(String errorMessage) {
        super(errorMessage);
    }
}
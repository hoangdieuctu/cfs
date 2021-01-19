package com.exercise.cfs.exception;

public class CFSException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766939L;

    public CFSException() {
        super();
    }

    public CFSException(String message) {
        super(message);
    }

    public CFSException(String message, Throwable cause) {
        super(message, cause);
    }
}

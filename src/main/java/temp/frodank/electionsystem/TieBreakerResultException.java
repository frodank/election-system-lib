/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

/**
 * An exception indicating that a {@link TieBreaker} has returned the wrong result.
 *
 * @author frodank
 */
public class TieBreakerResultException extends RuntimeException {

    /**
     * @see RuntimeException#RuntimeException() 
     */
    public TieBreakerResultException() {
        super();
    }

    /**
     * @param message
     * @see RuntimeException#RuntimeException(java.lang.String) 
     */
    public TieBreakerResultException(String message) {
        super(message);
    }

    /**
     * @param cause
     * @see RuntimeException#RuntimeException(java.lang.Throwable) 
     */
    public TieBreakerResultException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @see RuntimeException#RuntimeException(java.lang.String, java.lang.Throwable) 
     */
    public TieBreakerResultException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

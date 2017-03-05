/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

/**
 *
 * @author frodank
 */
public class TieBreakerResultException extends RuntimeException {

    public TieBreakerResultException() {
        super();
    }

    public TieBreakerResultException(String message) {
        super(message);
    }

    public TieBreakerResultException(Throwable cause) {
        super(cause);
    }

    public TieBreakerResultException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

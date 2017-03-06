/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.logging;

/**
 * A simple log message with both a regular message, and a detailed message.
 *
 * @author frodank
 */
public class LogMessage implements Log {
    private final String message;
    private final String detailedMessage;

    /**
     * This constructor sets both the simple and detailed log message to the 
     * value.
     * 
     * @param message A log message
     */
    public LogMessage(String message) {
        this.message = message;
        this.detailedMessage = message;
    }
    
    /**
     * This constructor sets both a simple and detailed log message.
     * 
     * @param message A simple log message
     * @param detailedMessage A detailed log message
     */
    public LogMessage(String message, String detailedMessage) {
        this.message = message;
        this.detailedMessage = detailedMessage;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailedMessage() {
        return detailedMessage;
    }
    
}

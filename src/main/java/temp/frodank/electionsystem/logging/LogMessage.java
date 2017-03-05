/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.logging;

/**
 *
 * @author frodank
 */
public class LogMessage implements Log {
    private final String message;
    private final String detailedMessage;

    public LogMessage(String message) {
        this.message = message;
        this.detailedMessage = message;
    }
    
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

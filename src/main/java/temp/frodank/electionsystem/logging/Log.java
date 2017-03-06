/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.logging;

/**
 * Used for all logging done when calculating election-results. Useful for 
 * understanding why an election-result ended up as it did. Contains a couple
 * of methods for getting default log-entry-messages
 * @author frodank
 */
public interface Log {
    /**
     * Returns a simple log-message for the log-entry.
     * 
     * @return a log message
     */
    String getMessage();
    /**
     * Returns a detailed log-message for the log-entry. Could be the same
     * message as {@link #getMessage()}.
     * 
     * @return a detailed log message
     */
    String getDetailedMessage();
}

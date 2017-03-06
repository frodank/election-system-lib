/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.logging;

import java.util.Map;
import temp.frodank.electionsystem.Choice;

/**
 * A log message detailing the number of votes each candidate gets at a given 
 * time in the calculation.
 *
 * @author frodank
 */
public class LogVoteCount implements Log {
    
    private final Map<? extends Choice, ? extends Number> voteCount;

    /**
     * Constructor
     * 
     * @param voteCount A map containing the candidates in the count (key) and the number of votes they have
     */
    public LogVoteCount(Map<? extends Choice, ? extends Number> voteCount) {
        this.voteCount = voteCount;
    }
    
    /**
     * Returns a string containing each candidate and their votes in the form of:
     * "(candidate): (votes)" separated by a delimiter.
     * 
     * @param delim The delimiter to separate the candidates
     * @return string with formatted candidate+vote-data
     */
    private String getVoteCountAsString(String delim) {
        String result ="";
        boolean first=true;
        for (Map.Entry<? extends Choice, ? extends Number> entrySet : voteCount.entrySet()) {
            Choice key = entrySet.getKey();
            Number value = entrySet.getValue();
            result+= (first ? "" : delim) + key.getName() + ": " + value;
            first = false;
        }
        return result;
    }
    
    @Override
    public String getMessage() {
        return getVoteCountAsString(", ");
    }

    @Override
    public String getDetailedMessage() {
        return getVoteCountAsString("\n");
    }
    
}

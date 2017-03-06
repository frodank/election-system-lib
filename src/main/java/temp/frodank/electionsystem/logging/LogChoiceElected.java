/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.logging;

import temp.frodank.electionsystem.Choice;

/**
 *
 * A log message indicating a candidate is elected, and how many votes the person 
 * has been elected by.
 * 
 * @author frodank
 */
public class LogChoiceElected implements Log {
    private Choice elected;
    private Long numberOfVotes;

    /**
     * Constructor
     * 
     * @param elected The candidate which was elected to a position
     * @param numberOfVotes The number of votes the candidate was elected by
     */
    public LogChoiceElected(Choice elected, Long numberOfVotes) {
        this.elected = elected;
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public String getMessage() {
        return elected.getName() + " was elected";
    }

    @Override
    public String getDetailedMessage() {
        return elected.getName() + " was elected by " + numberOfVotes + " votes.";
    }
    
    
}

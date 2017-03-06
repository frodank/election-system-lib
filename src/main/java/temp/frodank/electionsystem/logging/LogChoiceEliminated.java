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
 * A log message indicating a candidate is eliminated from being elected, and 
 * how many votes the person had.
 * 
 * @author frodank
 */
public class LogChoiceEliminated implements Log {
    private Choice eliminated;
    private Long numberOfVotes;

    /**
     * Constructor
     * 
     * @param eliminated The candidate that was eliminated
     * @param numberOfVotes The number of votes the candidate had
     */
    public LogChoiceEliminated(Choice eliminated, Long numberOfVotes) {
        this.eliminated = eliminated;
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public String getMessage() {
        return eliminated.getName() + " was eliminated";
    }

    @Override
    public String getDetailedMessage() {
        return eliminated.getName() + " was eliminated. He had " + numberOfVotes + " votes at the time of elimination.";
    }
}

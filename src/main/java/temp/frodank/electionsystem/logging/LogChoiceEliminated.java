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
 * @author frodank
 */
public class LogChoiceEliminated implements Log {
    private Choice eliminated;
    private Long numberOfVotes;

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
        return eliminated.getName() + " was eliminated by " + numberOfVotes + " votes.";
    }
}

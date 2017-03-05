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
public class LogChoiceElected implements Log {
    private Choice elected;
    private Long numberOfVotes;

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

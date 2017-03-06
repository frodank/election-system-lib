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
 * A log message indicating a candidate has been chosen by a tie-breker.
 *
 * @author frodank
 */
public class LogTieBreakChoice implements Log{
    private final Choice choice;

    /**
     * Constructor
     * 
     * @param choice The candidate chosen by tie-breaker
     */
    public LogTieBreakChoice(Choice choice) {
        this.choice = choice;
    }

    @Override
    public String getMessage() {
        return choice.getName() + " chosen by tie-break.";
    }

    @Override
    public String getDetailedMessage() {
        return getMessage();
    }
}

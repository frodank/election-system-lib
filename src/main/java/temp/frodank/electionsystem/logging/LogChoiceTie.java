/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.logging;

import java.util.List;
import temp.frodank.electionsystem.Choice;

/**
 *
 * @author frodank
 */
public class LogChoiceTie implements Log {
    private final List<? extends Choice> choices;
    private final Number tiedSpots;

    public LogChoiceTie(List<? extends Choice> elected, Number tiedSpots) {
        this.choices = elected;
        this.tiedSpots = tiedSpots;
    }

    @Override
    public String getMessage() {
        return choices + " became tied";
    }

    @Override
    public String getDetailedMessage() {
        if(tiedSpots == null)
            return choices + " became tied for elimination.";
        else
            return choices + " became tied for " + tiedSpots + " spot(s).";
    }
}

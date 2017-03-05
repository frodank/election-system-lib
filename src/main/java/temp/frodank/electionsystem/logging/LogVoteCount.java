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
 *
 * @author frodank
 * @param <U>
 */
public class LogVoteCount implements Log {
    
    private final Map<? extends Choice, Long> voteCount;

    public LogVoteCount(Map<? extends Choice, Long> voteCount) {
        this.voteCount = voteCount;
    }
    
    private String getVoteCountAsString(String delim) {
        String result ="";
        boolean first=true;
        for (Map.Entry<? extends Choice, Long> entrySet : voteCount.entrySet()) {
            Choice key = entrySet.getKey();
            Long value = entrySet.getValue();
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

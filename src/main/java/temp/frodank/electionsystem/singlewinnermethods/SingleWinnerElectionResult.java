/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.HashMap;
import java.util.Map;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.ElectionResult;

/**
 * An interface for use in election-systems that returns a single winner. The 
 * {@link ElectionResult#getWinningCandidates() }-method is implemented to return
 * a map containing only the winner (if there is one), with the value 1.
 * 
 * @author frodank
 * @param <U> The type of {@link Choice} used in the election
 */
public interface SingleWinnerElectionResult<U extends Choice<U>> extends ElectionResult<Integer, U> {
    /**
     * Returns the winner. null indicates that this is a {@link TiedSingleWinnerElectionResult}.
     * 
     * @return The winner, or null if there is none
     */
    U getWinner();

    @Override
    public default Map<U, Integer> getWinningCandidates() {
        Map m = new HashMap();
        U winner = getWinner();
        if(winner != null)
            m.put(winner, 1);
        return m;
    }
}

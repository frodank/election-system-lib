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
 *
 * @author frodank
 * @param <U>
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
        m.put(getWinner(), 1);
        return m;
    }
}

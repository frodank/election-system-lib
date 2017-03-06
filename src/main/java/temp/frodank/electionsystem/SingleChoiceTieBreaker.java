/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import temp.frodank.electionsystem.logging.Log;

/**
 * A tie-breaker which returns a single choice.
 *
 * @author frodank
 * @param <T> The type which determines the number of spots the tie-breaker should determine
 * @param <U> The type of {@link Choice} used by the tie-breaker
 * @param <V> The type of {@link Vote} to use.
 * @param <W> The type of {@link BallotBox} to use
 */
@FunctionalInterface
public interface SingleChoiceTieBreaker<T extends Number, U extends Choice<U>, V extends Vote<? extends Number,U, V>, W extends BallotBox<V,W>> extends TieBreaker<T,U,V,W> {

    @Override
    default Map<U, T> breakTie(List<U> choices, W ballotBox, T spots, List<Log> log) {
        Map m = new HashMap();
        U u = breakTie(choices, ballotBox, log);
        if(u != null)
            m.put(u, spots);
        return m;
    }
    
    /**
     * Breaks a tie between given choices.
     * 
     * @param choices A list of candidates to break a tie between
     * @param ballotBox The ballotbox containing the votes thus far in the election. Any implementation must make sure to not edit this object, but rather a copy of it.
     * @param log The log containing information of calculation of the election
     * @return The candidate which is selected. If null is returned, election-systems will in most cases treat that as a tie result.
     */
    U breakTie(List<U> choices, W ballotBox, List<Log> log);
}

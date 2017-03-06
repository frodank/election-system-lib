/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.List;
import java.util.Map;
import temp.frodank.electionsystem.logging.Log;

/**
 * A tie-breaker which returns the candidates and how many spots each candidate should be awarded.
 * 
 * @author frodank
 * @param <T> The type which determines the number of spots the tie-breaker should determine
 * @param <U> The type of {@link Choice} used by the tie-breaker
 * @param <V> The type of {@link Vote} to use.
 * @param <W> The type of {@link BallotBox} to use
 */
@FunctionalInterface
public interface TieBreaker<T extends Number, U extends Choice<U>, V extends Vote<? extends Number,U, V>, W extends BallotBox<V,W>> {
    /**
     * Method for breaking a tie, where multiple candidates can be the winner.
     * 
     * @param choices A list of the candidates
     * @param ballotBox A ballotbox containing the votes so far in the election. Any implementation must make sure to not edit this object, but rather a copy of it.
     * @param spots The number of spots that the candidates are tied for
     * @param log The log containing information of calculation of the election
     * @return A map containing the winning candidates of the tie-break (key), and how many spots they were awarded (value)
     */
    Map<U, T> breakTie(List<U> choices, W ballotBox, T spots, List<Log> log);
}

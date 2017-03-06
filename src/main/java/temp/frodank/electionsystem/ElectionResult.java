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
 * This is the result returned by {@link Calculator#calculateElection(temp.frodank.electionsystem.BallotBox, temp.frodank.electionsystem.ElectionSystem) }
 * 
 *
 * @author frodank
 * @param <T> What Number representation the number of candidates are
 * @param <U> What instance of Choice the winning candidates are
 */
public interface ElectionResult<T extends Number, U extends Choice> {
    /**
     * Returns the winning candidates in a map with the choice as key and number
     * of candidates as a number.
     * If null is returned the result is likely a {@link TiedElectionResult}.
     * 
     * @return Winning candidates
     */
    Map<U, T> getWinningCandidates();
    
    /**
     * Gets a log containing the detailed calculation for how the candidates was
     * selected.
     *
     * @return A list of Logs
     */
    List<Log> getDetailedLog();
}

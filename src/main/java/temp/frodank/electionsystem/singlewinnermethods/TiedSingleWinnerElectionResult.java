/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.TiedElectionResult;
import temp.frodank.electionsystem.logging.Log;

/**
 * An implementation of a tied election result in an election where a single 
 * winner was expected.
 *
 * @author frodank
 * @param <U> The type of {@link Choice} used in the election
 */
public class TiedSingleWinnerElectionResult<U extends Choice<U>> implements TiedElectionResult<Integer, U>, SingleWinnerElectionResult<U>{

    private final List<U> tiedCandidates;
    private final List<Log> log;
    
    /**
     * Constructor
     * 
     * @param tiedCandidates A list of the candidates that were tied for being a winner. Expects at least two candidates.
     * @param log A log which details the calculation that shows the calculation which determined the tie.
     */
    public TiedSingleWinnerElectionResult(List<U> tiedCandidates, List<Log> log) {
        if(tiedCandidates == null)
            throw new NullPointerException();
        if(tiedCandidates.size() < 2)
            throw new IllegalArgumentException("List of tied candidates cannot be less than 2.");
        this.tiedCandidates = tiedCandidates;
        this.log = log;
    }

    @Override
    public Map<U, Integer> getWinningCandidates() {
        return null;
    }

    @Override
    public List<Log> getDetailedLog() {
        return log;
    }

    @Override
    public U getWinner() {
        return null;
    }

    @Override
    public Map<List<U>, Integer> getTiedCandidates() {
        HashMap m = new HashMap<>();
        m.put(tiedCandidates,1);
        return m;
    }
    
    /**
     * Returns the list of candidates tied for a single-winner election.
     * 
     * @return The list of the tied candidates
     */
    public List<U> getSingleWinnerTiedCandidates() {
        return tiedCandidates;
    }

}

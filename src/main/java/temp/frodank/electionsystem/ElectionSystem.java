/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The super-class for all election-systems. Is used by {@link Calculator#calculateElection(temp.frodank.electionsystem.BallotBox, temp.frodank.electionsystem.ElectionSystem) }
 * in combination with a ballotbox, to determine the result of an election.
 * 
 * @author frodank
 * @param <T> The type of {@link ElectionResult} returned by this election-system
 * @param <V> The type of {@link Vote} to use.
 * @param <W> The type of {@link BallotBox} to use
 */
public abstract class ElectionSystem<T extends ElectionResult, V extends Vote, W extends BallotBox<V,W>> {
    /**
     * Used by {@link Calculator#calculateElection(temp.frodank.electionsystem.BallotBox, temp.frodank.electionsystem.ElectionSystem) }
     * to determine whether the ballot-box sent to {@link #calculateResult(temp.frodank.electionsystem.BallotBox)}
     * should be a copy of the original or not. Should only be set to false in
     * cases where the election-system-implementation does not modify any objects,
     * or any of the objects referenced by these objects, in the ballot-box sent
     * to calculateResult.
     * 
     * @return 
     */
    protected boolean needACopyOfVotesBeforeCalculation() {
        return true;
    }
    
    /**
     * Determines and returns a result represented by this election system.
     * 
     * @param ballotBox A ballot-box used in the calculation. If an implementation edits this in any way {@link #needACopyOfVotesBeforeCalculation()} must return its default value of true.
     * @return The result of the election determined by this system
     */
    public abstract T calculateResult(W ballotBox);
    
    /**
     * A helper-method for the election-system-implementation to sort candidates
     * by the number votes they get.
     * 
     * @param <U> The type of choice to sort
     * @param votes a map containing the candidates (key) and how many votes each gets (value)
     * @return A sorted {@link LinkedHashMap} containing the candidates (key) and how many votes each gets (value)
     */
    protected <U extends Choice<U>> Map<U, Long> sortedChoicesByVotes(Map<U, Long> votes) {
        List<U> sortedList = new ArrayList<>(votes.keySet());
        Collections.sort(sortedList, (U o1, U o2) -> (int) (votes.get(o2)-votes.get(o1)));
        Map<U, Long> sortedMap = new LinkedHashMap<>();
        for (U choice : sortedList) {
            sortedMap.put(choice, votes.get(choice));
        }
        return sortedMap;
    }
}

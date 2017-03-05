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
 *
 * @author frodank
 * @param <T>
 * @param <V>
 * @param <W>
 */
public abstract class ElectionSystem<T extends ElectionResult, V extends Vote, W extends BallotBox<V,W>> {
    protected boolean needACopyOfVotesBeforeCalculation() {
        return true;
    }
    public abstract T calculateResult(W ballotBox);
    
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

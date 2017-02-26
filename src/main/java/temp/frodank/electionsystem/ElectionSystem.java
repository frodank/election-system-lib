/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.ArrayList;
import java.util.Collection;
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
    
    protected Map<Choice, Long> sortedChoicesByVotes(Map<Choice, Long> votes) {
        List<Choice> sortedList = new ArrayList<>(votes.keySet());
        Collections.sort(sortedList, (Choice o1, Choice o2) -> (int) (votes.get(o2)-votes.get(o1)));
        Map<Choice, Long> sortedMap = new LinkedHashMap<>();
        for (Choice choice : sortedList) {
            sortedMap.put(choice, votes.get(choice));
        }
        return sortedMap;
    }
}

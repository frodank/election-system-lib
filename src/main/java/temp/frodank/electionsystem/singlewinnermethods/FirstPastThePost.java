/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import temp.frodank.electionsystem.BallotBox;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.ElectionSystem;
import temp.frodank.electionsystem.SingleChoiceTieBreaker;
import temp.frodank.electionsystem.Vote;
import temp.frodank.electionsystem.logging.Log;
import temp.frodank.electionsystem.logging.LogChoiceElected;
import temp.frodank.electionsystem.logging.LogChoiceTie;
import temp.frodank.electionsystem.logging.LogTieBreakChoice;
import temp.frodank.electionsystem.logging.LogVoteCount;

/**
 *
 * @author frodank
 * @param <V>
 * @param <W>
 */
public class FirstPastThePost<V extends Vote<? extends Choice, ? extends Vote>, W extends BallotBox<V,W>> extends ElectionSystem<SingleWinnerElectionResult, V,W>{

    private SingleChoiceTieBreaker tieBreaker;

    public FirstPastThePost(SingleChoiceTieBreaker tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public FirstPastThePost() {
        this.tieBreaker = null;
    }

    public SingleChoiceTieBreaker getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(SingleChoiceTieBreaker tieBreaker) {
        this.tieBreaker = tieBreaker;
    }
    
    @Override
    protected boolean needACopyOfVotesBeforeCalculation() {
        return false;
    }

    @Override
    public SingleWinnerElectionResult calculateResult(W ballotbox) {
        HashMap<Choice, Long> tally = new HashMap<>();
        List<Log> log = new ArrayList<>();
        for (V vote : ballotbox.getVotes()) {
            Choice c = vote.getPrioritizedList().peek();
            Long n = tally.get(c);
            if(n==null)
                n=0L;
            n+=vote.getWeight();
            tally.put(c, n);
        }
        Map<Choice, Long> orderedResult = sortedChoicesByVotes(tally);
        log.add(new LogVoteCount(orderedResult));
        List<Choice> winners = new ArrayList<>();
        Long winningNumber = null;
        for (Map.Entry<Choice, Long> entrySet : orderedResult.entrySet()) {
            Choice key = entrySet.getKey();
            Long value = entrySet.getValue();
            if(winningNumber == null || Objects.equals(value, winningNumber)) {
                winners.add(key);
                winningNumber = value;
            }
        }
        if(winners.size() != 1) {
            log.add(new LogChoiceTie(winners, 1));
            if(tieBreaker == null)
                return new TiedSingleWinnerElectionResult(winners, log);
            Choice winnerByTie = tieBreaker.breakTie(winners, ballotbox);
            if(winnerByTie == null)
                return new TiedSingleWinnerElectionResult(winners, log);
            log.add(new LogTieBreakChoice(winnerByTie));
            return new SimpleSingleWinnerElectionResult(winnerByTie, log);
        }
        log.add(new LogChoiceElected(winners.get(0), winningNumber));
        return new SimpleSingleWinnerElectionResult(winners.get(0), log);
    }
    
}

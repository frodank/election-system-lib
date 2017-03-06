/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
 * A first past the post election system. Used in the UK and US elections, among
 * others. The election system doesn't take into account any voter-preferences
 * other than the top-ranked candidate.
 * 
 * <p>
 * 
 * The first past the post tallies the number of votes each candidate gets, and
 * declares the candidate with the most votes as the winner. The winning 
 * candidate is not guaranteed to have won by a majority of the votes.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/First-past-the-post_voting">https://en.wikipedia.org/wiki/First-past-the-post_voting</a>
 *
 * @author frodank
 * @param <V> The type of {@link Vote} to use. It expects the weight of the vote to be a Long. It will ignore any other than the most preferred candidate stated on each vote.
 * @param <U> The type of {@link Choice} used in the election
 * @param <W> The type of {@link BallotBox} to use
 */
public class FirstPastThePost<V extends Vote<Long, U, V>, U extends Choice<U>, W extends BallotBox<V,W>> extends ElectionSystem<SingleWinnerElectionResult, V,W>{

    private SingleChoiceTieBreaker<Integer,U,V,W> tieBreaker;

    /**
     * Constructor. Takes a {@link SingleChoiceTieBreaker} as an argument, in 
     * case there is a tie among candidates.
     * 
     * @param tieBreaker The tie-breaker used in cases where there is a tie between multiple candidates. If null, or if {@link SingleChoiceTieBreaker#breakTie(java.util.List, temp.frodank.electionsystem.BallotBox, java.util.List)} returns null, a {@link TiedSingleWinnerElectionResult} is returned by {@link #calculateResult(temp.frodank.electionsystem.BallotBox) }.
     */
    public FirstPastThePost(SingleChoiceTieBreaker<Integer,U,V,W> tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    /**
     * Constructor. Sets tie-breaker as null, which accepts tie-results.
     * 
     * @see #FirstPastThePost(temp.frodank.electionsystem.SingleChoiceTieBreaker) 
     */
    public FirstPastThePost() {
        this.tieBreaker = null;
    }

    /**
     * @return 
     * @see #FirstPastThePost(temp.frodank.electionsystem.SingleChoiceTieBreaker) 
     */
    public SingleChoiceTieBreaker<Integer,U,V,W> getTieBreaker() {
        return tieBreaker;
    }

    /**
     * @param tieBreaker 
     * @see #FirstPastThePost(temp.frodank.electionsystem.SingleChoiceTieBreaker) 
     */
    public void setTieBreaker(SingleChoiceTieBreaker<Integer,U,V,W> tieBreaker) {
        this.tieBreaker = tieBreaker;
    }
    
    @Override
    protected boolean needACopyOfVotesBeforeCalculation() {
        return false;
    }

    @Override
    public SingleWinnerElectionResult calculateResult(W ballotbox) {
        Map<U, Long> tally = ballotbox.getVotes().stream().collect(Collectors.toMap((v) -> v.getPrioritizedList().peek(), (v) -> v.getWeight(), (v1, v2) -> v1+v2));
        List<Log> log = new ArrayList<>();
        Map<U, Long> orderedResult = sortedChoicesByVotes(tally);
        log.add(new LogVoteCount(orderedResult));
        List<U> winners = new ArrayList<>();
        Long winningNumber = null;
        for (Map.Entry<U, Long> entrySet : orderedResult.entrySet()) {
            U key = entrySet.getKey();
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
            Choice winnerByTie = tieBreaker.breakTie(winners, ballotbox, log);
            if(winnerByTie == null)
                return new TiedSingleWinnerElectionResult(winners, log);
            log.add(new LogTieBreakChoice(winnerByTie));
            return new SimpleSingleWinnerElectionResult(winnerByTie, log);
        }
        log.add(new LogChoiceElected(winners.get(0), winningNumber));
        return new SimpleSingleWinnerElectionResult(winners.get(0), log);
    }
    
}

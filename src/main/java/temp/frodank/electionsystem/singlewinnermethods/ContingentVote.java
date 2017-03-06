/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import temp.frodank.electionsystem.BallotBox;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.ElectionSystem;
import temp.frodank.electionsystem.SingleChoiceTieBreaker;
import temp.frodank.electionsystem.TieBreaker;
import temp.frodank.electionsystem.TieBreakerResultException;
import temp.frodank.electionsystem.Vote;
import temp.frodank.electionsystem.logging.Log;
import temp.frodank.electionsystem.logging.LogChoiceElected;
import temp.frodank.electionsystem.logging.LogChoiceTie;
import temp.frodank.electionsystem.logging.LogMessage;
import temp.frodank.electionsystem.logging.LogTieBreakChoice;
import temp.frodank.electionsystem.logging.LogVoteCount;

/**
 * A contingent-vote election system. Used in the Sri Lanka presidential election.
 * It's also used in the french presidential election, but in a separate vote
 * instead of an instant ranked election instant election.
 * 
 * <p>
 * 
 * The contingent vote works by tallying the number of votes each candidate has,
 * and then eliminating all but the two candidates with the most votes. A new
 * tally is made to find the winner between the two remaining candidates. This 
 * second tally takes into account all the votes for candidates that has been
 * eliminated, by looking at the ranked preference of each vote.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Contingent_vote">https://en.wikipedia.org/wiki/Contingent_vote</a>
 *
 * @author frodank
 * @param <V> The type of {@link Vote} to use. It expects the weight of the vote to be a Long. It should also be a ranked choice. A one-candidate-type of vote will result in the same result as {@link FirstPastThePost}
 * @param <U> The type of {@link Choice} used in the election
 * @param <W> The type of {@link BallotBox} to use
 */
public class ContingentVote<V extends Vote<Long, U, V>, U extends Choice, W extends BallotBox<V,W>> extends ElectionSystem<SingleWinnerElectionResult, V, W> {

    private SingleChoiceTieBreaker<Integer,U,V,W> tieBreaker;
    private TieBreaker<Integer,U,V,W> secondRoundDecider;

    /**
     * Constructor for the contingent vote. Takes a SingleChoiceTieBreaker and a
     * TieBreaker as arguments.
     * 
     * <p>
     * 
     * The secondRoundDecider will specify the number of candidates needed to 
     * reach a total of two candidates in the last round. In implementations
     * where the secondRoundDecider might return more candidates than needed a
     * majority win is not guaranteed by the election system, as the candidate
     * with the most votes in the second round is determined to be the winner.
     * 
     * @param tieBreaker A single-choice tie-breaker which determines the winner in the case of a second-round tie. If null or if {@link SingleChoiceTieBreaker#breakTie(java.util.List, temp.frodank.electionsystem.BallotBox, java.util.List) } returns null, a {@link TiedSingleWinnerElectionResult} is returned.
     * @param secondRoundDecider A tie-breaker which determines the second-round contenders. Must return enough candidates to have at least two candidates in a second round. If null, all tied candidates goes on to the next round.
     */
    public ContingentVote(SingleChoiceTieBreaker<Integer,U,V,W> tieBreaker, TieBreaker secondRoundDecider) {
        this.tieBreaker = tieBreaker;
        this.secondRoundDecider = secondRoundDecider;
    }
    
    /**
     * @return 
     * @see #ContingentVote(temp.frodank.electionsystem.SingleChoiceTieBreaker, temp.frodank.electionsystem.TieBreaker) 
     */
    public SingleChoiceTieBreaker<Integer,U,V,W> getTieBreaker() {
        return tieBreaker;
    }
    
    /**
     * @param tieBreaker
     * @see #ContingentVote(temp.frodank.electionsystem.SingleChoiceTieBreaker, temp.frodank.electionsystem.TieBreaker) 
     */
    public void setTieBreaker(SingleChoiceTieBreaker<Integer,U,V,W> tieBreaker) {
        this.tieBreaker = tieBreaker;
    }
    
    /**
     * @return 
     * @see #ContingentVote(temp.frodank.electionsystem.SingleChoiceTieBreaker, temp.frodank.electionsystem.TieBreaker) 
     */
    public TieBreaker<Integer, U, V, W> getSecondRoundDecider() {
        return secondRoundDecider;
    }
    
    /**
     * @param secondRoundDecider
     * @see #ContingentVote(temp.frodank.electionsystem.SingleChoiceTieBreaker, temp.frodank.electionsystem.TieBreaker) 
     */
    public void setSecondRoundDecider(TieBreaker<Integer, U, V, W> secondRoundDecider) {
        this.secondRoundDecider = secondRoundDecider;
    }
    
    @Override
    protected SingleWinnerElectionResult calculateResult(W ballotBox) {
        
        List<Log> log = new ArrayList<>();
        Map<U, Long> tally = ballotBox.getVotes().stream().filter((v)->!v.getPrioritizedList().isEmpty()).collect(Collectors.toMap((v) -> v.getPrioritizedList().peek(), (v) -> v.getWeight(), (v1, v2) -> v1+v2));
        long sumVotes = tally.values().stream().mapToLong((v)-> v).sum();
        Map<U, Long> orderedResult = sortedChoicesByVotes(tally);
        log.add(new LogVoteCount(orderedResult));
        Optional<Map.Entry<U, Long>> winner = orderedResult.entrySet().stream().filter((es) -> es.getValue() >= ((sumVotes / 2) + 1)).findFirst();
        if (winner.isPresent()) {
            Map.Entry<U, Long> winnerEntry = winner.get();
            log.add(new LogChoiceElected(winnerEntry.getKey(), winnerEntry.getValue()));
            return new SimpleSingleWinnerElectionResult(winnerEntry.getKey(), log);
        }
        List<U> safeCandidates = new ArrayList<>();
        List<U> maybeCandidates = new ArrayList<>();
        Long numberOfVotes = null;
        for (Map.Entry<U, Long> entrySet : orderedResult.entrySet()) {
            U key = entrySet.getKey();
            Long value = entrySet.getValue();
            if(numberOfVotes == null || Objects.equals(value, numberOfVotes)) {
                maybeCandidates.add(key);
                numberOfVotes = value;
            } else if(value < numberOfVotes) {
                int tot = safeCandidates.size()+maybeCandidates.size();
                if(tot > 2) {
                    if(secondRoundDecider != null) {
                        maybeCandidates = new ArrayList<>(secondRoundDecider.breakTie(maybeCandidates, ballotBox, 2- safeCandidates.size(), log).keySet());
                        if((safeCandidates.size()+maybeCandidates.size()) < 2)
                            throw new TieBreakerResultException("secondRoundDecider-tiebreaker returned too few choices. A second round should be between (at least) 2 choices.");
                    }
                } 
                safeCandidates.addAll(maybeCandidates);
                numberOfVotes = value;
                maybeCandidates = new ArrayList<>(Arrays.asList(key));
                if(safeCandidates.size()>=2) {
                    break;
                }
            }
        }
        log.add(new LogMessage("Starting second round among leading candidates.", "Starting second round among leading candidates: " + safeCandidates));
        ballotBox.getVotes().stream().forEach((v) -> v.getPrioritizedList().retainAll(safeCandidates));
        tally = ballotBox.getVotes().stream().filter((v)->!v.getPrioritizedList().isEmpty()).collect(Collectors.toMap((v) -> v.getPrioritizedList().peek(), (v) -> v.getWeight(), (v1, v2) -> v1+v2));
        orderedResult = sortedChoicesByVotes(tally);
        log.add(new LogVoteCount(tally));
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
            Choice winnerByTie = tieBreaker.breakTie(winners, ballotBox, log);
            if(winnerByTie == null)
                return new TiedSingleWinnerElectionResult(winners, log);
            log.add(new LogTieBreakChoice(winnerByTie));
            return new SimpleSingleWinnerElectionResult(winnerByTie, log);
        }
        log.add(new LogChoiceElected(winners.get(0), winningNumber));
        return new SimpleSingleWinnerElectionResult(winners.get(0), log);
    }
    
}

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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import temp.frodank.electionsystem.BallotBox;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.ElectionSystem;
import temp.frodank.electionsystem.SingleChoiceTieBreaker;
import temp.frodank.electionsystem.TieBreaker;
import temp.frodank.electionsystem.Vote;
import temp.frodank.electionsystem.logging.Log;
import temp.frodank.electionsystem.logging.LogChoiceElected;
import temp.frodank.electionsystem.logging.LogChoiceEliminated;
import temp.frodank.electionsystem.logging.LogChoiceTie;
import temp.frodank.electionsystem.logging.LogMessage;
import temp.frodank.electionsystem.logging.LogVoteCount;

/**
 *
 * @author frodank
 * @param <V>
 * @param <U>
 * @param <W>
 */
public class InstantRunOff<V extends Vote<U, V>, U extends Choice<U>, W extends BallotBox<V,W>> extends ElectionSystem<SingleWinnerElectionResult, V,W> {
    
    /**
     * This is the default tie-breaker for choosing the loosing candidate in a run-off competition.
     * 
     * Example of situation: 
     * a gets 4 votes 
     * b gets 3 votes 
     * c gets 3 votes
     * 
     * This tie-breaker is tasked with finding the loser of b and c.
     * 
     * There is however a possibility that b and c still gets the same number of 
     * votes (ex. 5-5) In that case the provided superLoserTieBreaker is used to
     * decide a looser between these two.
     * 
     * The default implementation of the superLoserTieBreaker is to randomly choose
     * a loser.
     */
    public class DefaultSingleChoiceLoserTieBreaker implements SingleChoiceTieBreaker<Number, U, V, W> {
        
        private SingleChoiceTieBreaker<Number,U,V,W> superLoserTieBreaker;

        /**
         * Constructor for DefaultSingleChoiceLoserTieBreaker. Demands a SingleChoiceTieBreaker
         * for cases where there's a tie between multiple choices that's gotten 
         * least amount of votes.
         * 
         * <p>
         * 
         * If superLoserTieBreaker is null the constructor creates randomizing 
         * SingleChoiceTieBreaker for cases where there's a tie between multiple 
         * choices that's gotten least amount of votes.
         * 
         * @param superLoserTieBreaker This is used in the rare occasion where there's a tie between who should be eliminated
         */
        public DefaultSingleChoiceLoserTieBreaker(SingleChoiceTieBreaker superLoserTieBreaker) {
            if(superLoserTieBreaker == null)
                this.superLoserTieBreaker = (SingleChoiceTieBreaker<Number, U, V, W>) (List<U> choices, W ballotBox, List<Log> log) -> {
                log.add(new LogMessage("Randomly chooses loser."));
                return choices.get(ThreadLocalRandom.current().nextInt(choices.size()));
                };
            else
                this.superLoserTieBreaker = superLoserTieBreaker;
        }

        @Override
        public U breakTie(List<U> choices, W ballotBox, List<Log> log) {
            Collection<V> votes = ballotBox.getVotes();
            votes.stream().forEach((V t) -> {
                t.getPrioritizedList().retainAll(choices);
            });
            Map<U, Long> tally = votes.stream().filter((v)->!v.getPrioritizedList().isEmpty()).collect(Collectors.toMap((v) -> v.getPrioritizedList().peek(), (v) -> v.getWeight(), (v1, v2) -> v1+v2));
            Map<U, Long> orderedResult = sortedChoicesByVotes(tally);
            log.add(new LogVoteCount(orderedResult));
            
            if(orderedResult.entrySet().stream().mapToLong((e) -> e.getValue()).distinct().count() == 1) {
                List<U> losers = new ArrayList<>(orderedResult.keySet());
                log.add(new LogMessage("Eliminating loser", "Difficult to choose which one is the loser. Must use superLoserTieBreaker."));
                return superLoserTieBreaker.breakTie(losers, ballotBox, log);
            }
            List<U> losers = new ArrayList<>();
            Long losingNumber = null;
            for (Map.Entry<U, Long> entrySet : orderedResult.entrySet()) {
                U key = entrySet.getKey();
                Long value = entrySet.getValue();
                if(losingNumber == null || losingNumber > value) {
                    losers = new ArrayList<>();
                    losingNumber = value;
                }
                if(Objects.equals(losingNumber, value)) {
                    losers.add(key);
                }
            }
            
            log.add(new LogMessage("Eliminating loser"));
            if(losers.size()>1) {
                log.add(new LogChoiceTie(losers, null));
                return breakTie(losers, ballotBox.getCopy(), log);
            } else
                return losers.get(0);
        };
    }
    
    private TieBreaker<Integer,U,V,W> loserTieBreaker;

    public InstantRunOff(TieBreaker<Integer, U, V, W> loserTieBreaker) {
        this.loserTieBreaker = loserTieBreaker;
    }

    public InstantRunOff() {
    }    

    public void setLoserTieBreaker(TieBreaker<Integer, U, V, W> loserTieBreaker) {
        this.loserTieBreaker = loserTieBreaker;
    }
    
    public TieBreaker<Integer,U,V,W> getLoserTieBreaker() {
        return loserTieBreaker;
    }
    
    @Override
    public SingleWinnerElectionResult calculateResult(W ballotBox) {
        List<Log> log = new ArrayList<>();
        while(true) {
            Map<U, Long> tally = ballotBox.getVotes().stream().filter((v)->!v.getPrioritizedList().isEmpty()).collect(Collectors.toMap((v) -> v.getPrioritizedList().peek(), (v) -> v.getWeight(), (v1, v2) -> v1+v2));
            long sumVotes = tally.values().stream().mapToLong((v)-> v).sum();
            Map<U, Long> orderedResult = sortedChoicesByVotes(tally);
            log.add(new LogVoteCount(orderedResult));
            Optional<Map.Entry<U, Long>> winner = orderedResult.entrySet().stream().filter((es) -> es.getValue() >= ((sumVotes / 2) + 1)).findFirst();
            if(winner.isPresent()) {
                Map.Entry<U, Long> winnerEntry = winner.get();
                log.add(new LogChoiceElected(winnerEntry.getKey(), winnerEntry.getValue()));
                return new SimpleSingleWinnerElectionResult(winnerEntry.getKey(), log);
            }
            List<U> losers = new ArrayList<>();
            Long losingNumber = null;
            for (Map.Entry<U, Long> entrySet : orderedResult.entrySet()) {
                U key = entrySet.getKey();
                Long value = entrySet.getValue();
                if(losingNumber == null || losingNumber > value) {
                    losers = new ArrayList<>();
                    losingNumber = value;
                }
                if(Objects.equals(losingNumber, value)) {
                    losers.add(key);
                }
            }
            log.add(new LogMessage("Eliminating loser", "No winner found yet. Starting elimination of loser."));
            List<U> losersForElimination;
            if(losers.size()>1) {
                log.add(new LogChoiceTie(losers, null));
                if(loserTieBreaker == null)
                    losersForElimination = new ArrayList<>(new DefaultSingleChoiceLoserTieBreaker(null).breakTie(losers, ballotBox.getCopy(), 1, log).keySet());
                else
                    losersForElimination = new ArrayList<>(loserTieBreaker.breakTie(losers, ballotBox.getCopy(), 1, log).keySet());
            } else {
                losersForElimination = Arrays.asList(losers.get(0));
            }
            // if loserTieBreaker doesn't want to eliminate any choices, return a tie.
            if(losersForElimination.isEmpty()) {
                List<U> winners = new ArrayList<>(orderedResult.keySet());
                log.add(new LogChoiceTie(winners, 1));
                return new TiedSingleWinnerElectionResult(winners, log);
            }
            for (U loser : losers) {
                log.add(new LogChoiceEliminated(loser, losingNumber));
            }
            ballotBox.getVotes().stream().forEach((v) -> v.getPrioritizedList().removeAll(losersForElimination));
        }
    }
    
}

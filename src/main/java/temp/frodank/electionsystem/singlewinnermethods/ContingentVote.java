/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.ArrayList;
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
import temp.frodank.electionsystem.Vote;
import temp.frodank.electionsystem.logging.Log;
import temp.frodank.electionsystem.logging.LogChoiceElected;
import temp.frodank.electionsystem.logging.LogChoiceTie;
import temp.frodank.electionsystem.logging.LogMessage;
import temp.frodank.electionsystem.logging.LogTieBreakChoice;
import temp.frodank.electionsystem.logging.LogVoteCount;

/**
 *
 * @author frodank
 * @param <V>
 * @param <U>
 * @param <W>
 */
public class ContingentVote<V extends Vote<U, V>, U extends Choice<U>, W extends BallotBox<V,W>> extends ElectionSystem<SingleWinnerElectionResult, V, W> {

    private SingleChoiceTieBreaker tieBreaker;
    private TieBreaker<Integer,U,V,W> secondRoundDecider;

    public ContingentVote(SingleChoiceTieBreaker tieBreaker, TieBreaker secondRoundDecider) {
        this.tieBreaker = tieBreaker;
        this.secondRoundDecider = secondRoundDecider;
    }
    
    public SingleChoiceTieBreaker getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(SingleChoiceTieBreaker tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public TieBreaker<Integer, U, V, W> getSecondRoundDecider() {
        return secondRoundDecider;
    }

    public void setSecondRoundDecider(TieBreaker<Integer, U, V, W> secondRoundDecider) {
        this.secondRoundDecider = secondRoundDecider;
    }
    
    @Override
    public SingleWinnerElectionResult calculateResult(W ballotBox) {
        
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
            } else if(value < numberOfVotes) {
                int tot = safeCandidates.size()+maybeCandidates.size();
                if(tot > 2) {
                    if(secondRoundDecider != null)
                        maybeCandidates = new ArrayList<>(secondRoundDecider.breakTie(maybeCandidates, ballotBox, 2- safeCandidates.size(), log).keySet());
                } 
                safeCandidates.addAll(maybeCandidates);
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

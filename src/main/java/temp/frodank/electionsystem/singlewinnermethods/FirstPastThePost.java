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
        Map<Choice, Long> tally = ballotbox.getVotes().stream().collect(Collectors.toMap((v) -> v.getPrioritizedList().peek(), (v) -> v.getWeight(), (v1, v2) -> v1+v2));
        List<Log> log = new ArrayList<>();
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

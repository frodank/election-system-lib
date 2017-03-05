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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import temp.frodank.electionsystem.BallotBox;
import temp.frodank.electionsystem.Calculator;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.SimpleBallotBox;
import temp.frodank.electionsystem.SimpleRankedVote;
import temp.frodank.electionsystem.TieBreakerResultException;

/**
 *
 * @author frodank
 */
public class ContingentVoteTest {
    @Test
    public void test() {
        ContingentVote cv = new ContingentVote<>(null, null);
        Choice donald = new TestChoice("Donald");
        Choice hillary = new TestChoice("Hillary");
        Choice jill = new TestChoice("Jill");
        Choice gary = new TestChoice("Gary");
        
        SimpleBallotBox bb = new SimpleBallotBox(new ArrayList<>());
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(donald, hillary, gary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(donald, gary, jill, hillary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(donald, jill, gary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(hillary, jill, gary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(hillary, jill, donald))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(hillary, jill))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(hillary, donald))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(jill, gary, hillary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(gary, jill))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(gary, donald))));
        SingleWinnerElectionResult swer = Calculator.calculateElection(bb, cv);
        assert swer.getWinner() == hillary : "Winner should be hillary";
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(gary))));
        swer = Calculator.calculateElection(bb, cv);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        TiedSingleWinnerElectionResult tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be hillary and gary";
        assert tswer.getSingleWinnerTiedCandidates().contains(gary) : "Tied result should be hillary and gary";
        assert tswer.getSingleWinnerTiedCandidates().contains(hillary) : "Tied result should be hillary and gary";
        assert !tswer.getSingleWinnerTiedCandidates().contains(donald) : "Tied result should be hillary and gary";
        assert !tswer.getSingleWinnerTiedCandidates().contains(jill) : "Tied result should be hillary and gary";
        cv.setSecondRoundDecider((List choices, BallotBox ballotBox, Number spots, List log) -> {
            Integer i = (Integer) spots;
            Map m = new HashMap();
            if(i > 0 && choices.contains(jill)) {
                m.put(jill, 1);
                i--;
            }
            if(i > 0 && choices.contains(gary)) {
                m.put(gary, 1);
                i--;
            }
            if(i > 0 && choices.contains(hillary)) {
                m.put(hillary, 1);
                i--;
            }
            if(i > 0 && choices.contains(donald)) {
                m.put(donald, 1);
                i--;
            }
            return m;
        });
        swer = Calculator.calculateElection(bb, cv);
        assert swer.getWinner() == gary : "Winner should be gary";
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(hillary, jill, donald))));
        swer = Calculator.calculateElection(bb, cv);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be hillary and gary";
        assert tswer.getSingleWinnerTiedCandidates().contains(gary) : "Tied result should be hillary and gary";
        assert tswer.getSingleWinnerTiedCandidates().contains(hillary) : "Tied result should be hillary and gary";
        assert !tswer.getSingleWinnerTiedCandidates().contains(donald) : "Tied result should be hillary and gary";
        assert !tswer.getSingleWinnerTiedCandidates().contains(jill) : "Tied result should be hillary and gary";
        cv.setTieBreaker((choices, ballotBox, log) -> gary);
        swer = Calculator.calculateElection(bb, cv);
        assert swer.getWinner() == gary : "Winner should be gary";
        cv.setTieBreaker((choices, ballotBox, log) -> hillary);
        swer = Calculator.calculateElection(bb, cv);
        assert swer.getWinner() == hillary : "Winner should be hillary";
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(jill, donald))));
        swer = Calculator.calculateElection(bb, cv);
        assert swer.getWinner() == hillary : "Winner should be hillary";
        cv.setTieBreaker((choices, ballotBox, log) -> gary);
        swer = Calculator.calculateElection(bb, cv);
        assert swer.getWinner() == gary : "Winner should be gary";
        cv.setTieBreaker((choices, ballotBox, log) -> null);
        swer = Calculator.calculateElection(bb, cv);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be hillary and gary";
        assert tswer.getSingleWinnerTiedCandidates().contains(gary) : "Tied result should be hillary and gary";
        assert tswer.getSingleWinnerTiedCandidates().contains(hillary) : "Tied result should be hillary and gary";
        assert !tswer.getSingleWinnerTiedCandidates().contains(donald) : "Tied result should be hillary and gary";
        assert !tswer.getSingleWinnerTiedCandidates().contains(jill) : "Tied result should be hillary and gary";
        cv.setSecondRoundDecider((c,b,s,l) -> new HashMap());
        try{
            Calculator.calculateElection(bb, cv);
            Assert.fail("Exception should have been thrown");
        } catch(TieBreakerResultException tbre) {
            assert tbre.getMessage().contains("secondRoundDecider") : "The message in the exception should contain secondRoundDecider";
        }
    }
    
}

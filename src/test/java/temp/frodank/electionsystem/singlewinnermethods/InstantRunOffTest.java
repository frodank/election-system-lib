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
import org.junit.Test;
import temp.frodank.electionsystem.BallotBox;
import temp.frodank.electionsystem.Calculator;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.SimpleBallotBox;
import temp.frodank.electionsystem.SimpleRankedVote;
import temp.frodank.electionsystem.SingleChoiceTieBreaker;

/**
 *
 * @author frodank
 */
public class InstantRunOffTest {
    @Test
    public void test() {
        InstantRunOff iro = new InstantRunOff();
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
        SingleWinnerElectionResult swer = Calculator.calculateElection(bb, iro);
        assert swer.getWinner() == hillary : "Winner should be hillary";
        iro.setLoserTieBreaker(iro.new DefaultSingleChoiceLoserTieBreaker((SingleChoiceTieBreaker) (List choices, BallotBox ballotBox, List log) -> {
            if(choices.contains(donald))
                return donald;
            else if(choices.contains(hillary))
                return hillary;
            else
                return gary;
        }));
        swer = Calculator.calculateElection(bb, iro);
        assert swer.getWinner() == hillary : "Winner should be hillary";
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(gary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(donald, gary))));
        bb.addVote(new SimpleRankedVote(new LinkedList<>(Arrays.asList(hillary, gary))));
        swer = Calculator.calculateElection(bb, iro);
        assert swer.getWinner() == gary : "Winner should be gary";
        iro.setLoserTieBreaker((c,b,s,l) -> new HashMap());
        swer = Calculator.calculateElection(bb, iro);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        TiedSingleWinnerElectionResult tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 3 : "Tied result should be between hillary, gary and donald";
        assert tswer.getSingleWinnerTiedCandidates().contains(hillary) : "Tied result should be between hillary, gary and donald";
        assert tswer.getSingleWinnerTiedCandidates().contains(gary) : "Tied result should be between hillary, gary and donald";
        assert tswer.getSingleWinnerTiedCandidates().contains(donald) : "Tied result should be between hillary, gary and donald";
        assert !tswer.getSingleWinnerTiedCandidates().contains(jill) : "Tied result should be between hillary, gary and donald";
    }
}
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
import org.junit.Test;
import temp.frodank.electionsystem.BallotBox;
import temp.frodank.electionsystem.Calculator;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.SimpleBallotBox;
import temp.frodank.electionsystem.SimpleVote;
import temp.frodank.electionsystem.SingleChoiceTieBreaker;


/**
 *
 * @author frodank
 */
public class FirstPastThePostTest {
    @Test
    public void test() {
        FirstPastThePost fptp = new FirstPastThePost();
        Choice a = new TestChoice("A");
        Choice b = new TestChoice("B");
        Choice c = new TestChoice("C");
        SingleChoiceTieBreaker tb = new SingleChoiceTieBreaker() {

            @Override
            public Choice breakTie(List choices, BallotBox ballotBox) {
                return choices.contains(b) ? b : null;
            }

            @Override
            public Map breakTie(List choices, BallotBox ballotBox, Number spots) {
                return SingleChoiceTieBreaker.super.breakTie(choices, ballotBox, (Integer) spots);
            }
        };
        FirstPastThePost fptpwtb = new FirstPastThePost(tb);
        
        SimpleBallotBox bb = new SimpleBallotBox(new ArrayList<>());
        bb.addVote(new SimpleVote(a));
        // a:1
        SingleWinnerElectionResult swer = Calculator.calculateElection(bb, fptp);
        assert swer.getWinner().equals(a) : "Winner should be a";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(a) : "Winner should be a";
        bb.addVote(new SimpleVote(b));
        // a:1 b:1
        swer = Calculator.calculateElection(bb, fptp);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        TiedSingleWinnerElectionResult tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be a and b";
        assert tswer.getSingleWinnerTiedCandidates().contains(a) : "Tied result should be a and b";
        assert tswer.getSingleWinnerTiedCandidates().contains(b) : "Tied result should be a and b";
        assert !tswer.getSingleWinnerTiedCandidates().contains(c) : "Tied result should be a and b";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(b) : "Winner should be b";
        bb.addVote(new SimpleVote(b));
        // a:1 b:2
        swer = Calculator.calculateElection(bb, fptp);
        assert swer.getWinner().equals(b) : "Winner should be b";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(b) : "Winner should be b";
        bb.addVote(new SimpleVote(a));
        // a:2 b:2
        swer = Calculator.calculateElection(bb, fptp);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be a and b";
        assert tswer.getSingleWinnerTiedCandidates().contains(a) : "Tied result should be a and b";
        assert tswer.getSingleWinnerTiedCandidates().contains(b) : "Tied result should be a and b";
        assert !tswer.getSingleWinnerTiedCandidates().contains(c) : "Tied result should be a and b";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(b) : "Winner should be b";
        bb.addVote(new SimpleVote(a));
        // a:3 b:2
        swer = Calculator.calculateElection(bb, fptp);
        assert swer.getWinner().equals(a) : "Winner should be a";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(a) : "Winner should be a";
        bb.addVote(new SimpleVote(c));
        // a:3 b:2 c:1
        swer = Calculator.calculateElection(bb, fptp);
        assert swer.getWinner().equals(a) : "Winner should be a";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(a) : "Winner should be a";
        bb.addVote(new SimpleVote(c));
        // a:3 b:2 c:2
        swer = Calculator.calculateElection(bb, fptp);
        assert swer.getWinner().equals(a) : "Winner should be a";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(a) : "Winner should be a";
        bb.addVote(new SimpleVote(c));
        // a:3 b:2 c:3
        swer = Calculator.calculateElection(bb, fptp);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be a and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(a) : "Tied result should be a and c";
        assert !tswer.getSingleWinnerTiedCandidates().contains(b) : "Tied result should be a and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(c) : "Tied result should be a and c";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 2 : "Tied result should be a and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(a) : "Tied result should be a and c";
        assert !tswer.getSingleWinnerTiedCandidates().contains(b) : "Tied result should be a and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(c) : "Tied result should be a and c";
        bb.addVote(new SimpleVote(b));
        // a:3 b:3 c:3
        swer = Calculator.calculateElection(bb, fptp);
        assert swer instanceof TiedSingleWinnerElectionResult : "Result should be a tie";
        tswer = (TiedSingleWinnerElectionResult) swer;
        assert tswer.getSingleWinnerTiedCandidates().size() == 3 : "Tied result should be a, b and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(a) : "Tied result should be a, b and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(b) : "Tied result should be a, b and c";
        assert tswer.getSingleWinnerTiedCandidates().contains(c) : "Tied result should be a, b and c";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(b) : "Winner should be b";
        bb.addVote(new SimpleVote(c));
        // a:3 b:3 c:4
        swer = Calculator.calculateElection(bb, fptp);
        assert swer.getWinner().equals(c) : "Winner should be c";
        swer = Calculator.calculateElection(bb, fptpwtb);
        assert swer.getWinner().equals(c) : "Winner should be c";
    }
    
}

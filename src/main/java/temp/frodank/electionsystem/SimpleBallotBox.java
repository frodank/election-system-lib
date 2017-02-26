/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author frodank
 * @param <T>
 */
public class SimpleBallotBox<T extends Choice> implements BallotBox<SimpleVote<T>, SimpleBallotBox>{
    private Collection<SimpleVote<T>> votes;

    public SimpleBallotBox(Collection<SimpleVote<T>> votes) {
        this.votes = votes;
    }
    
    public void addVote(SimpleVote<T> vote) {
        votes.add(vote);
    }

    @Override
    public SimpleBallotBox getCopy() {
        Collection<SimpleVote<T>> votesCopy = new ArrayList<>();
        for (SimpleVote<T> vote : votes) {
            votesCopy.add(vote.getCopy());
        }
        return new SimpleBallotBox(votesCopy);
    }

    @Override
    public Collection<SimpleVote<T>> getVotes() {
        return votes;
    }
    
}

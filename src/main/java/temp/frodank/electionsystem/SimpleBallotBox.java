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
 * @param <V>
 */
public class SimpleBallotBox<T extends Choice, V extends Vote<T,V>> implements BallotBox<V, SimpleBallotBox>{
    private Collection<V> votes;

    public SimpleBallotBox(Collection<V> votes) {
        this.votes = votes;
    }
    
    public void addVote(V vote) {
        votes.add(vote);
    }

    @Override
    public SimpleBallotBox getCopy() {
        Collection<V> votesCopy = new ArrayList<>();
        for (V vote : votes) {
            votesCopy.add(vote.getCopy());
        }
        return new SimpleBallotBox(votesCopy);
    }

    @Override
    public Collection<V> getVotes() {
        return votes;
    }
    
}

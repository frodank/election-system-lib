/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple implementation of a ballotbox.
 *
 * @author frodank
 * @param <T> The type of {@link Choice} used in the election
 * @param <V> The type of {@link Vote} to use. 
 */
public class SimpleBallotBox<T extends Choice, V extends Vote<? extends Number,T,V>> implements BallotBox<V, SimpleBallotBox>{
    private Collection<V> votes;

    /**
     * Constructor
     * 
     * @param votes A collection of votes contained within the box
     */
    public SimpleBallotBox(Collection<V> votes) {
        this.votes = votes;
    }
    
    /**
     * Adds a vote to the ballot box
     * 
     * @param vote Vote to add to the ballot box
     */
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

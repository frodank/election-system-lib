/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.Collection;

/**
 * An interface which represents a ballot-box. A ballot-box contains a collection
 * of votes.
 *
 * @author frodank
 * @param <T> The type of {@link Vote} to use. 
 * @param <V> Its own type. Used for returning a copy of itself.
 */
public interface BallotBox<T extends Vote, V extends BallotBox> {
    /**
     * A collection of votes contained in the ballot-box.
     * 
     * @return A collection of votes contained in the ballot-box.
     */
    Collection<T> getVotes();
    
    /**
     * Returns a copy of itself. The copy contains collection of the same number
     * of votes, and a copy of each of the votes in the collection. 
     * 
     * <p>
     * 
     * The implementation of the BallotBox must make sure that the references to
     * the copy's vote-objects in the {@link #getVotes()}-method are not the same as
     * this objects vote-objects. This is because several {@link ElectionSystem}-implementations
     * will modify both the collection of votes, and the votes within.
     * 
     * @return A copy of itself
     */
    V getCopy();
}

/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.LinkedList;

/**
 * Classes implementing Vote must be able to return a LinkedList of Choice's,
 * a Long weight and a copy of itself.
 *
 * @author frodank
 * @param <T> The type of the weight of the vote
 * @param <U> The type of {@link Choice} that the vote can have
 * @param <V> It's own type, for copying
 */
public interface Vote<T extends Number, U extends Choice, V extends Vote> {
    /**
     * Returns the list of preferred candidates in ranked order.
     * 
     * @return A ranked list of preferred candidates
     */
    LinkedList<U> getPrioritizedList();
    
    /**
     * How much this vote is worth.
     * 
     * @return the weight of the vote.
     */
    T getWeight();
    
    /**
     * Returns a copy of itself with a copy of the {@link #getPrioritizedList()}.
     * The objects within are not necessarily cloned.
     * 
     * @return A copy of itself
     */
    V getCopy();
}

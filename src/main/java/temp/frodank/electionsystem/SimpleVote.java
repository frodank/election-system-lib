/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * This is a simple vote with a weight of 1 and a single preferred candidate.
 * getPrioritizedList returns a one-item Choice.
 *
 * @author frodank
 * @param <T> The type of {@link Choice} that the vote can have
 */
public class SimpleVote<T extends Choice> implements Vote<Long, T, SimpleVote>{

    private final LinkedList<T> choice;

    /**
     * Constructor
     * 
     * @param choice The candidate this vote is for
     */
    public SimpleVote(T choice) {
        this.choice = new LinkedList<>(Arrays.asList(choice));
    }
    
    /**
     * Returns the candidate the vote is for
     * @return 
     */
    public T getChoice() {
        return choice.peek();
    }
    
    @Override
    public LinkedList<T> getPrioritizedList() {
        return choice;
    }

    @Override
    public Long getWeight() {
        return 1L;
    }

    @Override
    public SimpleVote getCopy() {
        return new SimpleVote(getChoice());
    }

    @Override
    public String toString() {
        return getChoice().getName();
    }
}

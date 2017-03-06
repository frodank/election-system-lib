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
 * A simple implementation of a ranked vote, with weight 1.
 *
 * @author frodank
 * @param <U> The type of {@link Choice} that the vote can have
 */
public class SimpleRankedVote<U extends Choice> implements Vote<Long, U, SimpleRankedVote>{

    private final LinkedList<U> choices;

    /**
     * Constructor
     * 
     * @param choices A LinkedList of preferred candidate in ranked order.
     */
    public SimpleRankedVote(LinkedList<U> choices) {
        this.choices = choices;
    }

    @Override
    public LinkedList<U> getPrioritizedList() {
        return choices;
    }

    @Override
    public Long getWeight() {
        return 1L;
    }

    @Override
    public SimpleRankedVote getCopy() {
        return new SimpleRankedVote((LinkedList) choices.clone());
    }
    
}

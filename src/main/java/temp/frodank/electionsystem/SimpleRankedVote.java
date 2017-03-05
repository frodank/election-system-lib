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
 *
 * @author frodank
 * @param <U>
 */
public class SimpleRankedVote<U extends Choice<U>> implements Vote<U, SimpleRankedVote>{

    private final LinkedList<U> choices;

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

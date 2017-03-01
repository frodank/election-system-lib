/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

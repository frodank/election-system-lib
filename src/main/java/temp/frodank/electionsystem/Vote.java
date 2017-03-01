/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.LinkedList;

/**
 * Classes implementing Vote must be able to return a LinkedList of Choice's,
 * a Long weight and a copy of itself.
 *
 * @author frodank
 * @param <U> The type of Choice that the vote can have
 * @param <V> It's own type, for copying
 */
public interface Vote<U extends Choice, V extends Vote> {
    LinkedList<U> getPrioritizedList();
    
    Long getWeight();
    
    V getCopy();
}

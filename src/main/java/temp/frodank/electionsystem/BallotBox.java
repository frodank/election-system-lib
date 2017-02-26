/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.Collection;

/**
 *
 * @author frodank
 * @param <T>
 * @param <V>
 */
public interface BallotBox<T extends Vote, V extends BallotBox> {
    Collection<T> getVotes();
    
    V getCopy();
}

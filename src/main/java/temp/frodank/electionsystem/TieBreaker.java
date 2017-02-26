/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.List;
import java.util.Map;

/**
 *
 * @author frodank
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <W>
 */
@FunctionalInterface
public interface TieBreaker<T extends Number, U extends Choice<U>, V extends Vote<U, V>, W extends BallotBox<V,W>> {
    Map<U, T> breakTie(List<U> choices, W ballotBox, T spots);
}

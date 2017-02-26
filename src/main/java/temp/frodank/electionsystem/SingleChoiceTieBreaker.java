/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author frodank
 * @param <U>
 * @param <V>
 * @param <W>
 */
@FunctionalInterface
public interface SingleChoiceTieBreaker<U extends Choice<U>, V extends Vote<U, V>, W extends BallotBox<V,W>> extends TieBreaker<Integer,U,V,W> {

    @Override
    default Map<U, Integer> breakTie(List<U> choices, W ballotBox, Integer spots) {
        Map m = new HashMap();
        m.put(breakTie(choices, ballotBox), spots);
        return m;
    }
    U breakTie(List<U> choices, W ballotBox);
}

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
 */
public interface TiedElectionResult<T extends Number, U extends Choice<U>> extends ElectionResult<T, U>{
    /**
     * A map containing tied candidates, and the number of spots they were competing for.
     * 
     * @return Map containing tied candidates.
     */
    Map<List<U>, T> getTiedCandidates();
}

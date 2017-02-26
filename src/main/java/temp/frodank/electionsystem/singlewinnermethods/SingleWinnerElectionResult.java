/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.HashMap;
import java.util.Map;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.ElectionResult;

/**
 *
 * @author frodank
 * @param <U>
 */
public interface SingleWinnerElectionResult<U extends Choice<U>> extends ElectionResult<Integer, U> {
    /**
     * Returns the winner. null indicates that this is a {@link TiedSingleWinnerElectionResult}.
     * 
     * @return The winner, or null if there is none
     */
    U getWinner();

    @Override
    public default Map<U, Integer> getWinningCandidates() {
        Map m = new HashMap();
        m.put(getWinner(), 1);
        return m;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.TiedElectionResult;
import temp.frodank.electionsystem.logging.Log;

/**
 *
 * @author frodank
 * @param <U>
 */
public class TiedSingleWinnerElectionResult<U extends Choice<U>> implements TiedElectionResult<Integer, U>, SingleWinnerElectionResult<U>{

    private final List<U> tiedCandidates;
    private final List<Log> log;
    
    public TiedSingleWinnerElectionResult(List<U> tiedCandidates, List<Log> log) {
        if(tiedCandidates == null)
            throw new NullPointerException();
        if(tiedCandidates.isEmpty())
            throw new IllegalArgumentException("List of tied candidates cannot be 0.");
        this.tiedCandidates = tiedCandidates;
        this.log = log;
    }

    @Override
    public Map<U, Integer> getWinningCandidates() {
        return null;
    }

    @Override
    public List<Log> getDetailedLog() {
        return log;
    }

    @Override
    public U getWinner() {
        return null;
    }

    @Override
    public Map<List<U>, Integer> getTiedCandidates() {
        HashMap m = new HashMap<>();
        m.put(tiedCandidates,1);
        return m;
    }
    
    public List<U> getSingleWinnerTiedCandidates() {
        return tiedCandidates;
    }

}

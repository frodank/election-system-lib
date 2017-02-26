/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.List;
import java.util.Map;
import temp.frodank.electionsystem.Choice;
import temp.frodank.electionsystem.logging.Log;

/**
 *
 * @author frodank
 * @param <U>
 */
public class SimpleSingleWinnerElectionResult<U extends Choice<U>> implements SingleWinnerElectionResult<U> {

    private final U winner;
    private final List<Log> log;

    public SimpleSingleWinnerElectionResult(U winner, List<Log> log) {
        this.winner = winner;
        this.log = log;
    }

    @Override
    public U getWinner() {
        return winner;
    }

    @Override
    public List getDetailedLog() {
        return log;
    }
}

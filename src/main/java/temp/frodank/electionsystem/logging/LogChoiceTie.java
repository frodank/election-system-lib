/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.logging;

import java.util.List;
import temp.frodank.electionsystem.Choice;

/**
 *
 * @author frodank
 */
public class LogChoiceTie implements Log {
    private final List<Choice> elected;
    private final Number tiedSpots;

    public LogChoiceTie(List<Choice> elected, Number tiedSpots) {
        this.elected = elected;
        this.tiedSpots = tiedSpots;
    }

    @Override
    public String getMessage() {
        return elected + " became tied";
    }

    @Override
    public String getDetailedMessage() {
        return elected + " became tied for " + tiedSpots + " spot(s).";
    }
}

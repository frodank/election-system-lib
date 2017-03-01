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
    private final List<? extends Choice> choices;
    private final Number tiedSpots;

    public LogChoiceTie(List<? extends Choice> elected, Number tiedSpots) {
        this.choices = elected;
        this.tiedSpots = tiedSpots;
    }

    @Override
    public String getMessage() {
        return choices + " became tied";
    }

    @Override
    public String getDetailedMessage() {
        if(tiedSpots == null)
            return choices + " became tied for elimination.";
        else
            return choices + " became tied for " + tiedSpots + " spot(s).";
    }
}

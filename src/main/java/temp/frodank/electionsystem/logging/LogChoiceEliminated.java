/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.logging;

import temp.frodank.electionsystem.Choice;

/**
 *
 * @author frodank
 */
public class LogChoiceEliminated implements Log {
    private Choice eliminated;
    private Long numberOfVotes;

    public LogChoiceEliminated(Choice eliminated, Long numberOfVotes) {
        this.eliminated = eliminated;
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public String getMessage() {
        return eliminated.getName() + " was eliminated";
    }

    @Override
    public String getDetailedMessage() {
        return eliminated.getName() + " was eliminated by " + numberOfVotes + " votes.";
    }
}

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
public class LogChoiceElected implements Log {
    private Choice elected;
    private Long numberOfVotes;

    public LogChoiceElected(Choice elected, Long numberOfVotes) {
        this.elected = elected;
        this.numberOfVotes = numberOfVotes;
    }

    @Override
    public String getMessage() {
        return elected.getName() + " was elected";
    }

    @Override
    public String getDetailedMessage() {
        return elected.getName() + " was elected by " + numberOfVotes + " votes.";
    }
    
    
}

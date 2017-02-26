/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.logging;

import java.util.Map;
import temp.frodank.electionsystem.Choice;

/**
 *
 * @author frodank
 */
public class LogVoteCount implements Log {
    
    private final Map<Choice, Long> voteCount;

    public LogVoteCount(Map<Choice, Long> voteCount) {
        this.voteCount = voteCount;
    }
    
    private String getVoteCountAsString(String delim) {
        String result ="";
        boolean first=true;
        for (Map.Entry<Choice, Long> entrySet : voteCount.entrySet()) {
            Choice key = entrySet.getKey();
            Long value = entrySet.getValue();
            result+= (first ? "" : delim) + key.getName() + ": " + value;
            first = false;
        }
        return result;
    }
    
    @Override
    public String getMessage() {
        return getVoteCountAsString(", ");
    }

    @Override
    public String getDetailedMessage() {
        return getVoteCountAsString("\n");
    }
    
}

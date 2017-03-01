/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem.logging;

/**
 *
 * @author frodank
 */
public class LogMessage implements Log {
    private final String message;
    private final String detailedMessage;

    public LogMessage(String message) {
        this.message = message;
        this.detailedMessage = message;
    }
    
    public LogMessage(String message, String detailedMessage) {
        this.message = message;
        this.detailedMessage = detailedMessage;
    }
    
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDetailedMessage() {
        return detailedMessage;
    }
    
}

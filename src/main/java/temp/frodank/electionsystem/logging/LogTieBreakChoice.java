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
public class LogTieBreakChoice implements Log{
    private final Choice choice;

    public LogTieBreakChoice(Choice choice) {
        this.choice = choice;
    }

    @Override
    public String getMessage() {
        return choice.getName() + " chosen by tie-break.";
    }

    @Override
    public String getDetailedMessage() {
        return getMessage();
    }
}

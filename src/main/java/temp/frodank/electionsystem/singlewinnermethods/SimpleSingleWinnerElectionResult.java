/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem.singlewinnermethods;

import java.util.List;
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

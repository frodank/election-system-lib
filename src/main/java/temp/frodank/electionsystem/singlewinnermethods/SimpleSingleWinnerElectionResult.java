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
 * A simple implementation of an election result with a single winner.
 *
 * @author frodank
 * @param <U> The type of {@link Choice} used in the election
 */
public class SimpleSingleWinnerElectionResult<U extends Choice<U>> implements SingleWinnerElectionResult<U> {

    private final U winner;
    private final List<Log> log;

    /**
     * Constructor
     * 
     * @param winner The winner of the election.
     * @param log A log which details the calculation that shows the calculation which determined the winner.
     */
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

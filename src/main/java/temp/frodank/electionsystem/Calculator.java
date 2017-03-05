/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

/**
 *
 * @author frodank
 */
public final class Calculator {
    
    public static final <T extends ElectionResult, U extends ElectionSystem<T,V,W>, V extends Vote<? extends Choice, V>, W extends BallotBox<V, W>> T calculateElection(W ballotBox, U electionSystem) {
            return electionSystem.calculateResult(ballotBox.getCopy());
    }
}

/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

/**
 * The 'main' class in this library. Contains one single static method that 
 * calculates the election-result with a given ballot-box and election system.
 *
 * @author frodank
 */
public final class Calculator {
    
    /**
     * 
     * @param <T> The type of {@link ElectionResult} to expect.
     * @param <U> The type of {@link ElectionSystem} used in the calculation.
     * @param <V> The type of {@link Vote} to contained within the ballot-box.
     * @param <W> The type of {@link BallotBox} used to calculate the result.
     * @param ballotBox A ballot-box containing all the votes that have been cast
     * @param electionSystem The election system to use to calculate a result
     * @return An Election-result with a log containing information about the calculation of the result
     */
    public static final <T extends ElectionResult, U extends ElectionSystem<T,V,W>, V extends Vote<? extends Number, ? extends Choice, V>, W extends BallotBox<V, W>> T calculateElection(W ballotBox, U electionSystem) {
            return electionSystem.calculateResult(ballotBox.getCopy());
    }
}

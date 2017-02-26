/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author frodank
 */
public final class Calculator {
    
    public static final <T extends ElectionResult, U extends ElectionSystem<T,V,W>, V extends Vote<? extends Choice, V>, W extends BallotBox<V, W>> T calculateElection(W ballotBox, U electionSystem) {
            return electionSystem.calculateResult(ballotBox.getCopy());
    }
}

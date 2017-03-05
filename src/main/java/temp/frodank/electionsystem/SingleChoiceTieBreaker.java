/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import temp.frodank.electionsystem.logging.Log;

/**
 *
 * @author frodank
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <W>
 */
@FunctionalInterface
public interface SingleChoiceTieBreaker<T extends Number, U extends Choice<U>, V extends Vote<U, V>, W extends BallotBox<V,W>> extends TieBreaker<T,U,V,W> {

    @Override
    default Map<U, T> breakTie(List<U> choices, W ballotBox, T spots, List<Log> log) {
        Map m = new HashMap();
        U u = breakTie(choices, ballotBox, log);
        if(u != null)
            m.put(u, spots);
        return m;
    }
    U breakTie(List<U> choices, W ballotBox, List<Log> log);
}

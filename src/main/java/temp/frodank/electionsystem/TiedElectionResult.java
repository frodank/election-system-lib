/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.List;
import java.util.Map;

/**
 * An interface indicating a tied election-result. 
 *
 * @author frodank
 * @param <T> The type representing the number of spots competed for
 * @param <U> The type of {@link Choice} used in the election
 */
public interface TiedElectionResult<T extends Number, U extends Choice<U>> extends ElectionResult<T, U>{
    /**
     * A map containing tied candidates, and the number of spots they were competing for.
     * 
     * @return Map containing tied candidates.
     */
    Map<List<U>, T> getTiedCandidates();
}

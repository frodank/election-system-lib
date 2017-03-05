/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.Collection;

/**
 *
 * @author frodank
 * @param <T>
 * @param <V>
 */
public interface BallotBox<T extends Vote, V extends BallotBox> {
    Collection<T> getVotes();
    
    V getCopy();
}

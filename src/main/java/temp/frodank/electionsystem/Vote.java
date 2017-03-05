/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

import java.util.LinkedList;

/**
 * Classes implementing Vote must be able to return a LinkedList of Choice's,
 * a Long weight and a copy of itself.
 *
 * @author frodank
 * @param <U> The type of Choice that the vote can have
 * @param <V> It's own type, for copying
 */
public interface Vote<U extends Choice, V extends Vote> {
    LinkedList<U> getPrioritizedList();
    
    Long getWeight();
    
    V getCopy();
}

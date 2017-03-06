/*
 * Copyright (C) 2017 Frode Ankill Kämpe <frodank@gmail.com>
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted without the express permission of the 
 * copyright holder
 */
package temp.frodank.electionsystem;

/**
 * An interface representing a choice that can be voted for.
 *
 * @author frodank
 */
public interface Choice {
    /**
     * All choices should returned at least a name. Is used in Log-implementation.
     * 
     * @return The choices name
     */
    String getName();
}


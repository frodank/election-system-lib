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
 * @param <T>
 */
public interface Choice<T extends Choice> extends Comparable<T>{
    String getName();
}


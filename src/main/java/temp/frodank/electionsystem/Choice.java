/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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


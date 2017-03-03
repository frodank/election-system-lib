/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.frodank.electionsystem;

/**
 *
 * @author frodank
 */
public class TieBreakerResultException extends RuntimeException {

    public TieBreakerResultException() {
        super();
    }

    public TieBreakerResultException(String message) {
        super(message);
    }

    public TieBreakerResultException(Throwable cause) {
        super(cause);
    }

    public TieBreakerResultException(String message, Throwable cause) {
        super(message, cause);
    }
    
}

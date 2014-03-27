/**
 * 
 */
package com.cppoon.tencent.magiccard;

/**
 * Base exception class for all Tencent magic card related exception.
 * 
 * @author Cyril
 * @since 0.1.0
 */
public class TxMagicCardException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 664882539208457935L;

	/**
	 * 
	 */
	public TxMagicCardException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TxMagicCardException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public TxMagicCardException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TxMagicCardException(Throwable cause) {
		super(cause);
	}

	
	
}

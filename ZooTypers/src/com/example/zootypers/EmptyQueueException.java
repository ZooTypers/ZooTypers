package com.example.zootypers;

import java.lang.Exception;


/**
 * An exception that is thrown when an opponent is unable to be found for a user
 * within an alloted amount of time.
 * 
 * @author winglam
 */
public class EmptyQueueException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor.
	 */
	public EmptyQueueException() { }
}




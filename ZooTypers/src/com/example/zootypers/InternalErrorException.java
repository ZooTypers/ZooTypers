package com.example.zootypers;

import java.lang.Exception;

/**
 * An exception that is thrown when internal errors such as thread interrupts
 * or concurrency issues occurs.
 * 
 * @author winglam
 */
public class InternalErrorException  extends Exception {
	private static final long serialVersionUID = 1L;

  /**
   * Empty constructor.
   */
	public InternalErrorException () { }
}



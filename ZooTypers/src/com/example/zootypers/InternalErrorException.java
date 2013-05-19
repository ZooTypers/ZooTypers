package com.example.zootypers;

import java.lang.Exception;

/** Exception that is thrown when internal errors such as thread interrupts or concurrency issues occurs. */
public class InternalErrorException  extends Exception {
	private static final long serialVersionUID = 1L;

	public InternalErrorException () {
	}
}



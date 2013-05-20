package com.example.zootypers;

import java.lang.Exception;


/**
 * An exception that is thrown when a user is not able to fetch / save information
 * from / to the database.
 *
 * @author winglam
 */
public class InternetConnectionException extends Exception {
	private static final long serialVersionUID = 1L;

  /**
   * Empty constructor.
   */
	public InternetConnectionException  () { }
}




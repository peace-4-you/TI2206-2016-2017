/*
 * File: Log.java
 * Class: Log
 *
 * Version: 0.0.1
 * Date: September 20th, 2016
 */


package nl.tudelft.bust_a_move20162017.bust_a_move_framework.log;

import java.text.MessageFormat;
import java.util.Date;

/**
 * The Log class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Log {
	
	private Date d;
	
	/**
	 * Method to output logging
	 */
	
	@SuppressWarnings("deprecation")
	public void log(String log){
		d = new Date();
		System.out.println(MessageFormat.format("[Bust-A-Move; Log] -> [{0}:{1}:{2}] -> {3}", d.getHours(), d.getMinutes(), d.getSeconds(), log));
	}
}

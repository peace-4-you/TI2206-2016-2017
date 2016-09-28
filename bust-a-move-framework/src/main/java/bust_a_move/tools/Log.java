/*
 * File: Log.java
 * Class: Log
 *
 * Version: 0.0.1
 * Date: September 20th, 2016
 */


package bust_a_move.tools;

import java.util.Date;

/**
 * The Log class represents a game entity.
 *
 * @author Maurice Willemsen
 */

public class Log {
	
	private Date d;
	private int number=0;
	
	/**
	 * Method to output logging
	 */
	
	@SuppressWarnings("deprecation")
	public void log(String log){
		d = new Date();
		this.number++;
		System.out.format("[Bust-A-Move] -> [Log; %d] -> [%d:%d:%d] -> %s\n", this.number, d.getHours(), d.getMinutes(), d.getSeconds(), log);
	}
	
	@SuppressWarnings("deprecation")
	public void log(Object obj, String log){
		d = new Date();
		this.number++;
		System.out.format("[Bust-A-Move] -> [Class: %s] -> [Log: %d] ->[%d:%d:%d] -> %s\n", obj.getClass().getSimpleName(), this.number, d.getHours(), d.getMinutes(), d.getSeconds(), log);
	}
}

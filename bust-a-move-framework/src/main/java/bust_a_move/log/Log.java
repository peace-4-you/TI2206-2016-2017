/*
 * File: Log.java
 * Class: Log
 *
 * Version: 0.0.1
 * Date: September 20th, 2016
 */

package bust_a_move.log;

import java.util.Date;

/**
 * The Log class represents a game entity.
 * @author Maurice Willemsen
 */
public class Log {
    /**
     * Used for timestamping.
     */
    private Date d;
    /**
     * Log count.
     */
    private int number = 0;
    /**
     * Method to output logging with only a log message.
     * @param log log message
     */
    @SuppressWarnings("deprecation")
    public final void log(final String log) {
        d = new Date();
        this.number++;
        System.out.format("[Bust-A-Move] -> [Log: %d] -> [%d:%d:%d] -> %s%n",
                this.number, d.getHours(), d.getMinutes(),
                d.getSeconds(), log);
    }

    /**
     * Method to output logging with a log message and the object that
     * generated that log.
     * @param obj object that generated the log
     * @param log log message
     */
    @SuppressWarnings("deprecation")
    public final void log(final Object obj, final String log) {
        d = new Date();
        this.number++;
        System.out.format("[Bust-A-Move] -> [Class: %s] -> [Log: %d] "
                + "-> [%d:%d:%d] -> %s%n",
                obj.getClass().getSimpleName(), this.number,
                d.getHours(), d.getMinutes(), d.getSeconds(),
                log);
    }
}

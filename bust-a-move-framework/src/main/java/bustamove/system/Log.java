/*
 * File: Log.java
 * Class: Log
 *
 * Version: 0.0.1
 * Date: September 20th, 2016
 */

package bustamove.system;

import java.util.Date;

/**
 * The Log class represents a game entity.
 *
 * @author Maurice Willemsen
 */
public class Log {
    /**
     * Used for timestamping.
     */
    private static Date d;
    /**
     * Log count.
     */
    private static int number = 0;

    /**
     * Method to output logging with only a log message.
     *
     * @param log log message
     */
    @SuppressWarnings("deprecation")
    public static final void log(final String log) {
        d = new Date();
        number++;
        System.out.format("[Bust-A-Move] -> [Log: %d] -> [%d:%d:%d] -> %s%n",
                number, d.getHours(), d.getMinutes(),
                d.getSeconds(), log);
    }

    /**
     * Method to output logging with a log message and the object that
     * generated that log.
     *
     * @param obj object that generated the log
     * @param log log message
     */
    @SuppressWarnings("deprecation")
    public static final void log(final Object obj, final String log) {
        d = new Date();
        number++;
        System.out.format("[Bust-A-Move] -> [Class: %s] -> [Log: %d] "
                        + "-> [%d:%d:%d] -> %s%n",
                obj.getClass().getSimpleName(), number,
                d.getHours(), d.getMinutes(), d.getSeconds(),
                log);
    }
}
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
public final class Log {
    /**
     * Used for timestamping.
     */
    private static Date d;
    /**
     * Log count.
     */
    private static int number = 0;


    /**
     * Constructor that you aren't supposed to use.
     */
    private Log() {
        throw new AssertionError("Not allowed to instantiate.");
    }

    /**
     * Method to output logging with only a Log message.
     *
     * @param log Log message
     */
    @SuppressWarnings("deprecation")
    public static void log(final String log) {
        d = new Date();
        number++;
        System.out.format("[Bust-A-Move] -> [Log: %d] -> [%d:%d:%d] -> %s%n",
                number, d.getHours(), d.getMinutes(),
                d.getSeconds(), log);
    }

    /**
     * Method to output logging with a Log message and the object that
     * generated that Log.
     *
     * @param obj object that generated the Log
     * @param log Log message
     */
    @SuppressWarnings("deprecation")
    public static void log(final Object obj, final String log) {
        d = new Date();
        number++;
        System.out.format("[Bust-A-Move] -> [Class: %s] -> [Log: %d] "
                        + "-> [%d:%d:%d] -> %s%n",
                obj.getClass().getSimpleName(), number,
                d.getHours(), d.getMinutes(), d.getSeconds(),
                log);
    }
}

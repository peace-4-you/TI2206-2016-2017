/*
 * File: Log.java
 * Class: Log
 *
 * Version: 0.0.1
 * Date: September 20th, 2016
 */
package bustamove.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The Log class represents a game entity.
 *
 * @author Maurice Willemsen
 */
public final class Log {

    /**
     * Singleton variable.
     */
    private static volatile Log uniqueLog;
    /**
     * Used for timestamping.
     */
    private static Calendar calendar = Calendar.getInstance();
    private final DateFormat format = new SimpleDateFormat("HH:mm:ss");
    /**
     * Log count.
     */
    private static int number = 0;

    /**
     * Constructor of the class.
     */
    private Log() {
    }

    /**
     * Singleton returns the unique instance.
     *
     * @return unique singleton instance
     */
    public static Log getInstance() {
        if (uniqueLog == null) {
            synchronized (Log.class) {
                if (uniqueLog == null) {
                    uniqueLog = new Log();
                }
            }
        }
        return uniqueLog;
    }

    /**
     * Method to output logging with a Log message and the object that
     * generated that Log.
     *
     * @param obj object that generated the Log
     * @param log Log message
     */
    public void log(final Object obj, final String log) {
        increment();
        System.out.format("[Bust-A-Move] -> [Class: %s] -> [Log: %d] "
                        + "-> [%s] -> %s%n",
                obj.getClass().getSimpleName(), number,
                format.format(calendar.getTime()),
                log);
    }

    /**
     * Increase log counter.
     */
    private static void increment() {
        number++;
    }
}
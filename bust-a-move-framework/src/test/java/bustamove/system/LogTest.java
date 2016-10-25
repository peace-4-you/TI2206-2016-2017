package bustamove.system;

import bustamove.App;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Unit testing for the Log class
 * Created by Winer Bao on Octorber 20th 2016.
 */
@RunWith(Parameterized.class)
public class LogTest {

    private PrintStream sout = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void resetStream() {
        System.setOut(sout);
    }

    private enum Type {
        logString, logObjectString
    }

    private Calendar calendar = Calendar.getInstance();
    private DateFormat format = new SimpleDateFormat("HH:mm:ss");
    private static String logStringprefix = "[Bust-A-Move] -> [Log: ";
    private static String logObjectStringprefix = "[Bust-A-Move] -> [Class: ";
    private static int logCount = 1;

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.logString, "asd", logStringprefix + logCount++ + "] -> [", null},
                {Type.logString, "Log message", logStringprefix + logCount++ + "] -> [", null},
                {Type.logString, "For testing purposes", logStringprefix + logCount++ + "] -> [", null},
                {Type.logObjectString, "asd", String.format("%s%s] -> [Log: %d] -> ", logObjectStringprefix, "Object", logCount++), new Object()},
                {Type.logObjectString, "asd", String.format("%s%s] -> [Log: %d] -> ", logObjectStringprefix, "String", logCount++), new String()},
                {Type.logObjectString, "Log message", String.format("%s%s] -> [Log: %d] -> ", logObjectStringprefix, "Log", logCount++), Log.getInstance()},
                {Type.logObjectString, "For testing purposes.", String.format("%s%s] -> [Log: %d] -> ", logObjectStringprefix, "App", logCount++), new App("")}
        });
    }

    private Type type;
    private String string;
    private String prefix;
    private Object object;
    private Log log = Log.getInstance();

    public LogTest(Type t, String s, String expected, Object o) {
        type = t;
        string = s;
        prefix = expected;
        object = o;
    }

    @Test
    public void getInstance() {
        Log log = Log.getInstance();
        assertEquals(log, Log.getInstance());
    }

    @Test
    public void logString() {
        try {
            Assume.assumeTrue(type == Type.logString);
            String expected = String.format("%s%s] -> %s%n", prefix, format.format(calendar.getTime()), string);
            logCount++;
            log.log(string);
            assertEquals(expected, outContent.toString());
        } catch (AssumptionViolatedException e) {
            // These exceptons are intentional
        }
    }

    @Test
    public void logObjectString() {
        try {
            Assume.assumeTrue(type == Type.logObjectString);
            String expected = String.format("%s[%s] -> %s%n", prefix, format.format(calendar.getTime()), string);
            logCount++;
            log.log(object, string);
            assertEquals(expected, outContent.toString());
        } catch (AssumptionViolatedException e) {
            // These exceptons are intentional
        }
    }
}
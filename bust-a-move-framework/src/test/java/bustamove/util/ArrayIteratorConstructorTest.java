package bustamove.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Calvin Nhieu on 10/13/2016.
 */
@RunWith(Parameterized.class)
public class ArrayIteratorConstructorTest {
    private ArrayIterator<Object> it;
    private Object[] elements;
    private int expectedPosition;
    private String message;

    @Before
    public void setUp() throws Exception {
        it = new ArrayIterator<Object>(elements);
    }

    public ArrayIteratorConstructorTest(Object[] in, int expected, String msg) {
        this.elements = in;
        this.expectedPosition = expected;
        this.message = msg;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        Object[] input1 = { new Object(), new Object(), new Object() };
        Object[] input2 = { null, new Object(), new Object() };
        Object[] input3 = { null, null, null };
        return Arrays.asList(new Object[][] {
            {
                input1,
                0,
                "Should initialize with position = 0 if first element is not null."
            },
            {
                input2,
                1,
                "Should initialize with position = 1 if first element is null."
            },
            {
                input3,
                3,
                "Should initialize with position = numElements all elements are null."
            }
        });
    }

    @Test
    public void constructorCallPositionTest() throws Exception {
        assertEquals(message, expectedPosition, it.getPosition());
    }
}

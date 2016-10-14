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
public class ArrayIteratorHasNextTest {
    private ArrayIterator<Object> it;
    private Object[] elements;
    private boolean expectedVal;
    private String message;

    @Before
    public void setUp() throws Exception {
        it = new ArrayIterator<Object>(elements);
    }

    public ArrayIteratorHasNextTest(Object[] in, boolean expected, String msg) {
        this.elements = in;
        this.expectedVal = expected;
        this.message = msg;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        Object[] input1 = { new Object(), new Object(), new Object() };
        Object[] input2 = { null, new Object(), null };
        Object[] input3 = { null, null, null };
        return Arrays.asList(new Object[][] {
            {
                input1,
                true,
                "Should return true if there all elements are non-null."
            },
            {
                input2,
                true,
                "Should return true if there is a non-null element."
            },
            {
                input3,
                false,
                "Should return false if all elements are null."
            }
        });
    }

    @Test
    public void hasNextCallTest() throws Exception {
        assertEquals(message, expectedVal, it.hasNext());
    }
}

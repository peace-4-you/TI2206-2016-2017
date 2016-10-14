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
public class ArrayIteratorNextTest{
    private ArrayIterator<Object> it;
    private Object[] elements;
    private int numElements;
    private String message;
    private String[] expectedOrder;

    @Before
    public void setUp() throws Exception {
        it = new ArrayIterator<Object>(elements);
    }

    public ArrayIteratorNextTest(Object[] in, int n, String msg) {
        this.elements = in;
        this.numElements = n;
        this.message = msg;
        this.expectedOrder = new String[]{ "1", "2", "3" };
    }

    @Parameterized.Parameters
    public static Collection<Object[]> input() {
        String s1 = "1";
        String s2 = "2";
        String s3 = "3";
        String[] input1 = { s1, s2, s3 };
        String[] input2 = { null, s1, s2 };
        String[] input3 = { s1, null, s2 };
        String[] input4 = { null, null, s1 };
        return Arrays.asList(new Object[][] {
            {
                input1,
                3,
                "Should return elements in order (no null elements)."
            },
            {
                input2,
                2,
                "Should return elements in order (first element null)."
            },
            {
                input3,
                2,
                "Should return elements in order (intermediate element null)."
            },
            {
                input4,
                1,
                "Should return elements in order (consequtive elements null)."
            }
        });
    }

    @Test
    public void hasNextCallTest() throws Exception {
        for (int i = 0; i < numElements; i++) {
            assertEquals(message, expectedOrder[i], it.next());
        }
    }
}

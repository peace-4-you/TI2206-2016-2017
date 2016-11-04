package bustamove.system;

import org.junit.Assume;
import org.junit.AssumptionViolatedException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import bustamove.system.SoundHandler;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit testing for the SoundHandler class
 * Created by Winer Bao on Octorber 20th 2016.
 */
@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SoundHandlerTest {
    private enum Type {
        SINGLETON
    }

    private Type type;
    private Object expectedResult;
    private String message;
    private SoundHandler soundHandler;
    private float epsilon = 0.00001f;

    public SoundHandlerTest(Type t, Object expected, String msg) {
        type = t;
        expectedResult = expected;
        message = msg;
    }

    @Parameters
    public static Collection<Object[]> input() {
        return Arrays.asList(new Object[][]{
                {Type.SINGLETON, SoundHandler.getInstance(), "Should return the same instance"},
        });
    }

    @Before
    public void setUp() {
        soundHandler = SoundHandler.getInstance();
    }

    @Test
    public void testSetVolume() {
        soundHandler.setVolumeBackground(0.5f);
        assertEquals(0.5f, soundHandler.getVolumeBackground(), epsilon);
        soundHandler.setVolumeBackground(0.8f);
        assertEquals(0.8f, soundHandler.getVolumeBackground(), epsilon);
        soundHandler.setVolumeEffects(0.5f);
        assertEquals(0.5f, soundHandler.getVolumeEffects(), epsilon);
        soundHandler.setVolumeEffects(0.8f);
        assertEquals(0.8f, soundHandler.getVolumeEffects(), epsilon);
    }

    @Test
    public void abubbleSingleton() {
        try {
            Assume.assumeTrue(type == Type.SINGLETON);
            assertEquals(message, expectedResult, SoundHandler.getInstance());
        } catch (AssumptionViolatedException e) {
            // These exceptions are intentional
        }
    }

    @Test
    public void btestPlaySoundNotInitializedNoCrash() {
        soundHandler.playClickSound();
        soundHandler.playFireSound();
        soundHandler.playLoseSound();
        soundHandler.playWinSound();
    }

    @Test
    public void increaseVolumes() {
        float bgVolume = soundHandler.getVolumeBackground();
        soundHandler.increaseVolumeBackground(0.1f);
        assertEquals(bgVolume + 0.1f, soundHandler.getVolumeBackground(), epsilon);
        soundHandler.increaseVolumeBackground(20.f);
        assertEquals(1.0f, soundHandler.getVolumeBackground(), epsilon);

        float efVolume = soundHandler.getVolumeEffects();
        soundHandler.increaseVolumeEffects(0.1f);
        assertEquals(bgVolume + 0.1f, soundHandler.getVolumeEffects(), epsilon);
        soundHandler.increaseVolumeEffects(20.f);
        assertEquals(1.0f, soundHandler.getVolumeEffects(), epsilon);
    }

    @Test
    public void decreaseVolumes() {
        soundHandler.setVolumeBackground(0.5f);
        float bgVolume = soundHandler.getVolumeBackground();
        soundHandler.decreaseVolumeBackground(0.1f);
        assertEquals(bgVolume - 0.1f, soundHandler.getVolumeBackground(), epsilon);
        soundHandler.decreaseVolumeBackground(20.f);
        assertEquals(0.0f, soundHandler.getVolumeBackground(), epsilon);

        soundHandler.setVolumeEffects(0.5f);
        float efVolume = soundHandler.getVolumeEffects();
        soundHandler.decreaseVolumeEffects(0.1f);
        assertEquals(efVolume - 0.1f, soundHandler.getVolumeEffects(), epsilon);
        soundHandler.decreaseVolumeEffects(20.f);
        assertEquals(0.0f, soundHandler.getVolumeEffects(), epsilon);
    }
}

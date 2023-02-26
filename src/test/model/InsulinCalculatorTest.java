package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsulinCalculatorTest {
    InsulinCalculator ic1;

    @BeforeEach
    public void setup() {
        ic1 = new InsulinCalculator(0, 0, 0, 0, 0);
    }

    @Test
    public void InsulinCalculatorConstructorTest() {
        assertEquals(0, ic1.currentSugar);
        assertEquals(0, ic1.numCarbs);
        assertEquals(0, ic1.baseBolus);
        assertEquals(0, ic1.insulinCarbRatio);
        assertEquals(0, ic1.insulinSensitivity);

    }

    @Test
    public void calculateInsulinTest() {
        assertEquals(0, ic1.calculateInsulin());

    }
}

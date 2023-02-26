package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InsulinCalculatorTest {
    InsulinCalculator ic1;
    InsulinCalculator ic2;
    InsulinCalculator ic3;
    InsulinCalculator ic4;
    InsulinCalculator ic5;
    InsulinCalculator ic6;


    @BeforeEach
    public void setup() {
        ic1 = new InsulinCalculator(5.6, 20, 3, 0.05, 0.25);
        ic2 = new InsulinCalculator(11.1, 31, 3, 0.05, 0.25);
        ic3= new InsulinCalculator(15.3, 31, 3, 0.05, 0.25);
        ic4 = new InsulinCalculator(19.3, 31, 3, 0.05, 0.25);
        ic5 = new InsulinCalculator(9.3, 62, 3, 0.05, 0.25);
        ic6 = new InsulinCalculator(11.3, 62, 3, 0.05, 0.25);

    }

    @Test
    public void InsulinCalculatorConstructorTest() {
        assertEquals(5.6, ic1.currentSugar);
        assertEquals(20, ic1.numCarbs);
        assertEquals(3, ic1.baseBolus);
        assertEquals(0.05, ic1.insulinCarbRatio);
        assertEquals(0.25, ic1.insulinSensitivity);

    }

    @Test
    public void calculateInsulinTest() {
        assertEquals(3, ic1.calculateInsulin());
        assertEquals(4, ic2.calculateInsulin());
        assertEquals(5, ic3.calculateInsulin());
        assertEquals(6, ic4.calculateInsulin());
        assertEquals(4, ic5.calculateInsulin());
        assertEquals(5, ic6.calculateInsulin());

    }
}

package com.example.secondMin;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SecondMinTest {

    private SecondMin secondMin;

    @Before
    public void initialise() {
        secondMin = new SecondMin();
        secondMin.setInput("input");
        secondMin.setOutput("output");
    }

    @Test
    public void testGetInput() {
        assertEquals("input",secondMin.getInput());
    }

    @Test
    public void testGetOutput() {
        assertEquals("output",secondMin.getOutput());
    }
}

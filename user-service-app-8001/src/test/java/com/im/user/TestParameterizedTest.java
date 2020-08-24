package com.im.user;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestParameterizedTest
{

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        return Arrays.asList(new Object[][]{
                {0, 0}, {1, 1}, {2, 1}, {3, 2}, {4, 3}, {5, 5}, {6, 8}
        });
    }

    private int fInput;

    private int fExpected;

    public TestParameterizedTest(int input, int expected)
    {
        fInput = input;
        fExpected = expected;
    }

    @Test
    public void test()
    {
        assertEquals(fExpected, Fibonacci.compute(fInput));

    }


    static class Fibonacci
    {
        public static int compute(int n)
        {
            int result = 0;

            if (n <= 1)
            {
                result = n;
            } else
            {
                result = compute(n - 1) + compute(n - 2);
            }

            return result;
        }
    }
}
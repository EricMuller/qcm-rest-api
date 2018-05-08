package com.emu.apps.qcm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MyTest {


    public  static  void  main(String[] args) {

    }

    @Test
    public void test()

    {
        {
            try {
                System.out.print("A");
                int value = Integer.parseInt("8A");
                System.out.print("B");
            } catch (NumberFormatException exception) {
                System.out.print("C");
                return;
            } finally {
                System.out.print("D");
            }
            System.out.print("E");
        }
    }

    @Test
    public void name() {
        float x = 12;
        int a = 7;
        x = 3 * a / 2.0F;
        System.out.println(a / 2.0F);
        System.out.print(x);
    }
}

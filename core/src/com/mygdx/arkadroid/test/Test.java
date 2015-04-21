package com.mygdx.arkadroid.test;

import java.util.Scanner;

public class Test {

    public static void main (String args[]) {

        System.out.println("What do you want to test?");
        System.out.println("1 - Polygons");
        System.out.println("2 - Next Level");
        System.out.println("3 - Game Over");

        Scanner in = new Scanner(System.in);


        switch(in.nextInt()) {
            case 1:
                testPolygons.execute();
                break;
            case 2:

        }

    }

}

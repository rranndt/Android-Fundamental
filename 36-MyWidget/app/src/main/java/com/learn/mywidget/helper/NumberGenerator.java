package com.learn.mywidget.helper;

import java.util.Random;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class NumberGenerator {

    public static int Generate(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}

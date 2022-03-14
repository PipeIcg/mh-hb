package com.example.homebanking.utils;

import com.example.homebanking.models.Card;

import java.util.Random;

public final class CardUtils  {
    private CardUtils() {
    }

    public static String getCardNumber(){
        Random random = new Random();
        String uno = random.nextInt(9999) + "";
        String dos = random.nextInt(8888)+ "";
        String tres = random.nextInt(7777)+ "";
        String cuatro = random.nextInt(6666)+ "";
        String todo = uno + " " + dos + " " + tres + " " + cuatro;
        return todo;
    };

    public static int getCvv(){
        Random random = new Random();
        int cvv = random.nextInt(999);
        return cvv;
    };


}

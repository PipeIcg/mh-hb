package com.example.homebanking;

import com.example.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {
    //getCardNumber
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void returnString(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,containsString(cardNumber));
    }

    //getCvv
    @Test
    public void cvvNumberIsCreated(){
        String cvv = CardUtils.getCvv() + "";
        assertThat(cvv,is(not(emptyOrNullString())));
    }

    @Test
    public void returnInt(){
        int cvv = CardUtils.getCvv();
        assertThat(cvv, isA(Integer.class));
    }
}

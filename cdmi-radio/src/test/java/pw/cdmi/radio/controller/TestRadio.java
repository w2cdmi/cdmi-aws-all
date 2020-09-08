package pw.cdmi.radio.controller;



import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestRadio {

        RadioDelegate radioDelegate = new RadioDelegate();


    @Test
    public void testhelloworld(){

        String expactReturnValue = "welcome,hello"; // You should put the expect String type value here.

        String returnValue = radioDelegate.helloworld("hello");

        assertEquals(expactReturnValue, returnValue);
    }

}
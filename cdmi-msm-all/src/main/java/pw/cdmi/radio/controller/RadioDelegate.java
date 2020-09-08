package pw.cdmi.radio.controller;

import org.springframework.stereotype.Component;


@Component
public class RadioDelegate {

    public String helloworld(String name){

        // Do Some Magic Here!
        return "welcome," + name;
    }
}

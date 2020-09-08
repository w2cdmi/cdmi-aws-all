package pw.cdmi.radio.controller;


import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.CseSpringDemoCodegen", date = "2018-01-05T00:08:34.445+08:00")

@RestSchema(schemaId = "radio")
@RequestMapping(path = "/radio", produces = MediaType.APPLICATION_JSON)
public class RadioImpl {

    @Autowired
    private RadioDelegate userRadioDelegate;


    @RequestMapping(value = "/helloworld",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    public String helloworld( @RequestParam(value = "name", required = true) String name){

        return userRadioDelegate.helloworld(name);
    }

}

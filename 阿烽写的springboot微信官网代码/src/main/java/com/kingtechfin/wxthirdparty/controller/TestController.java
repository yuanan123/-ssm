package com.kingtechfin.wxthirdparty.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class TestController {

//@RequestBody
//@RequestParam(value="id") String id
    //@RequestMapping(value="/hello",method= RequestMethod.POST)
    //@PostMapping(path = "/hello")
    /*@RequestMapping("/hello")*/
    @PostMapping(path = "/hello")
    private  String index( Person person){
        System.out.println();
        String kk=person.getId();
       // String reqId=map.get("id").toString();
        //String usernameI=username;


        return "Hello world";
    }
}

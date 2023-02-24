package com.nuce.account.resource;

import com.nuce.account.annotation.LogExecutionTime;
import com.nuce.account.resource.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloResource {
    @LogExecutionTime
    @RequestMapping(path = "/api/hello", method = RequestMethod.GET)
    public ResponseEntity<String> getHello(@RequestParam int a){
        int result = 9/a;
        throw new ArithmeticException();
    }
}

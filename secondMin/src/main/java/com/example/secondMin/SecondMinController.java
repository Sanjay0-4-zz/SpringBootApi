package com.example.secondMin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SecondMinController {

    @Autowired
    private SecondMinService secondMinService;

    @PostMapping("/secondMin")
    ResponseEntity getSecondMin(@RequestBody String array) {
        SecondMin secondMin = secondMinService.findSecondMin(array);
        return new ResponseEntity(secondMin,HttpStatus.OK);
    }

    @GetMapping("/history")
    ResponseEntity getHistory() {
        List<SecondMin> history = secondMinService.getHistory();
        return new ResponseEntity(history,HttpStatus.OK);
    }
}

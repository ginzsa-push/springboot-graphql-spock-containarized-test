package me.work.test.controller;

import me.work.test.MainApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = MainApplication.API_ROOT)
@CrossOrigin("*")
public class ApplicationController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pinging Ok");
    }
}
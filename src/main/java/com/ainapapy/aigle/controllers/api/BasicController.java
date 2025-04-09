package com.ainapapy.aigle.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/basic")
public class BasicController {

    @GetMapping("/page")
    public ResponseEntity<?> adminDashboard() {
        return ResponseEntity.ok("Basic page dashboard!");
    }
}
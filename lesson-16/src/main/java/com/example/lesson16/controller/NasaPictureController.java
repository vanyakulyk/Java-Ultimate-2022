package com.example.lesson16.controller;

import com.example.lesson16.service.NasaPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/pictures")
public class NasaPictureController {

    @Autowired
    NasaPictureService nasaPictureService;

    @GetMapping("/{sol}/largest")
    public ResponseEntity getLargestImage(@PathVariable("sol") int sol) {
        String largestPictureURl = nasaPictureService.getLargestPictureURl(sol);

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create(largestPictureURl))
                .build();
    }
}

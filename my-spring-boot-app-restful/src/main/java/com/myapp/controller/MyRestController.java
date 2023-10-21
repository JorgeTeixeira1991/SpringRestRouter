package com.myapp.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class MyRestController {

  @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
  public String home() {
    Path htmlPath = Paths.get("src/main/resources/static/home.html");
    try {
      return Files.readString(htmlPath);
    } catch (IOException e) {
      return e.getMessage();
    }
  }

  @GetMapping(value = "/cv", produces = MediaType.TEXT_HTML_VALUE)
  public String cv() {
    Path htmlPath = Paths.get("src/main/resources/static/Markdown CV.md");
    try {
      return Files.readString(htmlPath);
    } catch (IOException e) {
      return e.getMessage();
    }
  }

}

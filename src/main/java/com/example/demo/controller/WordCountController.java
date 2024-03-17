package com.example.demo.controller;

import com.example.demo.service.WordCountService;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class WordCountController {

  private final WordCountService wordCountService;

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");

  @RequestMapping(
      method = RequestMethod.POST,
      value = "/api/count/words-by-person",
      consumes = {"multipart/form-data"},
      produces = {"application/json"}
  )
  public ResponseEntity<String> getAllPosts(
      @Parameter(name = "file") @RequestPart(value = "file") @NonNull MultipartFile file) {
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.CONTENT_DISPOSITION,
            String.format("attachment; filename=\"counted-words_%s.txt\"",
                LocalDateTime.now().format(DATE_TIME_FORMATTER)))
        .body(wordCountService.countWordsByPerson(file));
  }
}

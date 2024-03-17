package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class WordCountService {

  private final List<Pair<String, String>> techSymbolsPairs = List.of(
      Pair.of("(", ")"), Pair.of("<", ">"), Pair.of("[", "]")
  );

  public String countWordsByPerson(@NonNull MultipartFile file) {
    List<String> fileData;
    try (InputStream inputStream = file.getInputStream()) {
      fileData = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
          .lines().collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return mapToSting(mapAndCount(fileData));
  }

  private String mapToSting(@NonNull Map<String, Integer> map) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, Integer> entry
        : map.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    return sb.toString();
  }

  private Map<String, Integer> mapAndCount(@NonNull List<String> data) {
    Map<String, Integer> result = new HashMap<>();
    for (String line : data) {
      if (line.isBlank()) {
        continue;
      }
      int colonNumber = line.indexOf(":");
      if (colonNumber < 0) {
        throw new RuntimeException(String.format("Colon is missing in the line [%s]", line));
      }
      String person = line.substring(0, colonNumber).trim().toLowerCase();
      String words = line.substring(colonNumber + 1);
      words = removeTechnicalDataAndPrepareWords(words);
      int wordCount = words.isBlank() ? 0 : words.split(" ").length;
      result.merge(person, wordCount, Integer::sum);
    }
    return result;
  }

  String removeTechnicalDataAndPrepareWords(@NonNull String line) {
    for (Pair<String, String> symbols : techSymbolsPairs) {
      line = line.replaceAll("\\" + symbols.getLeft() + ".*\\" + symbols.getRight(), "");
    }
    line = line.replaceAll("[^A-Za-zА-Яа-я0-9 ]", "");
    while (line.contains("  ")) {
      line = line.replaceAll("  ", " ");
    }
    return line.trim();
  }

}

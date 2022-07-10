package com.example.lesson16.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class NasaPictureServiceImpl implements NasaPictureService {
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos";
    private static final String API_KEY = "DEMO_KEY";

    private final RestTemplate restTemplate;

    @Autowired
    public NasaPictureServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SneakyThrows
    @Override
    public String getLargestPictureURl(int sol) {
        var apiUri = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("sol", sol)
                .queryParam("api_key", API_KEY)
                .build()
                .toUriString();

        var jsonNode = restTemplate.getForObject(apiUri, JsonNode.class);

        List<String> originalUrls = ofNullable(jsonNode).map(jnode -> jnode.get("photos"))
                .map(photos -> photos.findValuesAsText("img_src"))
                .orElse(emptyList());

        var sizeToUrlMap = originalUrls.stream()
                .collect(toMap(identity(),
                        originalUrl -> getSize(getLocation(URI.create(originalUrl)))));

        var maxEntry = sizeToUrlMap.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(NoSuchElementException::new);

        return maxEntry.getKey();
    }

    private URI getLocation(URI originalUrl) {
        return restTemplate.headForHeaders(originalUrl)
                .getLocation();
    }

    private Long getSize(URI location) {
        return restTemplate.headForHeaders(location)
                .getContentLength();
    }
}

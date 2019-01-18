package com.ads.ticket.api.controller;

import com.ads.ticket.api.data.entity.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Component
public class TokenHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHelper.class);
    private RestTemplate restTemplate = new RestTemplate();
    private String baseURL = "http://localhost:8080/login";

    // TEST READ
    @Test
    public String getAccessToken(String user, String password) {

        HttpEntity<LoginModel> httpEntity = new HttpEntity<>(new LoginModel(user, password));
        ResponseEntity<String> authResponse = this.restTemplate.exchange(baseURL, HttpMethod.POST, httpEntity, String.class);

        String accessToken = authResponse.getHeaders().get("Authorization").get(0).toString();
        LOGGER.debug(accessToken);
        return accessToken;
    }

}

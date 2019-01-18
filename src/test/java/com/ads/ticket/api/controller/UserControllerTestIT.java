package com.ads.ticket.api.controller;

import com.ads.ticket.api.data.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTestIT {
    private String CREATE_USER = "http://localhost:8080/users/sign-up";
    private String USER_BASE_URL = "http://localhost:8080/users";
    private RestTemplate restTemplate = new RestTemplate();
    TokenHelper tokenHelper = new TokenHelper();

    @Test
    public void create_user_OK() {
        User userModel = getUser();

        User user = createUser(userModel);
        assertNotNull(user);

        deleteUser(user.getId());
    }

    private User getUser() {
        return new User(null, "first-name", "last-name",
        		"123", "anbu@mail.com", "anbu@mail.com", true);
    }

    @Test
    public void delete_user_OK() {

        User user = createUser(getUser());
        HttpEntity<String> httpEntity = getAuthorizationHeader();
        ResponseEntity responseEntity = this.restTemplate.exchange(USER_BASE_URL + "/" + user.getId(), HttpMethod.DELETE, httpEntity, String.class);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }
    private User createUser(User userModel) {
        HttpEntity<User> httpEntity = new HttpEntity<>(userModel);
        try {
            return this.restTemplate.exchange(CREATE_USER, HttpMethod.PUT, httpEntity, User.class).getBody();
        } catch (HttpClientErrorException e) {

        }
        return null;
    }
    private void deleteUser(Long userId) {

        HttpEntity<String> httpEntity = getAuthorizationHeader();
        this.restTemplate.exchange(USER_BASE_URL + "/" + userId, HttpMethod.DELETE, httpEntity, String.class);
    }

    private HttpEntity<String> getAuthorizationHeader() {
        String accessToken = tokenHelper.getAccessToken("anbu@mail.com", "123");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        return new HttpEntity<>(headers);
    }
}

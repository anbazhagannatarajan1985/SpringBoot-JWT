package com.ads.ticket.api.data.repo;

import com.ads.ticket.api.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

}
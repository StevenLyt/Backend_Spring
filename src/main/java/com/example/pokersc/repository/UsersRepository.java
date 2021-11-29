package com.example.pokersc.repository;

import com.example.pokersc.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}

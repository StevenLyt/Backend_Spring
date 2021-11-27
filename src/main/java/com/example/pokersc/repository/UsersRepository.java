package com.example.pokersc.repository;

import com.example.pokersc.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}

package com.example.pokersc.repository;

import com.example.pokersc.entity.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {
}

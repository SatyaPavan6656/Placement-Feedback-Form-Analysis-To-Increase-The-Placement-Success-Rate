package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.models.Option;

public interface OptionRepository extends CrudRepository<Option, Integer> {
}

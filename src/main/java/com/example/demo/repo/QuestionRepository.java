package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.models.Question;

public interface QuestionRepository extends CrudRepository<Question, Integer> {

	void deleteById(int questionId);

	Question findFirstByOrderById();

	Optional<Question> findById(Long questionId);
}


package com.example.demo.repo;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Signup;

public interface Signuprepo extends CrudRepository<Signup, Integer>  {
    Signup findByUsername(String username);
    Signup findByEmail(String email);
}

package com.example.demo.repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.models.Response;

public interface ResponseRepository extends CrudRepository<Response, Long> {
    List<Response> findByQuestion_Id(int questionId);
	List<Response> findByCompanyNameAndResponseDate(String companyName, String responseDate);
}



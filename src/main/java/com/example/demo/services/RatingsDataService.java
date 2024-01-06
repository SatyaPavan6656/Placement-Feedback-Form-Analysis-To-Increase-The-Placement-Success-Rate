package com.example.demo.services;

import com.example.demo.models.RatingsData;
import com.example.demo.projections.RatingsDataProjection;
import com.example.demo.repo.RatingsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingsDataService {

    private final RatingsDataRepository ratingsDataRepository;

    @Autowired
    public RatingsDataService(RatingsDataRepository ratingsDataRepository) {
        this.ratingsDataRepository = ratingsDataRepository;
    }

    public List<RatingsDataProjection> getRatingsByDriveDateAndCompanyNameAndQuestionId(String drivedate, String companyname) {
        return ratingsDataRepository.findByDriveDateAndCompanyNameAndQuestionId(drivedate, companyname);
    }
}

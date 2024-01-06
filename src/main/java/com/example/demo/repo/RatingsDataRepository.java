package com.example.demo.repo;

import com.example.demo.models.RatingsData;
import com.example.demo.projections.RatingsDataProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingsDataRepository extends CrudRepository<RatingsData, Long> {

    @Query("SELECT rd.drivedate as drivedate, rd.companyname as companyname, rd.questiontext as questiontext, rd.avgratingrolls as avgratingrolls, rd.poorratingrolls as poorratingrolls FROM RatingsData rd WHERE rd.drivedate = :drivedate AND rd.companyname = :companyname")
    List<RatingsDataProjection> findByDriveDateAndCompanyNameAndQuestionId(
            @Param("drivedate") String drivedate,
            @Param("companyname") String companyname
    );
}

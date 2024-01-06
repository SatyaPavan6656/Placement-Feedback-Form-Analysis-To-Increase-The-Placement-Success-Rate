package com.example.demo.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratingsdata")
public class RatingsData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String drivedate;

    private String companyname;

    private Long questionid;

    private String avgratingrolls;

    private String poorratingrolls;
    
    private String questiontext;

    // getters and setters
    
    public String getQuestiontext() {
		return questiontext;
	}

	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getdrivedate() {
        return drivedate;
    }

    public void setdrivedate(String driveDate) {
        this.drivedate = driveDate;
    }

    public String getcompanyname() {
        return companyname;
    }

    public void setcompanyName(String companyName) {
        this.companyname = companyName;
    }

    public Long getquestionid() {
        return questionid;
    }

    public void setquestionid(Long questionId) {
        this.questionid = questionId;
    }

    public String getavgratingrolls() {
        return avgratingrolls;
    }

    public void setavgratingrolls(String avgRatingRolls) {
        this.avgratingrolls = avgRatingRolls;
    }

    public String getpoorratingrolls() {
        return poorratingrolls;
    }

    public void setpoorratingrolls(String poorRatingRolls) {
        this.poorratingrolls = poorRatingRolls;
    }
}
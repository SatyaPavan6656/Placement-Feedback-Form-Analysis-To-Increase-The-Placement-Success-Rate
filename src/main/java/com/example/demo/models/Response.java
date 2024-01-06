package com.example.demo.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity 
public class Response {

    @Id 
    @GeneratedValue(strategy = GenerationType .IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "question_id") // This should match the actual column name in the database
    private Question question;

    @ManyToOne
    private Option selectedOption;

    public String companyName;
    private String rollNo;
    @Temporal(TemporalType.DATE)
    @Column(name = "response_date")
    private String responseDate;


    // Getters and Setters
    
	public String getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(String dateString) {
		this.responseDate = dateString;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Option getSelectedOption() {
		return selectedOption;
	}
	public void setSelectedOption(Option selectedOption) {
		this.selectedOption = selectedOption;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
    
}

package com.example.demo.controller;

import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.models.Option;
import com.example.demo.models.Question;
import com.example.demo.models.Signup;
import com.example.demo.projections.RatingsDataProjection;
import com.example.demo.models.Response;
import com.example.demo.repo.OptionRepository;
import com.example.demo.repo.QuestionRepository;
import com.example.demo.repo.ResponseRepository;
import com.example.demo.repo.Signuprepo;
import com.example.demo.services.MailService;
import com.example.demo.services.RatingsDataService;

import jakarta.mail.MessagingException;

@Controller
public class HomeController {

    @Autowired
    private MailService emailService;

    @Autowired
    Signuprepo repo;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private ResponseRepository responseRepository;
    
    @Autowired
    private RatingsDataService ratingsDataService;

    
    public String name1=""; 
    @ModelAttribute("commonUsername")
    public String commonUsername() {
        return name1; // Set your default username here
    }
    public String password1="";
    @ModelAttribute("commonPassword")
    public String commonPassword() {
        return password1; // Set your default password here
    }
    
    @RequestMapping("signup")
    public String signup() {
        return "signup";
    }
    //Signup Congratulations mail and Admin Mail
    @RequestMapping("signupdata")
    public String signupdata(@ModelAttribute Signup s, Model model) throws MessagingException {
        repo.save(s);

        try {
            // Send a confirmation email to the user
            emailService.sendCongratulationsEmail(s, s.getEmail());
            //Send a notification email to the admin
            emailService.sendAdminNotificationEmail(s);
            model.addAttribute("name",s.getUsername());
            return "Aftersignup";
        } catch (MailException e) {
            model.addAttribute("error", "Error sending email: " + e.getMessage());
            return "error"; // Assuming you have a view named "error"
        }
    }
    private Signup user=new Signup();
    @ModelAttribute("user2")
    public Signup demo() 
    {
    	
    	if(user==null) {
    	user.setUsername(null);
    	user.setEmail(null);
    	user.setMobile(null);
    	user.setPassword(null);
    	user.setReenterpasword(null);
    	user.setId(0);
    	}
    	return user;
    }
    @RequestMapping("login")
    public String login(@ModelAttribute Signup s, @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Signup user1 = repo.findByUsername(username);
        user.setUsername(user1.getUsername());
        user.setPassword(user1.getPassword());
        if (user != null && user.getPassword().equals(password)) {
        	System.out.print(name1);
            model.addAttribute("name", user.getUsername());
            return showFeedbackForm(model);
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "signup";
        }
    }


    @RequestMapping("sendEmail")
    public String sendEmail(@RequestParam String email) {
        try {
            emailService.sendCongratulationsEmail(null, email);
            emailService.sendAdminNotificationEmail(null);
            return "redirect:/Aftersignup"; // Assuming you have a view named "success"
        } catch (Exception e) {
            return "redirect:/error"; // Assuming you have a view named "error"
        }
    }

    
    @RequestMapping("showFeedbackForm")
    @GetMapping("/feedback")
    public String showFeedbackForm(Model model) {
        Iterable<Question> questions = questionRepository.findAll();
        Iterable<Option> options = optionRepository.findAll();

        model.addAttribute("questions", questions);
        model.addAttribute("options", options);

        return "feedback";
    }
    
    @PostMapping("/saveResponse")
    public String saveResponse(@RequestParam Map<String, String> requestParams,
                               @RequestParam String companyName, @RequestParam String regNo,
                               Model model) {

        Date currentDate = new Date(); // Get the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define date format

        String dateString = dateFormat.format(currentDate);

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            if (entry.getKey().startsWith("question_")) {
            	int questionId = Integer.parseInt(entry.getKey().replace("question_", ""));
                int selectedOptionId = Integer.parseInt(entry.getValue());

                Question question = questionRepository.findById(questionId).orElse(null);
                Option selectedOption = optionRepository.findById(selectedOptionId).orElse(null);

                if (question != null && selectedOption != null) {
                    Response response = new Response();
                    response.setQuestion(question);
                    response.setSelectedOption(selectedOption);
                    response.setCompanyName(companyName);
                    response.setRollNo(regNo);
                    response.setResponseDate(dateString);

                    responseRepository.save(response);
                    System.out.println("Saving response: " + response.toString());
                }
            }
        }

        return "redirect:/thankyou";
    }


    @GetMapping("/thankyou")
    public String thankYouPage() {
      return "thankyou";
    }
    	
    
    //Add or Remove Questions Funcionality
    @GetMapping("/manageQuestions")
    public String showQuestions(Model model) {
        Iterable<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "/manageQuestions";
    }

    @PostMapping("/manageQuestions/add")
    public String addQuestion(@RequestParam String questionText) {
        Question newQuestion = new Question();
        newQuestion.setQuestionText(questionText);
        questionRepository.save(newQuestion);
        return "redirect:/manageQuestions";
    }

    @PostMapping("/manageQuestions/remove/{questionId}")
    public String removeQuestion(@PathVariable int questionId) {
        // Check if there are any responses associated with this question
        List<Response> responses = responseRepository.findByQuestion_Id(questionId);

        if (!responses.isEmpty()) {
            // Get a different question to reassign the responses (you need to implement this logic)
            Question newQuestion = questionRepository.findFirstByOrderById();

            if (newQuestion != null) {
                for (Response response : responses) {
                    // Reassign the response to the new question
                    response.setQuestion(newQuestion);
                    responseRepository.save(response);
                }
            } else {
                // Handle the case where there are no questions available to reassign
                // You might want to display an error message to the user
                return "redirect:/manageQuestions?error=noQuestionsAvailable";
            }
        }

        // Now, you can safely delete the question
        questionRepository.deleteById(questionId);

        return "redirect:/manageQuestions";
    }

    //Results		
    @GetMapping("/getChartData")
    public ResponseEntity<Map<String, Object>> getChartData(
            @RequestParam String companyName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date responseDate) throws ParseException {

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(responseDate);

        List<Response> responses = responseRepository.findByCompanyNameAndResponseDate(companyName, formattedDate);

        List<Question> questions = (List<Question>) questionRepository.findAll();

        Map<String, Object> chartData = new HashMap<>();

        List<String> labels = new ArrayList<>();
        List<Integer> excellentCounts = new ArrayList<>();
        List<Integer> goodCounts = new ArrayList<>();
        List<Integer> averageCounts = new ArrayList<>();
        List<Integer> poorCounts = new ArrayList<>();

        for (Question question : questions) {
            labels.add(question.getQuestionText());

            int excellentCount = (int) responses.stream()
                    .filter(response -> response.getQuestion().getId() == question.getId() && response.getSelectedOption().getOptionText().equals("Excellent"))
                    .count();

            int goodCount = (int) responses.stream()
                    .filter(response -> response.getQuestion().getId() == question.getId() && response.getSelectedOption().getOptionText().equals("Good"))
                    .count();

            int averageCount = (int) responses.stream()
                    .filter(response -> response.getQuestion().getId() == question.getId() && response.getSelectedOption().getOptionText().equals("Average"))
                    .count();

            int poorCount = (int) responses.stream()
                    .filter(response -> response.getQuestion().getId() == question.getId() && response.getSelectedOption().getOptionText().equals("Poor"))
                    .count();

            excellentCounts.add(excellentCount);
            goodCounts.add(goodCount);
            averageCounts.add(averageCount);
            poorCounts.add(poorCount);
        }

        chartData.put("labels", labels);
        chartData.put("excellentCounts", excellentCounts);
        chartData.put("goodCounts", goodCounts);
        chartData.put("averageCounts", averageCounts);
        chartData.put("poorCounts", poorCounts);

        return ResponseEntity.ok(chartData);
    }
    @GetMapping("/showResults")
    public String showResultsPage(Model model) {
        return "results";
    }
    
    @GetMapping("/displaySortedRatings")
    public String displaySortedRatings(@RequestParam String responseDate,@RequestParam String companyName, Model model) {
        // Assuming you have a method in your service to retrieve the sorted ratings
        List<RatingsDataProjection> sortedRatings = ratingsDataService.getRatingsByDriveDateAndCompanyNameAndQuestionId(responseDate, companyName);
        if(sortedRatings.size()==0) sortedRatings.add(null);
        model.addAttribute("ratingsList", sortedRatings);

        return "report";
    }
    
    @GetMapping("/showReport")
    public String showReportsPage(Model model) {
        return "report";
    }
}
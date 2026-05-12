package com.email.writer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailController {

    @GetMapping("/test")
    public String test() {
        return "Email Writer API is working";
    }

    @PostMapping("/generate")
    public String generateEmailReply(@RequestBody EmailRequest request) {
        String emailContent = request.getEmailContent();
        String tone = request.getTone();

        if (emailContent == null || emailContent.trim().isEmpty()) {
            return "Please provide email content.";
        }

        if (tone == null || tone.trim().isEmpty()) {
            tone = "professional";
        }

        tone = tone.toLowerCase();

        if (tone.equals("friendly")) {
            return "Hey, thanks for reaching out! Tomorrow works for me. Let me know what time is convenient for you, and I’ll be happy to discuss the project.";
        } 
        else if (tone.equals("formal")) {
            return "Dear Sir/Madam, thank you for your email. I would be available to discuss the project tomorrow. Kindly let me know a suitable time for the meeting.";
        } 
        else if (tone.equals("short")) {
            return "Sure, tomorrow works. Please let me know the time.";
        } 
        else {
            return "Hi, thank you for your email. Sure, we can schedule a meeting to discuss the project. Please let me know a convenient time, and I will be happy to join.";
        }
    }
}
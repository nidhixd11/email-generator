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

        return "Generated " + tone + " reply for: " + emailContent;
    }
}
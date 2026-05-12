# AI-Powered Email Reply Generator

This is a Spring Boot REST API project for generating email replies. It provides API endpoints to test the backend and generate a basic email reply based on email content and tone.

## Features

- Spring Boot REST API
- Test endpoint to check if the backend is running
- Email reply generation endpoint
- Supports email content and tone input
- Can be extended with Gemini API for AI-generated replies

## Tech Stack

- Java
- Spring Boot
- Maven
- REST API

## API Endpoints

### Test API

Method: GET

URL: http://localhost:8080/api/email/test

Response: Email Writer API is working

### Generate Email Reply

Method: POST

URL: http://localhost:8080/api/email/generate

Request Body:

{
  "emailContent": "Can we schedule a meeting tomorrow to discuss the project?",
  "tone": "professional"
}

Response: Generated professional reply for: Can we schedule a meeting tomorrow to discuss the project?

## How to Run

Clone the repository:

git clone https://github.com/nidhixd11/email-generator.git

cd email-generator

Run the project on Windows PowerShell:

.\mvnw.cmd spring-boot:run

Run the project on Mac/Linux:

./mvnw spring-boot:run

The backend will run on:

http://localhost:8080

## Note

This project is currently a working backend prototype. It can be extended further by integrating Gemini API or OpenAI API for real AI-generated email replies.

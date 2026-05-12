document.getElementById("generateBtn").addEventListener("click", async function () {
  const emailContent = document.getElementById("emailContent").value;
  const tone = document.getElementById("tone").value;
  const resultDiv = document.getElementById("result");

  if (!emailContent.trim()) {
    resultDiv.innerText = "Please enter email content.";
    return;
  }

  resultDiv.innerText = "Generating reply...";

  try {
    const response = await fetch("http://localhost:8080/api/email/generate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        emailContent: emailContent,
        tone: tone
      })
    });

    const data = await response.text();
    resultDiv.innerText = data;
  } catch (error) {
    resultDiv.innerText = "Error connecting to backend. Please make sure the Spring Boot server is running.";
  }
});
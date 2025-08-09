document.addEventListener("DOMContentLoaded", function () {
  const getStartedBtn = document.getElementById("getStartedBtn");
  const formContainer = document.getElementById("studentFormContainer");

  getStartedBtn.addEventListener("click", function () {
    formContainer.style.display = "block";
    getStartedBtn.style.display = "none";
  });

  const studentForm = document.getElementById("studentForm");

  studentForm.addEventListener("submit", async function (e) {
    e.preventDefault();

    const student = {
      name: document.getElementById("name").value,
      course: document.getElementById("course").value,
      fee: parseFloat(document.getElementById("fee").value),
    };

	
	
	
	
    try {
      const response = await fetch("/api/students", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(student),
      });

      if (response.ok) {
        alert("Student added successfully!");
        studentForm.reset();
      } else {
        alert("Failed to add student.");
      }
    } catch (error) {
      alert("Error: " + error.message);
    }
  });
});
      






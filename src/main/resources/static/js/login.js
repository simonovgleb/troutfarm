document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("login-form");
  const errorMessage = document.getElementById("error-message");

  if (localStorage.getItem("token")) {
    window.location.href = "/dashboard.html";
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    errorMessage.classList.add("d-none");

    const payload = {
      username: form.email.value,
      password: form.password.value,
    };

    try {
      const response = await fetch("/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        response.json().then(data => {
            localStorage.setItem("token", data.token);
            window.location.href = "/dashboard.html";
        });
      } else {
        errorMessage.classList.remove("d-none");
      }
    } catch (err) {
      console.error(err);
      errorMessage.textContent = "Network error.";
      errorMessage.classList.remove("d-none");
    }
  });
});

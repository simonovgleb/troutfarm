document.addEventListener("DOMContentLoaded", async () => {
    try {
        await initAuth();

        const user = window.currentUser ?? JSON.parse(sessionStorage.getItem("user"));

        if (isAdmin()) {
          const usersLink = document.getElementById("users-link");
          if (usersLink) {
            usersLink.style.display = "block";
          }
        }

        const welcome = document.getElementById("welcome-username");
        if (welcome && user && user.name) {
          welcome.textContent = `Добро пожаловать, ${user.name}!`;
        }

    } catch (error) {
        console.error("Auth error:", error);
        localStorage.removeItem("token");
        window.location.href = "/login.html";
    }
});
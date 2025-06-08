document.addEventListener("DOMContentLoaded", async () => {
    try {
        await initAuth();

        const user = window.currentUser ?? JSON.parse(sessionStorage.getItem("user"));

        if (isAdmin()) {
          displayAdminLinks([
            document.getElementById("users-link"),
            document.getElementById("tanks-link")
          ]);
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

function displayAdminLinks(elements) {
    elements
        .filter(el => el)
        .forEach(el => {
            el.style.display = "block";
        });
}
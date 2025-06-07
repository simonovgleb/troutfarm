async function checkAuthAndLoadUser() {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "/login.html";
    return;
  }

  try {
    const response = await fetch("/auth/user", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error("Unauthorized");
    }

    const user = await response.json();

    sessionStorage.setItem("user", JSON.stringify(user));
    window.currentUser = user;

  } catch (err) {
    console.error(err);
    localStorage.removeItem("token");
    sessionStorage.removeItem("user");
    window.location.href = "/login.html";
  }
}

function setupLogout() {
  const logoutBtn = document.getElementById("logoutBtn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
      localStorage.removeItem("token");
      sessionStorage.removeItem("user");
      window.location.href = "/login.html";
    });
  }
}

function isAdmin() {
   const user = window.currentUser ?? JSON.parse(sessionStorage.getItem("user"));
   return user && user.authorities.includes("ROLE_ADMIN");
}

function isOperator() {
   const user = window.currentUser ?? JSON.parse(sessionStorage.getItem("user"));
   return user && user.authorities.includes("ROLE_OPERATOR");
}

async function initAuth() {
    await checkAuthAndLoadUser();
    setupLogout();
}
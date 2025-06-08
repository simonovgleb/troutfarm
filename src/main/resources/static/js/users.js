document.addEventListener("DOMContentLoaded", async () => {
  const userForm = document.getElementById("userForm");
  const userModalLabel = document.getElementById("userModalLabel");
  const userIdInput = document.getElementById("userId");
  const nameInput = document.getElementById("userName");
  const emailInput = document.getElementById("userEmail");
  const passwordInput = document.getElementById("userPassword");
  const rolesSelect = document.getElementById("userRoles");
  const activeInput = document.getElementById("userActive");

  await initAuth();
  if (!isAdmin()) {
    window.location.href = "/dashboard.html";
  }
  
  fetchUsers();

  function fetchUsers() {
    fetch("/api/users", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(users => renderUsers(users));
  }

  function renderUsers(users) {
    const container = document.getElementById("userContainer");
    container.innerHTML = "";
    users.forEach(user => {
      const template = document.getElementById("userTemplate");
      const clone = template.content.cloneNode(true);
      clone.querySelector(".user-id").textContent = user.id;
      clone.querySelector(".user-name").textContent = user.name;
      clone.querySelector(".user-email").textContent = user.email;
      clone.querySelector(".user-roles").textContent = user.role;
      clone.querySelector(".user-active").textContent = user.active ? "Активен" : "Не активен";
      container.appendChild(clone);
    });
  }

  window.openCreateUserModal = function () {
    userModalLabel.textContent = "Новый пользователь";
    userForm.reset();
    userIdInput.value = "";
    passwordInput.placeholder = "Введите пароль";
  };

  window.editUser = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".user-id").textContent;
    fetch(`/api/users/${id}`, {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(user => {
        userModalLabel.textContent = "Редактировать пользователя";
        userIdInput.value = user.id;
        nameInput.value = user.name;
        emailInput.value = user.email;
        passwordInput.value = "";
        passwordInput.placeholder="Заполните только, если хотите изменить текущий пароль";
        rolesSelect.value = user.role;
        activeInput.checked = user.active;
        new bootstrap.Modal(document.getElementById("userModal")).show();
      });
  };

  window.deleteUser = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".user-id").textContent;
    if (!confirm("Удалить пользователя?")) {
        return;
    }
    fetch(`/api/users/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    }).then(res => {
        if (!res.ok) {
          alert("Не удалось выполнить удаление. Проверьте связи с другими сущностями.");
        }
        fetchUsers();
    });
  };

  userForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = userIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `/api/users/${id}` : "/api/users";
    const body = {
      name: nameInput.value,
      email: emailInput.value,
      password: passwordInput.value || null,
      role: rolesSelect.value,
      isActive: activeInput.checked,
    };
    fetch(url, {
      method,
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(() => {
      bootstrap.Modal.getInstance(document.getElementById("userModal")).hide();
      fetchUsers();
    });
  });
});

document.getElementById("togglePassword").addEventListener("click", () => {
  const passwordInput = document.getElementById("userPassword");
  const icon = document.getElementById("togglePasswordIcon");
  const isVisible = passwordInput.type === "text";
  passwordInput.type = isVisible ? "password" : "text";
  icon.className = isVisible ? "bi bi-eye" : "bi bi-eye-slash";
});
let tasks = [];

async function loadUserAndTasks() {
  try {
    await initAuth();
    if (isAdmin()) {
        document.getElementById('createTaskBtn').classList.remove('d-none');
    }
    loadTasks();
    loadOperators();
  } catch (err) {
    console.error(err);
    location.href = "/login.html";
  }
}

async function loadTasks() {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("Unauthorized");
  }

  const res = await fetch("/api/tasks", {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });
  if (!res.ok) {
    throw new Error("Не удалось загрузить задачи");
  }
  tasks = await res.json();
  renderTasks();
}

function loadOperators() {
    fetch("/api/users?role=OPERATOR", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
    .then(res => res.json())
    .then(users => {
        const taskAssignedToSelect = document.getElementById('taskAssignedTo');
        taskAssignedToSelect.innerHTML = "<option value=''>-- Не выбрано --</option>";
        users.forEach(user => {
          const option = document.createElement("option");
          option.value = user.id;
          option.textContent = user.name;
          taskAssignedToSelect.appendChild(option);
        });
    });
}

function renderTasks() {
  const container = document.getElementById("taskContainer");
  container.innerHTML = "";

  const template = document.getElementById("taskTemplate");

  tasks.forEach(task => {
    const clone = template.content.cloneNode(true);

    clone.querySelector(".task-title").textContent = task.title;
    clone.querySelector(".task-description").textContent = task.description;
    clone.querySelector(".task-status").textContent = statusLabel(task.status);
    clone.querySelector(".task-due").textContent = `К сроку: ${formatDate(task.dueDate)}`;
    clone.querySelector(".task-created-by").textContent = task.createdBy?.name || "—";
    clone.querySelector(".task-assigned-to").textContent = task.assignedTo?.name || "—";
    clone.querySelector('.task-id').textContent = task.id;

    if (isAdmin()) {
      clone.querySelector(".task-edit").classList.remove("d-none");
      clone.querySelector(".task-del").classList.remove("d-none");
      clone.querySelector('.task-actions').classList.remove('d-none');
    } else if (isOperator() && task.status !== "COMPLETED") {
      clone.querySelector(".task-complete").classList.remove("d-none");
      clone.querySelector(".task-actions").classList.remove("d-none");
    }

    container.appendChild(clone);
  });
}

function statusLabel(status) {
  switch (status) {
    case "PENDING": return "В ожидании";
    case "IN_PROGRESS": return "В работе";
    case "COMPLETED": return "Завершено";
    case "CANCELLED": return "Отменено";
    default: return status;
  }
}

function formatDate(dateStr) {
  const date = new Date(dateStr);
  return date.toLocaleString("ru-RU", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit"
  });
}

function toLocalInputValue(offsetDateTimeString) {
  const date = new Date(offsetDateTimeString);
  const local = new Date(date.getTime() - date.getTimezoneOffset() * 60000)
    .toISOString()
    .slice(0, 16);
  return local;
}


function openCreateModal() {
  document.getElementById('taskForm').reset();
  document.getElementById('taskId').value = '';
  document.getElementById('taskModalLabel').textContent = 'Новая задача';
  document.getElementById('taskStatusGroup').classList.add("d-none");
}

function editTask(button) {
  const item = button.closest('.list-group-item');
  const id = item.querySelector('.task-id').textContent;
  const task = tasks.find(t => t.id === id);
  if (!task) {
    return;
  }

  document.getElementById('taskId').value = task.id;
  document.getElementById('taskTitle').value = task.title;
  document.getElementById('taskDescription').value = task.description;
  document.getElementById('taskDueDate').value = toLocalInputValue(task.dueDate);
  document.getElementById('taskStatus').value = task.status;
  document.getElementById('taskAssignedTo').value = task.assignedTo?.id || "";
  document.getElementById('taskStatusGroup').classList.remove("d-none");
  document.getElementById('taskModalLabel').textContent = 'Редактировать задачу';
  new bootstrap.Modal(document.getElementById('taskModal')).show();
}

async function deleteTask(button) {
  const item = button.closest('.list-group-item');
  const id = item.querySelector('.task-id').textContent;

  if (!confirm('Удалить задачу?')) {
    return;
  }

  await fetch('/api/tasks/' + id, {
    method: 'DELETE',
    headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
  });
  await loadTasks();
}

async function saveTask(event) {
  event.preventDefault();

  const taskId = document.getElementById('taskId').value;
  const task = {
    title: document.getElementById('taskTitle').value,
    description: document.getElementById('taskDescription').value,
    dueDate: new Date(document.getElementById('taskDueDate').value).toISOString(),
    assignedToId: document.getElementById('taskAssignedTo').value,
  };
  if (taskId) {
    task.status = document.getElementById('taskStatus').value;
  }

  const method = taskId ? 'PUT' : 'POST';
  const url = `/api/tasks${taskId ? `/${taskId}` : ''}`;

  await fetch(url, {
    method,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    },
    body: JSON.stringify(task)
  });

  bootstrap.Modal.getInstance(document.getElementById('taskModal')).hide();
  await loadTasks();
}

function completeTask(btn) {
  const id = btn.closest(".list-group-item").querySelector(".task-id").textContent;
  if (!confirm("Отметить задачу как выполненную?")) {
    return;
  }
  fetch(`/api/tasks/${id}/complete`, {
    method: "PATCH",
    headers: {
      "Authorization": "Bearer " + localStorage.getItem("token"),
      "Content-Type": "application/json"
    },
  }).then(() => loadTasks());
};

document.getElementById('taskForm').addEventListener('submit', saveTask);

document.addEventListener("DOMContentLoaded", loadUserAndTasks);

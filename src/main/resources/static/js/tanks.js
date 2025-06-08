document.addEventListener("DOMContentLoaded", async () => {
  const tankForm = document.getElementById("tankForm");
  const tankModalLabel = document.getElementById("tankModalLabel");
  const tankIdInput = document.getElementById("tankId");
  const tankNameInput = document.getElementById("tankName");
  const tankCapacityInput = document.getElementById("tankCapacity");
  const tankTemperatureInput = document.getElementById("tankTemperature");
  const tankOxygenInput = document.getElementById("tankOxygen");
  const batchSelect = document.getElementById("batchSelect");

  await initAuth();
  if (!isAdmin()) {
    window.location.href = "/dashboard.html";
  }
  fetchTanks();
  loadBatches();

  function fetchTanks() {
    fetch("/api/tanks", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(tanks => renderTanks(tanks));
  }

  function renderTanks(tanks) {
    const container = document.getElementById("tankContainer");
    container.innerHTML = "";
    tanks.forEach(tank => {
      const template = document.getElementById("tankTemplate");
      const clone = template.content.cloneNode(true);
      clone.querySelector(".tank-id").textContent = tank.id;
      clone.querySelector(".tank-name").textContent = tank.name;
      clone.querySelector(".tank-capacity").textContent = tank.capacity;
      clone.querySelector(".tank-temperature").textContent = tank.temperature;
      clone.querySelector(".tank-oxygen").textContent = tank.oxygenLevel;
      clone.querySelector(".tank-batch").textContent = tank.batch?.name || "-";
      container.appendChild(clone);
    });
  }

  function loadBatches() {
    fetch("/api/fish-batches", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
    .then(res => res.json())
    .then(batches => {
       batchSelect.innerHTML = "<option value=''>-- Не выбрано --</option>";
       batches.forEach(batch => {
         const option = document.createElement("option");
         option.value = batch.id;
         option.textContent = batch.name;
         batchSelect.appendChild(option);
       });
    });
  }

  window.openCreateTankModal = function () {
    tankModalLabel.textContent = "Новый бассейн";
    document.getElementById("tankForm").reset();
    tankIdInput.value = "";
  };

  window.editTank = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".tank-id").textContent;
    fetch(`/api/tanks/${id}`, {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(tank => {
        tankModalLabel.textContent = "Редактировать бассейн";
        tankIdInput.value = tank.id;
        tankNameInput.value = tank.name;
        tankCapacityInput.value = tank.capacity;
        tankTemperatureInput.value = tank.temperature;
        tankOxygenInput.value = tank.oxygenLevel;
        batchSelect.value = tank.batch?.id;
        new bootstrap.Modal(document.getElementById("tankModal")).show();
      });
  };

  window.deleteTank = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".tank-id").textContent;
    if (!confirm("Удалить бассейн?")) {
      return;
    }
    fetch(`/api/tanks/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    }).then(res => {
      if (!res.ok) {
        alert("Не удалось выполнить удаление. Проверьте наличие связей с другими сущностями.");
      }
      fetchTanks();
    });
  };

  tankForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = tankIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `/api/tanks/${id}` : "/api/tanks";
    const body = {
      name: tankNameInput.value,
      capacity: tankCapacityInput.value,
      temperature: tankTemperatureInput.value,
      oxygenLevel: tankOxygenInput.value,
      batchId: batchSelect.value,
    };
    fetch(url, {
      method,
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(() => {
      bootstrap.Modal.getInstance(document.getElementById("tankModal")).hide();
      fetchTanks();
    });
  });
});

document.addEventListener("DOMContentLoaded", async () => {
  const mortalityForm = document.getElementById("mortalityForm");
  const mortalityModalLabel = document.getElementById("mortalityModalLabel");
  const mortalityIdInput = document.getElementById("mortalityId");
  const mortalityDateInput = document.getElementById("mortalityDate");
  const batchSelect = document.getElementById("batchSelect");
  const causeSelect = document.getElementById("mortalityReason");
  const countInput = document.getElementById("mortalityCount");
  const mortalityUserSelect = document.getElementById("mortalityUser");

  await initAuth();
  
  fetchMortalities();
  loadBatches();
  loadOperators();

  function fetchMortalities() {
    fetch("/api/mortalities", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(data => renderMortalities(data));
  }

  function renderMortalities(list) {
    const container = document.getElementById("mortalityContainer");
    container.innerHTML = "";
    list.forEach(m => {
      const template = document.getElementById("mortalityTemplate");
      const clone = template.content.cloneNode(true);
      clone.querySelector(".mortality-id").textContent = m.id;
      clone.querySelector(".mortality-date").textContent = formatDate(m.dateTime);
      clone.querySelector(".mortality-batch").textContent = m.batch?.name || "-";
      clone.querySelector(".mortality-reason").textContent = deathReasonLabel(m.reason);
      clone.querySelector(".mortality-count").textContent = m.count;
      clone.querySelector(".mortality-operator").textContent = m.operator?.name || "-";
      if (isAdmin()) {
        clone.querySelector(".mortality-actions").classList.remove("d-none");
      }
      container.appendChild(clone);
    });
  }

  function deathReasonLabel(reason) {
    switch(reason) {
        case "DISEASE": return "Болезнь";
        case "PREDATOR": return "Хищник";
        case "ACCIDENT": return "Происшествие";
        case "UNKNOWN": return "Неизвестно";
        default: return reason;
    }
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

  function loadOperators() {
    fetch("/api/users?role=OPERATOR", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(users => {
        mortalityUserSelect.innerHTML = "<option value=''>-- Не выбрано --</option>";
        users.forEach(user => {
          const option = document.createElement("option");
          option.value = user.id;
          option.textContent = user.name;
          mortalityUserSelect.appendChild(option);
        });
      });
  }

  window.openCreateMortalityModal = function () {
    mortalityModalLabel.textContent = "Новое событие";
    document.getElementById("mortalityForm").reset();
    mortalityIdInput.value = "";
    if (isOperator()) {
      mortalityUserSelect.value = currentUser.id;
      mortalityUserSelect.disabled = true;
    }
  };

  window.editMortality = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".mortality-id").textContent;
    fetch(`/api/mortalities/${id}`, {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(m => {
        mortalityModalLabel.textContent = "Редактировать событие";
        mortalityIdInput.value = m.id;
        mortalityDateInput.value = toLocalInputValue(m.dateTime);
        batchSelect.value = m.batch?.id || "";
        causeSelect.value = m.reason;
        countInput.value = m.count;
        mortalityUserSelect.value = m.operator?.id || "";
        new bootstrap.Modal(document.getElementById("mortalityModal")).show();
      });
  };

  window.deleteMortality = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".mortality-id").textContent;
    if (!confirm("Удалить запись о смертности?")) return;
    fetch(`/api/mortalities/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    }).then(() => fetchMortalities());
  };

  mortalityForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = mortalityIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `/api/mortalities/${id}` : "/api/mortalities";
    const body = {
      dateTime: new Date(mortalityDateInput.value).toISOString(),
      batchId: batchSelect.value,
      reason: causeSelect.value,
      count: countInput.value,
      operatorId: mortalityUserSelect.value,
    };
    fetch(url, {
      method,
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(() => {
      bootstrap.Modal.getInstance(document.getElementById("mortalityModal")).hide();
      fetchMortalities();
    });
  });
});

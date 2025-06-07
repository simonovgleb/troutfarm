document.addEventListener("DOMContentLoaded", async () => {
  const feedingForm = document.getElementById("feedingForm");
  const feedingModalLabel = document.getElementById("feedingModalLabel");
  const feedingIdInput = document.getElementById("feedingId");
  const feedingDateInput = document.getElementById("feedingDate");
  const batchSelect = document.getElementById("feedingBatch");
  const foodTypeSelect = document.getElementById("feedingType");
  const amountInput = document.getElementById("feedingAmount");
  const feedingUserSelect = document.getElementById("feedingUserSelect");

  await initAuth();
  if (isAdmin() || isOperator()) {
    document.getElementById("createFeedingBtn").classList.remove("d-none");
  }
  fetchFeedings();
  loadBatches();
  loadOperators();

  function fetchFeedings() {
    fetch("/api/feedings", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(feedings => renderFeedings(feedings));
  }

  function renderFeedings(feedings) {
    const container = document.getElementById("feedingContainer");
    container.innerHTML = "";
    feedings.forEach(feed => {
      const template = document.getElementById("feedingTemplate");
      const clone = template.content.cloneNode(true);
      clone.querySelector(".feeding-id").textContent = feed.id;
      clone.querySelector(".feeding-date").textContent = formatDate(feed.dateTime);
      clone.querySelector(".feeding-batch").textContent = feed.batch?.name || "-";
      clone.querySelector(".feeding-amount").textContent = feed.foodAmountGrams;
      clone.querySelector(".feeding-type").textContent = foodTypeLabel(feed.foodType);
      clone.querySelector(".feeding-user").textContent = feed.operator?.name || "-";
      if (isAdmin()) {
        clone.querySelector(".feeding-actions").classList.remove("d-none");
      }
      container.appendChild(clone);
    });
  }

  function foodTypeLabel(foodType) {
    switch (foodType) {
      case "DRY": return "Сухой";
      case "WET": return "Влажный";
      case "LIVE": return "Живой";
      case "OTHER": return "Другое";
      default: return foodType;
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
        feedingUserSelect.innerHTML = "<option value=''>-- Не выбрано --</option>";
        users.forEach(user => {
          const option = document.createElement("option");
          option.value = user.id;
          option.textContent = user.name;
          feedingUserSelect.appendChild(option);
        });
      });
  }

  window.openCreateFeedingModal = function () {
    feedingModalLabel.textContent = "Новое кормление";
    feedingIdInput.value = "";
    feedingDateInput.value = "";
    batchSelect.value = "";
    foodTypeSelect.value = "DRY";
    amountInput.value = "";
    feedingUserSelect.value = "";
    if (isOperator()) {
        feedingUserSelect.value = currentUser.id;
        feedingUserSelect.disabled = true;
    }
  };

  window.editFeeding = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".feeding-id").textContent;
    fetch(`/api/feedings/${id}`, {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(feed => {
        feedingModalLabel.textContent = "Редактировать кормление";
        feedingIdInput.value = feed.id;
        feedingDateInput.value = toLocalInputValue(feed.dateTime);
        batchSelect.value = feed.batch?.id || "";
        foodTypeSelect.value = feed.foodType;
        amountInput.value = feed.foodAmountGrams;
        feedingUserSelect.value = feed.operator?.id || "";
        new bootstrap.Modal(document.getElementById("feedingModal")).show();
      });
  };

  window.deleteFeeding = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".feeding-id").textContent;
    if (!confirm("Удалить запись о кормлении?")) return;
    fetch(`/api/feedings/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    }).then(() => fetchFeedings());
  };

  feedingForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = feedingIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `/api/feedings/${id}` : "/api/feedings";
    const body = {
      dateTime: new Date(feedingDateInput.value).toISOString(),
      batchId: batchSelect.value,
      foodType: foodTypeSelect.value,
      foodAmountGrams: amountInput.value,
      operatorId: feedingUserSelect.value || null
    };
    fetch(url, {
      method,
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(() => {
      bootstrap.Modal.getInstance(document.getElementById("feedingModal")).hide();
      fetchFeedings();
    });
  });
});

document.addEventListener("DOMContentLoaded", async () => {
  const batchForm = document.getElementById("batchForm");
  const batchModalLabel = document.getElementById("batchModalLabel");
  const batchIdInput = document.getElementById("batchId");
  const batchNameInput = document.getElementById("batchName");
  const fishTypeInput = document.getElementById("fishType");
  const arrivalDateInput = document.getElementById("arrivalDate");
  const initialCountInput = document.getElementById("initialCount");
  const avgWeightInput = document.getElementById("avgWeight");
  const currentCountInput = document.getElementById("currentCount");
  const statusSelect = document.getElementById("batchStatus");

  await initAuth();

  if (isAdmin()) {
    document.getElementById("createBatchBtn").classList.remove("d-none");
  }
  fetchBatches();

  function fetchBatches() {
    fetch("/api/fish-batches", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(batches => renderBatches(batches));
  }

  function renderBatches(batches) {
    const container = document.getElementById("batchContainer");
    container.innerHTML = "";
    batches.forEach(batch => {
      const template = document.getElementById("batchTemplate");
      const clone = template.content.cloneNode(true);
      clone.querySelector(".batch-id").textContent = batch.id;
      clone.querySelector(".batch-name").textContent = batch.name;
      clone.querySelector(".batch-type").textContent = batch.fishType;
      clone.querySelector(".batch-arrival").textContent = formatDate(batch.arrivalDate);
      clone.querySelector(".batch-initial").textContent = batch.initialCount;
      clone.querySelector(".batch-weight").textContent = batch.averageWeightGrams;
      clone.querySelector(".batch-current").textContent = batch.currentCount;
      clone.querySelector(".batch-status").textContent = batchStatusLabel(batch.status);
      if (isAdmin()) {
        clone.querySelector(".batch-actions").classList.remove("d-none");
      }
      container.appendChild(clone);
    });
  }

  function batchStatusLabel(status) {
    switch(status) {
      case "ACTIVE": return "Активна";
      case "HARVESTED": return "Собрана";
      case "CLOSED": return "Закрыта";
      default: return status;
    }
  }

  window.openCreateBatchModal = function () {
    batchModalLabel.textContent = "Новая партия";
    batchForm.reset();
    batchIdInput.value = "";
    statusSelect.value = "ACTIVE";
  };

  window.editBatch = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".batch-id").textContent;
    fetch(`/api/fish-batches/${id}`, {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(batch => {
        batchModalLabel.textContent = "Редактировать партию";
        batchIdInput.value = batch.id;
        batchNameInput.value = batch.name;
        fishTypeInput.value = batch.fishType;
        arrivalDateInput.value = toLocalInputValue(batch.arrivalDate);
        initialCountInput.value = batch.initialCount;
        avgWeightInput.value = batch.averageWeightGrams;
        currentCountInput.value = batch.currentCount;
        statusSelect.value = batch.status;
        new bootstrap.Modal(document.getElementById("batchModal")).show();
      });
  };

  window.deleteBatch = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".batch-id").textContent;
    if (!confirm("Удалить партию?")) {
      return;
    }
    fetch(`/api/fish-batches/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    }).then(res => {
      if (!res.ok) {
        alert("Не удалось выполнить удаление. Проверьте наличие связей с другими сущностями.");
      }
      fetchBatches();
    });
  };

  batchForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = batchIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `/api/fish-batches/${id}` : "/api/fish-batches";
    const body = {
      name: batchNameInput.value,
      fishType: fishTypeInput.value,
      arrivalDate: new Date(arrivalDateInput.value).toISOString(),
      initialCount: initialCountInput.value,
      averageWeightGrams: avgWeightInput.value,
      currentCount: currentCountInput.value,
      status: statusSelect.value,
    };
    fetch(url, {
      method,
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(() => {
      bootstrap.Modal.getInstance(document.getElementById("batchModal")).hide();
      fetchBatches();
    });
  });
});

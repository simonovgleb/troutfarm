document.addEventListener("DOMContentLoaded", async () => {
  const transferForm = document.getElementById("transferForm");
  const transferModalLabel = document.getElementById("transferModalLabel");
  const transferIdInput = document.getElementById("transferId");
  const transferDateInput = document.getElementById("transferDate");
  const fromTankSelect = document.getElementById("fromTank");
  const toTankSelect = document.getElementById("toTank");
  const batchSelect = document.getElementById("transferBatchSelect");
  const countInput = document.getElementById("transferCount");
  const userSelect = document.getElementById("transferUserSelect");

  await initAuth();

  fetchTransfers();
  loadTanks();
  loadBatches();
  loadOperators();

  function fetchTransfers() {
    fetch("/api/transfers", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(transfers => renderTransfers(transfers));
  }

  function renderTransfers(transfers) {
    const container = document.getElementById("transferContainer");
    container.innerHTML = "";
    transfers.forEach(tr => {
      const template = document.getElementById("transferTemplate");
      const clone = template.content.cloneNode(true);
      clone.querySelector(".transfer-id").textContent = tr.id;
      clone.querySelector(".transfer-date").textContent = formatDate(tr.dateTime);
      clone.querySelector(".transfer-batch").textContent = tr.batch?.name || "-";
      clone.querySelector(".transfer-from").textContent = tr.fromTank?.name || "-";
      clone.querySelector(".transfer-to").textContent = tr.toTank?.name || "-";
      clone.querySelector(".transfer-count").textContent = tr.count;
      clone.querySelector(".transfer-user").textContent = tr.operator?.name || "-";
      if (isAdmin()) {
        clone.querySelector(".transfer-actions").classList.remove("d-none");
      }
      container.appendChild(clone);
    });
  }

  function loadTanks() {
    fetch("/api/tanks", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(tanks => {
        fromTankSelect.innerHTML = toTankSelect.innerHTML = "<option value=''>-- Не выбрано --</option>";
        tanks.forEach(tank => {
          const option1 = document.createElement("option");
          option1.value = tank.id;
          option1.textContent = tank.name;
          fromTankSelect.appendChild(option1);

          const option2 = option1.cloneNode(true);
          toTankSelect.appendChild(option2);
        });
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

  function loadOperators() {
    fetch("/api/users?role=OPERATOR", {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
    .then(res => res.json())
    .then(users => {
      transferUserSelect.innerHTML = "<option value=''>-- Не выбрано --</option>";
      users.forEach(user => {
        const option = document.createElement("option");
        option.value = user.id;
        option.textContent = user.name;
        transferUserSelect.appendChild(option);
      });
    });
  }

  window.openCreateTransferModal = function () {
    transferModalLabel.textContent = "Новое перемещение";
    transferForm.reset();
    transferIdInput.value = "";
    if (isOperator()) {
      transferUserSelect.value = currentUser.id;
      transferUserSelect.disabled = true;
    }
  };

  window.editTransfer = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".transfer-id").textContent;
    fetch(`/api/transfers/${id}`, {
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    })
      .then(res => res.json())
      .then(tr => {
        transferModalLabel.textContent = "Редактировать перемещение";
        transferIdInput.value = tr.id;
        transferDateInput.value = toLocalInputValue(tr.dateTime);
        batchSelect.value = tr.batch?.id || "";
        fromTankSelect.value = tr.fromTank?.id || "";
        toTankSelect.value = tr.toTank?.id || "";
        countInput.value = tr.count;
        transferUserSelect.value = tr.operator?.id;
        new bootstrap.Modal(document.getElementById("transferModal")).show();
      });
  };

  window.deleteTransfer = function (btn) {
    const id = btn.closest(".list-group-item").querySelector(".transfer-id").textContent;
    if (!confirm("Удалить запись о перемещении?")) {
      return;
    }
    fetch(`/api/transfers/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + localStorage.getItem("token") }
    }).then(() => fetchTransfers());
  };

  transferForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const id = transferIdInput.value;
    const method = id ? "PUT" : "POST";
    const url = id ? `/api/transfers/${id}` : "/api/transfers";
    const body = {
      dateTime: new Date(transferDateInput.value).toISOString(),
      batchId: batchSelect.value,
      fromTankId: fromTankSelect.value,
      toTankId: toTankSelect.value,
      count: countInput.value,
      operatorId: transferUserSelect.value,
    };
    fetch(url, {
      method,
      headers: {
        "Authorization": "Bearer " + localStorage.getItem("token"),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    }).then(() => {
      bootstrap.Modal.getInstance(document.getElementById("transferModal")).hide();
      fetchTransfers();
    });
  });
});

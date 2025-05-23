document.addEventListener("DOMContentLoaded", function () {
  // Get modal elements
  const modal = document.getElementById("lessonCreateModal");
  const createBtn = document.querySelector(".create-btn-lesson");
  const closeBtn = modal.querySelector(".close-modal");
  const cancelBtn = modal.querySelector(".cancel-btn");
  const form = document.getElementById("createLessonForm");

  // Open modal
  if (createBtn) {
    createBtn.addEventListener("click", function () {
      modal.style.display = "block";
    });
  }

  // Close modal functions
  function closeModal() {
    modal.style.display = "none";
    form.reset();
  }

  // Close modal when clicking close button
  if (closeBtn) {
    closeBtn.addEventListener("click", closeModal);
  }

  // Close modal when clicking cancel button
  if (cancelBtn) {
    cancelBtn.addEventListener("click", closeModal);
  }

  // Close modal when clicking outside
  window.addEventListener("click", function (event) {
    if (event.target === modal) {
      closeModal();
    }
  });

  // Form submission handling
  if (form) {
    form.addEventListener("submit", function (e) {
      e.preventDefault();

      // Basic validation
      const duration = document.getElementById("duration").value;
      if (duration < 30 || duration % 30 !== 0) {
        alert(
          "Duration must be in multiples of 30 minutes and at least 30 minutes"
        );
        return;
      }

      // Submit the form
      this.submit();
    });
  }
});

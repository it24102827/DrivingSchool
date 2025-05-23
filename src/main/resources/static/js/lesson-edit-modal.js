
document.addEventListener("DOMContentLoaded", function () {


  const modal = document.getElementById("lessonEditModal");
  if (!modal) {
    console.error("Modal element not found!");
    return;
  }

  // Use event delegation for dynamic elements
  document.addEventListener("click", function (e) {
    if (e.target.classList.contains("edit-lesson-btn")) {
      console.log("Edit button clicked");

      // Get lesson data from data attributes
      const lessonData = {
        id: e.target.dataset.id,
        instructorId: e.target.dataset.instructorId,
        instructorName: e.target.dataset.instructorName,
        lessonName: e.target.dataset.lessonName,
        lessonType: e.target.dataset.lessonType,
        description: e.target.dataset.description,
        duration: e.target.dataset.duration,
      };

      console.log("Lesson data:", lessonData);

      // Populate form
      document.getElementById("editLessonId").value = lessonData.id;
      document.getElementById("editLessonInstructorId").value =
        lessonData.instructorId;
      document.getElementById("editLessonInstructorName").value =
        lessonData.instructorName;
      document.getElementById("editLessonName").value = lessonData.lessonName;
      document.getElementById("editLessonType").value = lessonData.lessonType;
      document.getElementById("editLessonDescription").value =
        lessonData.description;
      document.getElementById("editLessonDuration").value = lessonData.duration;

      // Show modal
      modal.style.display = "block";
      console.log("Modal should be visible now");
    }

    // Close modal handlers
    if (
      e.target.classList.contains("close-modal") ||
      e.target.classList.contains("cancel-btn") ||
      e.target === modal
    ) {
      modal.style.display = "none";
    }
  });
});

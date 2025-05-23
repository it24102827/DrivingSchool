// Handle menu navigation
document.addEventListener('DOMContentLoaded', function() {
    const menuItems = document.querySelectorAll('.menu-item');
    const sections = document.querySelectorAll('.dashboard-section');

    menuItems.forEach(item => {
        item.addEventListener('click', function() {
            // Remove active class from all menu items
            menuItems.forEach(mi => mi.classList.remove('active'));

            // Add active class to clicked menu item
            this.classList.add('active');

            // Get the section to show
            const sectionId = this.getAttribute('data-section');

            // Hide all sections
            sections.forEach(section => {
                section.classList.remove('active');
            });

            // Show the selected section
            document.getElementById(sectionId).classList.add('active');
        });
    });
});

// Handle admin create modal
document.addEventListener('DOMContentLoaded', function() {
    // Modal elements
    const adminModal = document.getElementById('adminCreateModal');
    const openModalBtn = document.querySelector('#admins .create-btn');
    const closeModalBtn = document.querySelector('.close-modal');
    const cancelBtn = document.querySelector('.cancel-btn');
    const adminForm = document.getElementById('createAdminForm');

    // Open modal when the create button is clicked
    if (openModalBtn) {
        openModalBtn.addEventListener('click', function(e) {
            e.stopPropagation(); 
            adminModal.style.display = 'block';
            document.body.style.overflow = 'hidden'; 
        });
    }

    // Close modal functions
    function closeModal() {
        adminModal.style.display = 'none';
        document.body.style.overflow = 'auto'; 
        if (adminForm) adminForm.reset(); 
        // Remove any error classes
        const errorFields = document.querySelectorAll('.has-error');
        errorFields.forEach(field => field.classList.remove('has-error'));
    }

    // Close modal when X is clicked
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', closeModal);
    }

    // Close modal when Cancel button is clicked
    if (cancelBtn) {
        cancelBtn.addEventListener('click', closeModal);
    }

    // Close modal when clicking outside of it
    window.addEventListener('click', function(event) {
        if (event.target === adminModal) {
            closeModal();
        }
    });

    // Close alert messages
    const closeAlertButtons = document.querySelectorAll('.close-alert');
    closeAlertButtons.forEach(button => {
        button.addEventListener('click', function() {
            this.parentElement.style.display = 'none';
        });
    });

    // Form validation
    if (adminForm) {
        adminForm.addEventListener('submit', function(event) {
            let isValid = true;

            // Get form values
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            // Check if passwords match
            if (password !== confirmPassword) {
                isValid = false;
                event.preventDefault();

                // Create error message if it doesn't exist
                const passwordGroup = document.getElementById('confirmPassword').parentElement;
                let errorMsg = passwordGroup.querySelector('.error-message');

                if (!errorMsg) {
                    errorMsg = document.createElement('div');
                    errorMsg.className = 'error-message';
                    errorMsg.textContent = 'Passwords do not match';
                    passwordGroup.appendChild(errorMsg);
                }

                passwordGroup.classList.add('has-error');
            }

            // Add more validations as needed
            return isValid;
        });
    }
});

// Handle admin edit modal
document.addEventListener('DOMContentLoaded', function() {
    // Get edit modal elements
    const editModal = document.getElementById('adminEditModal');
    const editForm = document.getElementById('editAdminForm');
    const closeEditModalBtn = editModal ? editModal.querySelector('.close-modal') : null;
    const cancelEditBtn = editModal ? editModal.querySelector('.cancel-btn') : null;

    // Get edit modal form fields
    const editIdInput = document.getElementById('editId');
    const editNameInput = document.getElementById('editName');
    const editEmailInput = document.getElementById('editEmail');
    const editPasswordInput = document.getElementById('editPassword');
    const editConfirmPasswordInput = document.getElementById('editConfirmPassword');

    // Add event listeners to all edit buttons
    const editButtons = document.querySelectorAll('.edit-admin-btn');

    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Get admin data from data attributes
            const id = this.getAttribute('data-id');
            const name = this.getAttribute('data-name');
            const email = this.getAttribute('data-email');

            // Populate the form
            editIdInput.value = id;
            editNameInput.value = name;
            editEmailInput.value = email;
            editPasswordInput.value = '';
            editConfirmPasswordInput.value = '';

            // Clear any previous error messages
            const errorMessages = editModal.querySelectorAll('.error-message');
            errorMessages.forEach(msg => msg.style.display = 'none');

            // Show the modal
            editModal.style.display = 'block';
            document.body.style.overflow = 'hidden'; 
        });
    });

    // Function to close the edit modal
    function closeEditModal() {
        if (editModal) {
            editModal.style.display = 'none';
            document.body.style.overflow = 'auto';
            if (editForm) editForm.reset();
        }
    }

    // Close modal when X button is clicked
    if (closeEditModalBtn) {
        closeEditModalBtn.addEventListener('click', closeEditModal);
    }

    // Close modal when Cancel button is clicked
    if (cancelEditBtn) {
        cancelEditBtn.addEventListener('click', closeEditModal);
    }

    // Close modal when clicking outside of it
    window.addEventListener('click', function(event) {
        if (editModal && event.target === editModal) {
            closeEditModal();
        }
    });

    // Validate form before submission
    if (editForm) {
        editForm.addEventListener('submit', function(event) {
            // Only validate passwords if a new password is being set
            if (editPasswordInput && editPasswordInput.value.length > 0) {
                if (editPasswordInput.value !== editConfirmPasswordInput.value) {
                    event.preventDefault();

                    const errorMsg = editForm.querySelector('.error-message');
                    if (errorMsg) errorMsg.style.display = 'block';

                    editConfirmPasswordInput.parentElement.classList.add('has-error');
                    return false;
                }
            }

            return true;
        });
    }
});


document.addEventListener('DOMContentLoaded', function() {


    // ---- Navigation Toggle for Mobile ----
    const navToggle = document.getElementById('navToggle');
    const navLinks = document.getElementById('navLinks');

    if (navToggle) {
        navToggle.addEventListener('click', function() {
            navLinks.classList.toggle('active');
        });
    }

    // Close navigation when clicking outside
    document.addEventListener('click', function(e) {
        if (navLinks && navLinks.classList.contains('active') &&
            !navLinks.contains(e.target) &&
            !navToggle.contains(e.target)) {
            navLinks.classList.remove('active');
        }
    });

    // ---- User profile dropdown ----
    const userMenu = document.getElementById('userMenu');
    const userButton = userMenu ? userMenu.querySelector('.user-button') : null;

    if (userButton) {
        userButton.addEventListener('click', function(e) {
            e.preventDefault();
            userMenu.classList.toggle('active');
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', function(e) {
            if (userMenu && userMenu.classList.contains('active') &&
                !userMenu.contains(e.target)) {
                userMenu.classList.remove('active');
            }
        });
    }

    // ---- Smooth scrolling for anchor links ----
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            const targetId = this.getAttribute('href');

            if (targetId !== '#') {
                e.preventDefault();

                const targetElement = document.querySelector(targetId);

                if (targetElement) {
                    window.scrollTo({
                        top: targetElement.offsetTop - 80,
                        behavior: 'smooth'
                    });

                    // Close mobile menu if open
                    if (navLinks && navLinks.classList.contains('active')) {
                        navLinks.classList.remove('active');
                    }
                }
            }
        });
    });

    // ---- FAQ Functionality ----
    const faqQuestions = document.querySelectorAll('.faq-question');

    faqQuestions.forEach(question => {
        question.addEventListener('click', function() {
            // Toggle active class on the parent faq-item
            const faqItem = this.parentElement;
            faqItem.classList.toggle('active');

            // Get the toggle icon element
            const toggleIcon = this.querySelector('.faq-toggle i');

            // Toggle the icon between plus and minus
            if (faqItem.classList.contains('active')) {
                toggleIcon.classList.remove('fa-plus');
                toggleIcon.classList.add('fa-minus');
            } else {
                toggleIcon.classList.remove('fa-minus');
                toggleIcon.classList.add('fa-plus');
            }

            // Get the answer element
            const answer = this.nextElementSibling;

            // Toggle the visibility of the answer
            if (faqItem.classList.contains('active')) {
                answer.style.maxHeight = answer.scrollHeight + 'px';
            } else {
                answer.style.maxHeight = '0';
            }
        });
    });

    // ---- Location tabs in the map section ----
    const locationTabs = document.querySelectorAll('.location-tab');

    locationTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // Remove active class from all tabs
            locationTabs.forEach(t => t.classList.remove('active'));

            // Add active class to the clicked tab
            this.classList.add('active');

            // Hide all location details
            const locationDetails = document.querySelectorAll('.location-details');
            locationDetails.forEach(detail => detail.classList.remove('active'));

            // Show the corresponding location detail
            const locationId = this.getAttribute('data-location');
            document.getElementById(locationId + '-location').classList.add('active');
        });
    });

    
});
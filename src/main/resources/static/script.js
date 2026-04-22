// NEXTHIRE – script.js
// Simple JavaScript for form interactions and result page animations

// Show loading overlay when form is submitted
function setupFormLoading() {
    var form = document.getElementById("analyzeForm");
    var loadingOverlay = document.getElementById("loadingOverlay");
    var submitBtn = document.getElementById("submitBtn");

    if (form == null) {
        return;
    }

    form.addEventListener("submit", function(event) {
        // Simple validation first
        var cvText = document.getElementById("cvText").value;
        var githubUrl = document.getElementById("githubUrl").value;
        var targetRole = document.getElementById("targetRole").value;

        if (cvText.trim() == "") {
            alert("Please paste your CV text before submitting.");
            event.preventDefault();
            return;
        }

        if (githubUrl.trim() == "") {
            alert("Please enter your GitHub profile URL.");
            event.preventDefault();
            return;
        }

        if (targetRole == "") {
            alert("Please select a target role.");
            event.preventDefault();
            return;
        }

        // Show loading overlay
        if (loadingOverlay != null) {
            loadingOverlay.style.display = "flex";
        }

        // Disable submit button to prevent double submission
        if (submitBtn != null) {
            submitBtn.disabled = true;
        }
    });
}

// Character counter for CV textarea
function setupCharacterCounter() {
    var textarea = document.getElementById("cvText");

    if (textarea == null) {
        return;
    }

    // Create counter element
    var counter = document.createElement("span");
    counter.className = "field-hint";
    counter.style.textAlign = "right";
    counter.style.display = "block";
    counter.textContent = "0 characters";

    // Insert counter after the textarea's next sibling (the hint span)
    var parent = textarea.parentNode;
    var nextElement = textarea.nextSibling;
    if (nextElement != null && nextElement.nextSibling != null) {
        parent.insertBefore(counter, nextElement.nextSibling);
    } else {
        parent.appendChild(counter);
    }

    textarea.addEventListener("input", function() {
        var count = textarea.value.length;
        counter.textContent = count + " characters";

        // Color code the counter based on length
        if (count < 100) {
            counter.style.color = "#ff6666";
        } else if (count < 300) {
            counter.style.color = "#ffcc00";
        } else {
            counter.style.color = "#4caf50";
        }
    });
}

// Animate finding cards on result page
function highlightFindings() {
    var findingCards = document.querySelectorAll(".finding-card");

    for (var i = 0; i < findingCards.length; i++) {
        var card = findingCards[i];
        card.style.opacity = "0";
        card.style.transform = "translateY(10px)";
        card.style.transition = "opacity 0.3s ease, transform 0.3s ease";

        // Use a closure to capture the correct card and index
        (function(currentCard, delay) {
            setTimeout(function() {
                currentCard.style.opacity = "1";
                currentCard.style.transform = "translateY(0)";
            }, delay);
        })(card, i * 80);
    }
}

// Animate recommendation cards on result page
function animateRecommendations() {
    var recCards = document.querySelectorAll(".rec-card");

    for (var i = 0; i < recCards.length; i++) {
        var card = recCards[i];
        card.style.opacity = "0";
        card.style.transform = "translateX(-10px)";
        card.style.transition = "opacity 0.3s ease, transform 0.3s ease";

        (function(currentCard, delay) {
            setTimeout(function() {
                currentCard.style.opacity = "1";
                currentCard.style.transform = "translateX(0)";
            }, delay);
        })(card, 200 + i * 100);
    }
}

// Animate stats numbers counting up on result page
function animateStats() {
    var statNums = document.querySelectorAll(".stat-num");

    for (var i = 0; i < statNums.length; i++) {
        var element = statNums[i];
        var targetValue = parseInt(element.textContent);

        if (isNaN(targetValue) || targetValue == 0) {
            continue;
        }

        var currentValue = 0;
        var increment = Math.max(1, Math.floor(targetValue / 20));

        // Use a closure to capture the correct element and target
        (function(el, target) {
            var current = 0;
            var inc = Math.max(1, Math.floor(target / 20));
            var timer = setInterval(function() {
                current = current + inc;
                if (current >= target) {
                    el.textContent = target;
                    clearInterval(timer);
                } else {
                    el.textContent = current;
                }
            }, 40);
        })(element, targetValue);
    }
}

// Validate GitHub URL format when user leaves the field
function validateGithubUrl() {
    var githubInput = document.getElementById("githubUrl");

    if (githubInput == null) {
        return;
    }

    githubInput.addEventListener("blur", function() {
        var url = githubInput.value.trim();

        if (url != "" && !url.includes("github.com")) {
            githubInput.style.borderColor = "#ff4444";
            githubInput.setAttribute("title", "Please enter a valid GitHub URL like: https://github.com/yourusername");
        } else {
            githubInput.style.borderColor = "";
            githubInput.setAttribute("title", "");
        }
    });
}

// Highlight skill tags with a fade-in animation
function animateSkillTags() {
    var skillTags = document.querySelectorAll(".skill-tag");

    for (var i = 0; i < skillTags.length; i++) {
        var tag = skillTags[i];
        tag.style.opacity = "0";
        tag.style.transform = "scale(0.8)";
        tag.style.transition = "opacity 0.2s ease, transform 0.2s ease";

        (function(currentTag, delay) {
            setTimeout(function() {
                currentTag.style.opacity = "1";
                currentTag.style.transform = "scale(1)";
            }, delay);
        })(tag, 400 + i * 40);
    }
}

// Run everything when the page loads
window.onload = function() {
    setupFormLoading();
    setupCharacterCounter();
    validateGithubUrl();

    // These only run on the result page
    highlightFindings();
    animateRecommendations();
    animateStats();
    animateSkillTags();
};

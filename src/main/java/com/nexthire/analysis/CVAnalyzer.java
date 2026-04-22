package com.nexthire.analysis;

import com.nexthire.model.CVData;
import com.nexthire.model.Finding;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CVAnalyzer {

    public List<Finding> analyze(CVData cvData) {
        List<Finding> findings = new ArrayList<>();

        String rawText = cvData.getRawText().toLowerCase();

        // Check for missing skills section
        if (cvData.getSkills() == null || cvData.getSkills().isEmpty()) {
            Finding finding = new Finding();
            finding.setType("Missing Section");
            finding.setDescription("No skills were found in your CV. Please add a dedicated skills section.");
            finding.setSource("CV Analyzer");
            findings.add(finding);
        }

        // Check for missing projects section
        if (!rawText.contains("project") && !rawText.contains("built") && !rawText.contains("developed")) {
            Finding finding = new Finding();
            finding.setType("Missing Section");
            finding.setDescription("No projects were mentioned in your CV. Adding projects makes your CV stronger.");
            finding.setSource("CV Analyzer");
            findings.add(finding);
        }

        // Check for missing certifications
        if (!rawText.contains("certification") && !rawText.contains("certified") && !rawText.contains("certificate")) {
            Finding finding = new Finding();
            finding.setType("Missing Section");
            finding.setDescription("No certifications found in your CV. Consider adding relevant certifications.");
            finding.setSource("CV Analyzer");
            findings.add(finding);
        }

        // Check if CV is too short
        if (rawText.length() < 200) {
            Finding finding = new Finding();
            finding.setType("Weak CV");
            finding.setDescription("Your CV text is very short. A detailed CV helps recruiters understand your background better.");
            finding.setSource("CV Analyzer");
            findings.add(finding);
        }

        // Check for contact information
        if (!rawText.contains("@") && !rawText.contains("email")) {
            Finding finding = new Finding();
            finding.setType("Missing Contact Info");
            finding.setDescription("No email address found in CV. Make sure to include contact information.");
            finding.setSource("CV Analyzer");
            findings.add(finding);
        }

        // Check for education
        if (!rawText.contains("education") && !rawText.contains("university") && !rawText.contains("college") && !rawText.contains("degree")) {
            Finding finding = new Finding();
            finding.setType("Missing Education");
            finding.setDescription("No education details found. Add your educational background to the CV.");
            finding.setSource("CV Analyzer");
            findings.add(finding);
        }

        return findings;
    }
}

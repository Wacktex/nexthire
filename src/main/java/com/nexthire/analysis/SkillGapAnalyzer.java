package com.nexthire.analysis;

import com.nexthire.model.CVData;
import com.nexthire.model.Finding;
import com.nexthire.model.TargetRole;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SkillGapAnalyzer {

    public List<Finding> analyze(CVData cvData, TargetRole targetRole) {
        List<Finding> findings = new ArrayList<>();

        if (targetRole == null) {
            return findings;
        }

        List<String> requiredSkills = targetRole.getRequiredSkills();
        String rawText = cvData.getRawText().toLowerCase();

        // Check each required skill against the CV text
        for (String requiredSkill : requiredSkills) {
            boolean skillFound = false;

            // Check in raw CV text
            if (rawText.contains(requiredSkill.toLowerCase())) {
                skillFound = true;
            }

            // Also check in the skills list if available
            if (!skillFound && cvData.getSkills() != null) {
                for (String userSkill : cvData.getSkills()) {
                    if (userSkill.toLowerCase().contains(requiredSkill.toLowerCase())) {
                        skillFound = true;
                        break;
                    }
                }
            }

            // If skill is missing, add a finding
            if (!skillFound) {
                Finding finding = new Finding();
                finding.setType("Missing Skill");
                finding.setDescription("You are missing the skill: " + requiredSkill + ". This skill is required for " + targetRole.getRoleName() + ".");
                finding.setSource("Skill Gap Analyzer");
                findings.add(finding);
            }
        }

        return findings;
    }

    public List<String> getMissingSkills(CVData cvData, TargetRole targetRole) {
        List<String> missingSkills = new ArrayList<>();

        if (targetRole == null) {
            return missingSkills;
        }

        List<String> requiredSkills = targetRole.getRequiredSkills();
        String rawText = cvData.getRawText().toLowerCase();

        for (String requiredSkill : requiredSkills) {
            boolean skillFound = false;

            if (rawText.contains(requiredSkill.toLowerCase())) {
                skillFound = true;
            }

            if (!skillFound && cvData.getSkills() != null) {
                for (String userSkill : cvData.getSkills()) {
                    if (userSkill.toLowerCase().contains(requiredSkill.toLowerCase())) {
                        skillFound = true;
                        break;
                    }
                }
            }

            if (!skillFound) {
                missingSkills.add(requiredSkill);
            }
        }

        return missingSkills;
    }
}

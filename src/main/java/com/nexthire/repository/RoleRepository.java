package com.nexthire.repository;

import com.nexthire.model.TargetRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepository {

    private List<TargetRole> roles;

    public RoleRepository() {
        roles = new ArrayList<>();
        setupRoles();
    }

    private void setupRoles() {

        // DevOps Engineer
        TargetRole devops = new TargetRole();
        devops.setRoleName("DevOps Engineer");
        List<String> devopsSkills = new ArrayList<>();
        devopsSkills.add("Docker");
        devopsSkills.add("Linux");
        devopsSkills.add("CI/CD");
        devopsSkills.add("Git");
        devopsSkills.add("Kubernetes");
        devops.setRequiredSkills(devopsSkills);
        List<String> devopsProjects = new ArrayList<>();
        devopsProjects.add("pipeline automation");
        devopsProjects.add("containerization");
        devops.setRequiredProjectTypes(devopsProjects);
        roles.add(devops);

        // Backend Developer
        TargetRole backend = new TargetRole();
        backend.setRoleName("Backend Developer");
        List<String> backendSkills = new ArrayList<>();
        backendSkills.add("Java");
        backendSkills.add("Spring Boot");
        backendSkills.add("REST API");
        backendSkills.add("Database");
        backendSkills.add("SQL");
        backend.setRequiredSkills(backendSkills);
        List<String> backendProjects = new ArrayList<>();
        backendProjects.add("REST API project");
        backendProjects.add("database project");
        backend.setRequiredProjectTypes(backendProjects);
        roles.add(backend);

        // Frontend Developer
        TargetRole frontend = new TargetRole();
        frontend.setRoleName("Frontend Developer");
        List<String> frontendSkills = new ArrayList<>();
        frontendSkills.add("HTML");
        frontendSkills.add("CSS");
        frontendSkills.add("JavaScript");
        frontendSkills.add("React");
        frontendSkills.add("Responsive Design");
        frontend.setRequiredSkills(frontendSkills);
        List<String> frontendProjects = new ArrayList<>();
        frontendProjects.add("web application");
        frontendProjects.add("UI project");
        frontend.setRequiredProjectTypes(frontendProjects);
        roles.add(frontend);

        // Data Scientist
        TargetRole datascience = new TargetRole();
        datascience.setRoleName("Data Scientist");
        List<String> dsSkills = new ArrayList<>();
        dsSkills.add("Python");
        dsSkills.add("Machine Learning");
        dsSkills.add("Pandas");
        dsSkills.add("NumPy");
        dsSkills.add("Data Analysis");
        datascience.setRequiredSkills(dsSkills);
        List<String> dsProjects = new ArrayList<>();
        dsProjects.add("machine learning project");
        dsProjects.add("data analysis");
        datascience.setRequiredProjectTypes(dsProjects);
        roles.add(datascience);

        // Full Stack Developer
        TargetRole fullstack = new TargetRole();
        fullstack.setRoleName("Full Stack Developer");
        List<String> fsSkills = new ArrayList<>();
        fsSkills.add("Java");
        fsSkills.add("JavaScript");
        fsSkills.add("HTML");
        fsSkills.add("CSS");
        fsSkills.add("Database");
        fsSkills.add("REST API");
        fullstack.setRequiredSkills(fsSkills);
        List<String> fsProjects = new ArrayList<>();
        fsProjects.add("full stack web application");
        fsProjects.add("end-to-end project");
        fullstack.setRequiredProjectTypes(fsProjects);
        roles.add(fullstack);
    }

    public List<TargetRole> getAllRoles() {
        return roles;
    }

    public TargetRole getRoleByName(String name) {
        for (TargetRole role : roles) {
            if (role.getRoleName().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }

    public List<String> getAllRoleNames() {
        List<String> names = new ArrayList<>();
        for (TargetRole role : roles) {
            names.add(role.getRoleName());
        }
        return names;
    }
}

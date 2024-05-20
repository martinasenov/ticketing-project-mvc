package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    ProjectDTO getByProjectCode(String code);
    List<ProjectDTO>listAllProjects();
    void save(ProjectDTO dto);
    void update(ProjectDTO dto);
    void delete(String code);
    void complete(String projectCode);

}

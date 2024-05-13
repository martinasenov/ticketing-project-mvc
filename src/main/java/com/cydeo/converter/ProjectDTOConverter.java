package com.cydeo.converter;

import com.cydeo.dto.ProjectDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOConverter implements Converter<String, ProjectDTO> {
    @Override
    public ProjectDTO convert(String source) {
        return null;
    }

/*
    ProjectService projectService;

    public ProjectDTOConverter(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public ProjectDTO convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }

        return projectService.findById(source);

    }*/

}
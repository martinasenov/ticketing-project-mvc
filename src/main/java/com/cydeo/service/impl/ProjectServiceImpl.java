package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, @Lazy UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project=projectRepository.findByProjectCode(code);
        return projectMapper.convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> listAllProjects() {

        return projectRepository.findAll(Sort.by("projectCode")).stream()
                .map(projectMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {

        dto.setProjectStatus(Status.OPEN);
        projectRepository.save(projectMapper.convertToEntity(dto));

    }

    @Override
    public void update(ProjectDTO dto) {

        Project pr1= projectRepository.findByProjectCode(dto.getProjectCode());
        Project convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(pr1.getId());
        convertedProject.setProjectStatus(pr1.getProjectStatus());

        projectRepository.save(convertedProject);

    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode()+"-"+project.getId());
        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDTO(project));
    }

    @Override
    public void complete(String projectCode) {
        Project project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskService.completeByProject(projectMapper.convertToDTO(project));
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        String username= SecurityContextHolder.getContext().getAuthentication().getName(); // retrieves the user info whoever is logged in

        UserDTO currentUserDto=userService.findByUserName(username);
        User user=userMapper.convertToEntity(currentUserDto);

        List<Project>list=projectRepository.findAllByAssignedManager(user);
        //give me all projects assigned to manager login in the system

        return list.stream().
                map(project -> {

                    ProjectDTO obj=projectMapper.convertToDTO(project);

                    obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
                    obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));


                    return obj;

                        }


                ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllNonCompletedByAssignedManager(UserDTO assignedManager) {
        List<Project>projects=projectRepository.findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE,userMapper.convertToEntity(assignedManager));

        return projects.stream()
                .map(projectMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}

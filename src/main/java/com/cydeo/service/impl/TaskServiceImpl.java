package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.CrudService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends AbstractMapService<TaskDTO,Long> implements TaskService {


    @Override
    public TaskDTO save(TaskDTO task) {

        if (task.getTaskStatus()==null)
            task.setTaskStatus(Status.OPEN);

        if (task.getAssignedDate()==null)
            task.setAssignedDate(LocalDate.now());

        if (task.getId()==null)
            task.setId(UUID.randomUUID().getMostSignificantBits());

        return super.save(task.getId(),task);
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void update(TaskDTO task) {

          TaskDTO foundTask=findById(task.getId());

          task.setTaskStatus(foundTask.getTaskStatus());
          task.setAssignedDate(foundTask.getAssignedDate());


        if (task.getId()==null)
            task.setId(UUID.randomUUID().getMostSignificantBits());

       super.update(task.getId(),task);
    }

    @Override
    public List<TaskDTO> findTasksByManager(UserDTO manager) {
        return findAll().stream()
                .filter(task->task.getProject().getAssignedManager().equals(manager))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatusIsNot(Status status) {
        return findAll().stream()
                .filter(task-> !task.getTaskStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllTasksByStatus(Status status) {
        return findAll().stream()
                .filter(task-> task.getTaskStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO taskDto) {
        TaskDTO task = findById(taskDto.getId()); // Retrieve the actual task entity
        task.setTaskStatus(taskDto.getTaskStatus()); // Update the status
        update(task); // Persist changes
    }
}

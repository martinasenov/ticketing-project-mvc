package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    public TaskController(ProjectService projectService, UserService userService,TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService=taskService;
    }

    @GetMapping("/create")
    public String createTask(Model model){

       model.addAttribute("projects", projectService.findAll());
       model.addAttribute("employees",userService.findEmployees());
       model.addAttribute("tasks",taskService.findAll());
       model.addAttribute("task",new TaskDTO());

        return "/task/create";
    }


    @PostMapping("/create")
    public String insertTask(@ModelAttribute TaskDTO task){

        taskService.save(task);

        return "redirect:/task/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){


        taskService.deleteById(id);

        return "redirect:/task/create";
    }


    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable Long taskId,Model model){

        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());
        model.addAttribute("tasks",taskService.findAll());
        return "/task/update";
    }



 /*  @PostMapping("/update/{taskId}")
   public String updateTask(@PathVariable("taskId") Long taskId, TaskDTO task) {

       task.setId(taskId);
       taskService.update(task);

       return "redirect:/task/create";
   }*/

    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task){

    /*
       when we pass {id} or whatever we name it to our url, it works just fine without using @Pathvariable
       annotation as long as the object (TaskDTO) field has the same variable name. In this case it's "Long id".
    */
        taskService.update(task);

        return "redirect:/task/create";
    }










}

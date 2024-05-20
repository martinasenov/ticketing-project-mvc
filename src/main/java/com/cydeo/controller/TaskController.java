package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

       model.addAttribute("projects", projectService.listAllProjects());
       model.addAttribute("employees",userService.listAllByRole("employee"));
       model.addAttribute("tasks",taskService.listAllTasks());
       model.addAttribute("task",new TaskDTO());

        return "/task/create";
    }



    @PostMapping("/create")
    public String insertTask(@ModelAttribute TaskDTO task, BindingResult bindingResult,Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("employees",userService.listAllByRole("employee"));
            model.addAttribute("tasks",taskService.listAllTasks());

            return "/task/create";
        }

        taskService.save(task);

        return "redirect:/task/create";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id){


        taskService.delete(id);

        return "redirect:/task/create";
    }


    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable Long taskId,Model model){

        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("employees",userService.listAllByRole("employee"));
        model.addAttribute("tasks",taskService.listAllTasks());
        return "/task/update";
    }


    @PostMapping("/update/{id}")
    public String updateTask(@ModelAttribute("task") TaskDTO task, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("employees", userService.listAllByRole("employee"));
            model.addAttribute("tasks", taskService.listAllTasks());

            return "/task/update";
        }

        taskService.update(task);

        return "redirect:/task/create";

    }

    /*@PostMapping("/update/{id}")
    public String updateTask(TaskDTO task){

       *//*when we pass {id} or whatever we name it to our url, it works just fine without using @Pathvariable
       annotation as long as the object (TaskDTO) field has the same variable name. In this case it's "Long id".*//*

        taskService.update(task);

        return "redirect:/task/create";
    }*/

 /*


    @GetMapping("/employee/pending-tasks")
    public String employeePendingTasks(Model model){

        model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETE));

        return "task/pending-tasks";
    }

    @GetMapping("/employee/archive")
    public String employeeArchivedTasks(Model model){

        model.addAttribute("tasks", taskService.findAllTasksByStatus(Status.COMPLETE));

        return "task/archive";
    }


   @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable Long id,Model model){
        model.addAttribute("task",taskService.findById(id));
  model.addAttribute("projects",projectService.findAll());
        model.addAttribute("employees",userService.findEmployees());

        model.addAttribute("statuses",Status.values());
        model.addAttribute("tasks",taskService.findAllTasksByStatusIsNot(Status.COMPLETE));


        return "/task/status-update";
   }


 @PostMapping("employee/update/{id}")
    public String employeeUpdateTask(TaskDTO task){

        taskService.updateStatus(task);

        return "redirect:/task/employee/pending-tasks";
 }

*/



}

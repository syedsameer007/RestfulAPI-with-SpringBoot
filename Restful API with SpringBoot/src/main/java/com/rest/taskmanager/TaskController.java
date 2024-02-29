package com.rest.taskmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
		
		@Autowired
		private TaskRepository taskRepository;
		
		@GetMapping
		public List<Task> getAllTasks(){
			return taskRepository.findAll();
		}
		@PostMapping
		public Task createTask(@RequestBody Task task) {
			return taskRepository.save(task);
		}
		@GetMapping("/{id}")
		public Task getTaskById(@PathVariable Long id) {
			return taskRepository.findById(id).orElseThrow( () -> new RuntimeException("Task not found"));
		}
		@PutMapping("/{id}")
		public Task updateTask(@PathVariable Long id, @RequestBody Task updateTask ) {
	        return taskRepository.findById(id)
	                .map(task -> {
	                    task.setTitle(updateTask.getTitle());
	                    task.setDescription(updateTask.getDescription());
	                    task.setCompleted(updateTask.isCompleted());
	                    return taskRepository.save(task);
	                })
	                .orElseThrow(() -> new RuntimeException("Task not found"));
		}
		@DeleteMapping("/{id}")
		public void deleteTask(@PathVariable Long id) {
			taskRepository.deleteById(id);
		}
}

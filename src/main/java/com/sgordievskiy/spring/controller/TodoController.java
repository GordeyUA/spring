package com.sgordievskiy.spring.controller;

import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.service.TaskHistoryService;
import com.sgordievskiy.spring.service.TodoService;
import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {

  private final TodoService todoService;
  private final TaskHistoryService taskHistoryService;

  @GetMapping
  public List<TodoResponseDto> getAll() {
    return todoService.getAll();
  }

  @PostMapping
  public TodoResponseDto create(@Valid @RequestBody TodoCreateDto todo) {
    return todoService.create(todo);
  }

  @PutMapping("/{id}")
  public TodoResponseDto update(@PathVariable Long id, @Valid @RequestBody TodoUpdateDto todo) {
    todo.setId(id);
    return todoService.update(todo);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    todoService.delete(id);
  }

  @GetMapping("/{id}/history")
  public List<TaskHistoryResponseDto> getHistory(@PathVariable Long id) {
    return taskHistoryService.getAllByTaskId(id);
  }
}

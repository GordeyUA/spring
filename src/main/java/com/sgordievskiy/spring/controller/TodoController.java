package com.sgordievskiy.spring.controller;

import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.service.TaskHistoryService;
import com.sgordievskiy.spring.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
  public TodoResponseDto create(@RequestBody TodoCreateDto todo) {
    return todoService.create(todo);
  }

  @PutMapping("/{id}")
  public TodoResponseDto update(@PathVariable Long id, @RequestBody TodoUpdateDto todo) {
    todo.setId(id);
    return todoService.update(todo);
  }

  @GetMapping("/{id}/history")
  public List<TaskHistoryResponseDto> getHistory(@PathVariable Long id) {
    return taskHistoryService.getAllByTaskId(id);
  }
}

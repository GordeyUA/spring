package com.sgordievskiy.spring.service;

import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import java.util.List;
import java.util.NoSuchElementException;
import com.sgordievskiy.spring.mapper.TaskHistoryMapper;
import com.sgordievskiy.spring.model.Todo;
import com.sgordievskiy.spring.repository.TaskHistoryRepository;
import com.sgordievskiy.spring.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskHistoryServiceImpl implements TaskHistoryService {

  private final TodoRepository todoRepository;
  private final TaskHistoryRepository taskHistoryRepository;
  private final TaskHistoryMapper taskHistoryMapper;

  @Override
  public List<TaskHistoryResponseDto> getAllByTaskId(Long taskId) {
    Todo todo = todoRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found."));
    return taskHistoryRepository.findAllByTodo(todo).stream()
      .map(taskHistoryMapper::toDto)
      .toList();
  }
}

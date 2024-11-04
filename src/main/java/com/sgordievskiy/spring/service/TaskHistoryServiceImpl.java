package com.sgordievskiy.spring.service;

import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import java.util.List;
import java.util.Optional;

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
    Optional<Todo> todo = todoRepository.findById(taskId);
    return taskHistoryRepository.findAllByTodo(todo.orElseThrow()).stream()
      .map(taskHistoryMapper::toDto)
      .toList();
  }
}

package com.sgordievskiy.spring.service;

import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.mapper.TodoMapper;
import com.sgordievskiy.spring.model.TaskHistory;
import com.sgordievskiy.spring.model.Todo;
import com.sgordievskiy.spring.repository.TaskHistoryRepository;
import com.sgordievskiy.spring.repository.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;
  private final TaskHistoryRepository taskHistoryRepository;
  private final TodoMapper todoMapper;

  @Override
  public Todo findById(Long id) {
    return todoRepository.findById(id).orElseThrow();
  }

  @Override
  public List<TodoResponseDto> getAll() {
    return todoRepository.findAll().stream()
      .map(todoMapper::toDto)
      .toList();
  }

  @Override
  public TodoResponseDto create(TodoCreateDto todo) {
    return todoMapper.toDto(todoRepository.save(todoMapper.toEntity(todo)));
  }

  @Override
  @Transactional
  public TodoResponseDto update(TodoUpdateDto todo) {
    Todo todoOld = todoRepository.findById(todo.getId()).orElseThrow();
    String oldTodo = todoOld.toString();
    Todo todoNew = todoRepository.save(todoMapper.toEntity(todo));
    String newTodo = todoNew.toString();
    TaskHistory taskHistory = new TaskHistory();
    taskHistory.setTodo(todoNew);
    taskHistory.setOldState(oldTodo);
    taskHistory.setNewState(newTodo);
    taskHistory.setChangeDate(LocalDateTime.now());
    taskHistoryRepository.save(taskHistory);
    return todoMapper.toDto(todoNew);
  }
}

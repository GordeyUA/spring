package com.sgordievskiy.spring.service;

import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.model.Todo;
import java.util.List;

public interface TodoService {

  Todo findById(Long id);

  List<TodoResponseDto> getAll();

  TodoResponseDto create(TodoCreateDto todo);

  TodoResponseDto update(TodoUpdateDto todo);

  void delete(Long id);
}

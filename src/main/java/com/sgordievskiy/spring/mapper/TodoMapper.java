package com.sgordievskiy.spring.mapper;

import com.sgordievskiy.spring.config.MapperConfig;
import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.model.Todo;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TodoMapper {

  TodoResponseDto toDto(Todo todo);

  Todo toEntity(TodoCreateDto dto);

  Todo toEntity(TodoUpdateDto dto);
}

package com.sgordievskiy.spring.mapper;

import com.sgordievskiy.spring.config.MapperConfig;
import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import com.sgordievskiy.spring.model.TaskHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface TaskHistoryMapper {

  @Mapping(source = "todo.id", target = "todoId")
  TaskHistoryResponseDto toDto(TaskHistory taskHistory);
}

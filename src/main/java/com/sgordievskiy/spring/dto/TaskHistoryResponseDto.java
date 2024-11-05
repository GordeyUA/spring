package com.sgordievskiy.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TaskHistoryResponseDto {

  private Long id;

  private Long todoId;

  private String oldState;

  private String newState;

  private Long changedBy;
}

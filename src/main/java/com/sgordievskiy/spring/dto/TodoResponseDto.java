package com.sgordievskiy.spring.dto;

import com.sgordievskiy.spring.model.Priority;
import com.sgordievskiy.spring.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TodoResponseDto {

  private Long id;

  private String title;

  private String description;

  private LocalDateTime dueDate;

  private Priority priority;

  private Status status;

  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;

  private Long userId;
}

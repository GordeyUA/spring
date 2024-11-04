package com.sgordievskiy.spring.dto;

import com.sgordievskiy.spring.model.Status;
import com.sgordievskiy.spring.model.Priority;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TodoUpdateDto {

  private long id;

  private String title;

  private String description;

  private LocalDateTime dueDate;

  private Priority priority;

  private Status status;
}

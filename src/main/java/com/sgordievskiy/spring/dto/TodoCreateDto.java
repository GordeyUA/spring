package com.sgordievskiy.spring.dto;

import com.sgordievskiy.spring.model.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Data
public class TodoCreateDto {

  @NotEmpty(message = "Task title can't be empty")
  @Length(max = 100)
  private String title;

  @Length(max = 500)
  private String description;

  @NotNull
  private LocalDateTime dueDate;

  private Priority priority;
}

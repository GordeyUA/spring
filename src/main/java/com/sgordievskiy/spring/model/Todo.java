package com.sgordievskiy.spring.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Table(name = "todos")
@SQLDelete(sql = "UPDATE todos SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  @Column(name = "due_date")
  private LocalDateTime dueDate;

  @Enumerated(EnumType.STRING)
  private Priority priority = Priority.MEDIUM;

  @Enumerated(EnumType.STRING)
  private Status status = Status.PENDING;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "updated_date", nullable = false)
  private LocalDateTime updatedDate;

  @Column(name = "user_id")
  private Long userId = 1L;

  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;

  public String getInfo() {
    return "Title: " + title
      + " Description: " + description
      + " Due date: " + dueDate
      + " Priority: " + priority
      + " Status: " + status;
  }
}

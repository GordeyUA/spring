package com.sgordievskiy.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@SQLDelete(sql = "UPDATE task_history SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted=false")
@Table(name = "task_history")
public class TaskHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "todo_id")
  private Todo todo;

  @Column(name = "old_state")
  private String oldState;

  @Column(name = "new_state")
  private String newState;

  @CreationTimestamp
  @Column(name = "change_date")
  private LocalDateTime changeDate;

  @Column(name = "changed_by")
  private Long changedBy = 1L;

  @Column(name = "is_deleted")
  private boolean isDeleted = false;
}

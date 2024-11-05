package com.sgordievskiy.spring.repository;

import java.util.List;
import com.sgordievskiy.spring.model.TaskHistory;
import com.sgordievskiy.spring.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {

  List<TaskHistory> findAllByTodo(Todo todo);
}

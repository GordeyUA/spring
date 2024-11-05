package com.sgordievskiy.spring.repository;

import com.sgordievskiy.spring.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}

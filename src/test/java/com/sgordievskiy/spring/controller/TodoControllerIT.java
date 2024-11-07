package com.sgordievskiy.spring.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.model.Priority;
import com.sgordievskiy.spring.model.Status;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerIT {

  protected static MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeAll
  static void beforeAll(
    @Autowired DataSource dataSource,
    @Autowired WebApplicationContext applicationContext
  ) throws SQLException {
    mockMvc = MockMvcBuilders
      .webAppContextSetup(applicationContext)
      .build();
    teardown(dataSource);
    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(true);
      ScriptUtils.executeSqlScript(
        connection,
        new ClassPathResource("database/add-default-task.sql")
      );
    }
  }

  @AfterAll
  static void afterAll(@Autowired DataSource dataSource) {
    teardown(dataSource);
  }

  @SneakyThrows
  static void teardown(DataSource dataSource) {
    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(true);
      ScriptUtils.executeSqlScript(
        connection,
        new ClassPathResource("database/clear-db.sql")
      );
    }
  }

  @Test
  @DisplayName("Create a new task")
  @Sql(
    scripts = "classpath:database/delete-test-todo.sql",
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
  )
  void createTodo_Success() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 7, 12, 15, 30);
    TodoCreateDto requestDto = new TodoCreateDto(
      "Title",
      "Description",
      date,
      null
    );

    TodoResponseDto expected = new TodoResponseDto(
      1L,
      "Title",
      "Description",
      date,
      Priority.MEDIUM,
      Status.PENDING,
      date,
      date,
      1L
    );

    String jsonRequest = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(
        post("/todos")
          .content(jsonRequest)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andReturn();

    TodoResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), TodoResponseDto.class);
    Assertions.assertNotNull(actual);
    Assertions.assertNotNull(actual.getId());
    Assertions.assertEquals(expected.getTitle(), actual.getTitle());
    Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    Assertions.assertEquals(expected.getDueDate(), actual.getDueDate());
    Assertions.assertEquals(expected.getPriority(), actual.getPriority());
    Assertions.assertEquals(expected.getStatus(), actual.getStatus());
  }

  @Test
  @DisplayName("Update existing task")
  @Sql(
    scripts = {"classpath:database/clear-db.sql", "classpath:database/add-default-task.sql"},
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
  )
  void updateTodo_Success() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 7, 12, 15, 30);
    TodoUpdateDto requestDto = new TodoUpdateDto(
      1L,
      "New title",
      "Description",
      date,
      Priority.MEDIUM,
      Status.IN_PROGRESS
    );

    TodoResponseDto expected = new TodoResponseDto(
      1L,
      "New title",
      "Description",
      date,
      Priority.MEDIUM,
      Status.IN_PROGRESS,
      date,
      date,
      1L
    );

    String jsonRequest = objectMapper.writeValueAsString(requestDto);

    MvcResult result = mockMvc.perform(
        put("/todos/1")
          .content(jsonRequest)
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andReturn();

    TodoResponseDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), TodoResponseDto.class);
    Assertions.assertNotNull(actual);
    Assertions.assertNotNull(actual.getId());
    Assertions.assertEquals(expected.getId(), actual.getId());
    Assertions.assertEquals(expected.getTitle(), actual.getTitle());
    Assertions.assertEquals(expected.getDescription(), actual.getDescription());
    Assertions.assertEquals(expected.getDueDate(), actual.getDueDate());
    Assertions.assertEquals(expected.getPriority(), actual.getPriority());
    Assertions.assertEquals(expected.getStatus(), actual.getStatus());
  }

  @Test
  @DisplayName("Delete existing task")
  @Sql(
    scripts = "classpath:database/restore-deleted-task.sql",
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
  )
  void deleteTodo_Success() throws Exception {
    MvcResult result = mockMvc.perform(
        delete("/todos/1")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andReturn();
  }

  @Test
  @DisplayName("Get all tasks")
  void getAll_ShouldReturnAllTasks() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 5, 1, 2, 3);
    List<TodoResponseDto> expected = new ArrayList<>();
    expected.add(new TodoResponseDto(
      1L,
      "Task",
      "",
      date,
      Priority.HIGH,
      Status.PENDING,
      date,
      date,
      1L
    ));

    // When
    MvcResult result = mockMvc.perform(
        get("/todos")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andReturn();

    // Then
    TodoResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), TodoResponseDto[].class);
    Assertions.assertEquals(1, actual.length);
    Assertions.assertEquals(expected, Arrays.stream(actual).toList());
  }

  @Test
  @DisplayName("Get task history")
  void getTaskHistory_ShouldReturnTaskHistory() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 5, 1, 2, 3);
    List<TaskHistoryResponseDto> expected = new ArrayList<>();
    expected.add(new TaskHistoryResponseDto(
      1L,
      1L,
      "Old state",
      "New state",
      1L
    ));

    // When
    MvcResult result = mockMvc.perform(
        get("/todos/1/history")
          .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andReturn();

    // Then
    TaskHistoryResponseDto[] actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), TaskHistoryResponseDto[].class);
    Assertions.assertEquals(1, actual.length);
    Assertions.assertEquals(expected, Arrays.stream(actual).toList());
  }
}

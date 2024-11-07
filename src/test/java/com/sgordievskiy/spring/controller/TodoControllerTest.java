package com.sgordievskiy.spring.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import com.sgordievskiy.spring.dto.TodoCreateDto;
import com.sgordievskiy.spring.dto.TodoResponseDto;
import com.sgordievskiy.spring.dto.TodoUpdateDto;
import com.sgordievskiy.spring.model.Priority;
import com.sgordievskiy.spring.model.Status;
import com.sgordievskiy.spring.service.TaskHistoryService;
import com.sgordievskiy.spring.service.TodoService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = TodoController.class, excludeAutoConfiguration = {
  org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, // disable security checks
  org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})public class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private TodoService todoService;

  @MockBean
  private TaskHistoryService taskHistoryService;

  @Test
  void getAll_ValidRequest_Success() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 7, 12, 15, 30);
    TodoResponseDto responseDto = new TodoResponseDto(
      1L,
      "Title1",
      "Description1",
      date,
      Priority.MEDIUM,
      Status.PENDING,
      date,
      date,
      1L
    );

    Mockito.when(todoService.getAll()).thenReturn(List.of(responseDto));

    mockMvc.perform(MockMvcRequestBuilders.get("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(""))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(responseDto.getId()))
      .andExpect(jsonPath("$[0].title").value(responseDto.getTitle()))
      .andExpect(jsonPath("$[0].description").value(responseDto.getDescription()))
      .andExpect(jsonPath("$[0].dueDate").value(responseDto.getDueDate().toString()))
      .andExpect(jsonPath("$[0].priority").value(responseDto.getPriority().toString()))
      .andExpect(jsonPath("$[0].status").value(responseDto.getStatus().toString()))
      .andExpect(jsonPath("$[0].createdDate").value(responseDto.getCreatedDate().toString()))
      .andExpect(jsonPath("$[0].updatedDate").value(responseDto.getUpdatedDate().toString()))
      .andExpect(jsonPath("$[0].userId").value(responseDto.getUserId()));
  }

  @Test
  void createTodo_ValidRequest_Success() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 7, 12, 15, 30);
    TodoCreateDto requestDto = new TodoCreateDto(
      "Title",
      "Description",
      date,
      null
    );

    TodoResponseDto responseDto = new TodoResponseDto(
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

    Mockito.when(todoService.create(requestDto)).thenReturn(responseDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(responseDto.getId()))
      .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
      .andExpect(jsonPath("$.description").value(responseDto.getDescription()))
      .andExpect(jsonPath("$.dueDate").value(responseDto.getDueDate().toString()))
      .andExpect(jsonPath("$.priority").value(responseDto.getPriority().toString()))
      .andExpect(jsonPath("$.status").value(responseDto.getStatus().toString()))
      .andExpect(jsonPath("$.createdDate").value(responseDto.getCreatedDate().toString()))
      .andExpect(jsonPath("$.updatedDate").value(responseDto.getUpdatedDate().toString()))
      .andExpect(jsonPath("$.userId").value(responseDto.getUserId()));
  }

  @Test
  void createTodo_InvalidRequest_ShouldReturnBadRequest() throws Exception {
    TodoCreateDto invalidRequest = new TodoCreateDto(
      null,
      StringUtils.repeat("*", 501),
      null,
      null
    );

    mockMvc.perform(MockMvcRequestBuilders.post("/todos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidRequest)))
      .andExpect(status().isBadRequest())
      .andExpect(content().string(containsString("\"title\":\"Task title can't be empty\"")))
      .andExpect(content().string(containsString("\"description\":\"length must be between 0 and 500\"")))
      .andExpect(content().string(containsString("\"dueDate\":\"must not be null\"")));
  }

  @Test
  void updateTodo_ValidRequest_Success() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 7, 12, 15, 30);
    TodoUpdateDto requestDto = new TodoUpdateDto(
      1L,
      "Title",
      "Description",
      date,
      Priority.HIGH,
      Status.IN_PROGRESS
    );

    TodoResponseDto responseDto = new TodoResponseDto(
      1L,
      "Title",
      "Description",
      date,
      Priority.HIGH,
      Status.IN_PROGRESS,
      null,
      null,
      1L
    );

    Mockito.when(todoService.update(requestDto)).thenReturn(responseDto);

    mockMvc.perform(MockMvcRequestBuilders.put("/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(responseDto.getId()))
      .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
      .andExpect(jsonPath("$.description").value(responseDto.getDescription()))
      .andExpect(jsonPath("$.dueDate").value(responseDto.getDueDate().toString()))
      .andExpect(jsonPath("$.priority").value(responseDto.getPriority().toString()))
      .andExpect(jsonPath("$.status").value(responseDto.getStatus().toString()))
      .andExpect(jsonPath("$.createdDate").value(responseDto.getCreatedDate()))
      .andExpect(jsonPath("$.updatedDate").value(responseDto.getUpdatedDate()))
      .andExpect(jsonPath("$.userId").value(responseDto.getUserId()));
  }

  @Test
  void updateTodo_InvalidRequest_ShouldReturnBadRequest() throws Exception {
    TodoUpdateDto invalidRequest = new TodoUpdateDto(
      1L,
      null,
      StringUtils.repeat("*", 501),
      null,
      Priority.HIGH,
      null
    );

    mockMvc.perform(MockMvcRequestBuilders.put("/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(invalidRequest)))
      .andExpect(status().isBadRequest())
      .andExpect(content().string(containsString("\"title\":\"Task title can't be empty\"")))
      .andExpect(content().string(containsString("\"description\":\"length must be between 0 and 500\"")))
      .andExpect(content().string(containsString("\"dueDate\":\"must not be null\"")))
      .andExpect(content().string(containsString("\"status\":\"must not be null\"")));
  }

  @Test
  void deleteTodo_ValidRequest_Success() throws Exception {
    Mockito.doNothing().when(todoService).delete(1L);

    mockMvc.perform(MockMvcRequestBuilders.delete("/todos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(""))
      .andExpect(status().isOk());
  }

  @Test
  void deleteTodo_InvalidRequest_ShouldReturnNotFound() throws Exception {
    Mockito.doThrow(new NoSuchElementException("Task not found.")).when(todoService).delete(2L);

    mockMvc.perform(MockMvcRequestBuilders.delete("/todos/2")
        .contentType(MediaType.APPLICATION_JSON)
        .content(""))
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Task not found.")));
  }

  @Test
  void getHistory_ValidRequest_Success() throws Exception {
    LocalDateTime date = LocalDateTime.of(2024, 11, 7, 12, 15, 30);
    TaskHistoryResponseDto responseDto = new TaskHistoryResponseDto(
      1L,
      1L,
      "Old state",
      "New state",
      1L
    );

    Mockito.when(taskHistoryService.getAllByTaskId(1L)).thenReturn(List.of(responseDto));

    mockMvc.perform(MockMvcRequestBuilders.get("/todos/1/history")
        .contentType(MediaType.APPLICATION_JSON)
        .content(""))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(responseDto.getId()))
      .andExpect(jsonPath("$[0].todoId").value(responseDto.getTodoId()))
      .andExpect(jsonPath("$[0].oldState").value(responseDto.getOldState()))
      .andExpect(jsonPath("$[0].newState").value(responseDto.getNewState()))
      .andExpect(jsonPath("$[0].changedBy").value(responseDto.getChangedBy()));
  }

  @Test
  void getHistory_InvalidRequest_ShouldReturnNotFound() throws Exception {
    Mockito.when(taskHistoryService.getAllByTaskId(2L)).thenThrow(new NoSuchElementException("Task not found."));

    mockMvc.perform(MockMvcRequestBuilders.get("/todos/2/history")
        .contentType(MediaType.APPLICATION_JSON)
        .content(""))
      .andExpect(status().isNotFound())
      .andExpect(content().string(containsString("Task not found.")));
  }
}

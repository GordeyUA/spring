package com.sgordievskiy.spring.service;

import com.sgordievskiy.spring.dto.TaskHistoryResponseDto;
import java.util.List;

public interface TaskHistoryService {

  List<TaskHistoryResponseDto> getAllByTaskId(Long taskId);
}

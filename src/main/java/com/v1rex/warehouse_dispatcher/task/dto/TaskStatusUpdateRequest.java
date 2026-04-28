package com.v1rex.warehouse_dispatcher.task.dto;

import com.v1rex.warehouse_dispatcher.task.enums.TaskStatus;

public record TaskStatusUpdateRequest(TaskStatus status) {
}

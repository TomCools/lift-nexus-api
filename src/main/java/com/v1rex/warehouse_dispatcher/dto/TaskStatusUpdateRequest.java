package com.v1rex.warehouse_dispatcher.dto;

import com.v1rex.warehouse_dispatcher.enums.TaskStatus;

public record TaskStatusUpdateRequest(TaskStatus status) {
}

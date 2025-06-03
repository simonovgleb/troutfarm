package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.User;
import by.simonov.troutfarm.backend.entity.type.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    private UUID id;

    private String title;

    private String description;

    private User assignedTo;

    private User createdBy;

    private OffsetDateTime dueDate;

    private TaskStatus status;
}

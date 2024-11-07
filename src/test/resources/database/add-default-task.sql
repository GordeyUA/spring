insert into todos (id, title, description, due_date, priority, status, created_date, updated_date, user_id, is_deleted)
values (1, "Task", "", "2024-11-05 01:02:03", "HIGH", "PENDING", "2024-11-05 01:02:03", "2024-11-05 01:02:03", 1, 0);

insert into task_history (id, todo_id, old_state, new_state, change_date, changed_by, is_deleted)
values (1, 1, "Old state", "New state", "2024-11-05 01:02:03", 1, 0);

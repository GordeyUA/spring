--liquibase formatted sql
--changeset sgordievskiy:create-task_history-table
CREATE TABLE task_history (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    todo_id BIGINT NOT NULL,
    old_state VARCHAR(2550),
    new_state VARCHAR(2550),
    change_date DATETIME NOT NULL,
    changed_by BIGINT NOT NULL,
    is_deleted BIT,

    FOREIGN KEY (todo_id) REFERENCES todos (id) ON DELETE CASCADE
) ENGINE=InnoDB;

--rollback DROP TABLE task_history;

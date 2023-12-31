package ru.tinkoff.java.serminar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SimpleTaskTracker implements TaskTracker {

    private final Map<UUID, Task> tasks = new HashMap<>();
    private final HistoryViewManager<UUID> historyViewManager = new InMemoryHistoryViewManager<>();

    @Override
    public Task createTask(Task task) {
        task.setId(UUID.randomUUID());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task getTask(UUID uuid) {
        historyViewManager.addView(uuid);
        return tasks.get(uuid);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTask(UUID uuid) {
        tasks.remove(uuid);
    }

    @Override
    public List<Task> getPriorityTasks() {
        return tasks.values()
            .stream()
            .sorted(Comparator.comparing(Task::getPriority))
            .toList();
    }

    @Override
    public List<Task> getViewHistory() {
        return historyViewManager.getViewHistory().stream()
            .map(tasks::get)
            .toList();
    }

    @Override
    public Map<TaskStatus, List<Task>> getStatusBoard() {
        return tasks.values()
            .stream()
            .collect(Collectors.groupingBy(Task::getStatus));
    }
}

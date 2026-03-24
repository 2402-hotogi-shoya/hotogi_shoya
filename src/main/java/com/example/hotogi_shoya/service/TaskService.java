package com.example.hotogi_shoya.service;

import com.example.hotogi_shoya.controller.form.TaskForm;
import com.example.hotogi_shoya.repository.TaskRepository;
import com.example.hotogi_shoya.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /*
     * レコード全件取得処理
     */
    public List<TaskForm> findAllReport(LocalDate starDate, LocalDate endDate, String filterText, Short status) {
        List<Task> results =
                taskRepository.findTasksReport(starDate, endDate, filterText, status);
        List<TaskForm> reports = setCommentForm(results);
        return reports;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<TaskForm> setCommentForm(List<Task> results) {
        List<TaskForm> tasks = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            TaskForm task = new TaskForm();
            Task result = results.get(i);

            task.setId(result.getId());
            task.setContent(result.getContent());
            task.setStatus(result.getStatus());
            task.setLimitDate(result.getLimitDate());

            tasks.add(task);
        }
        return tasks;
    }

    /*
     * レコード追加
     */
    public void saveTask(TaskForm reqTask) {
        reqTask.setStatus((short) 1);
        Task saveTask = setTaskEntity(reqTask);
        taskRepository.save(saveTask);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Task setTaskEntity(TaskForm reqTask) {

        Task task = new Task();
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setStatus(reqTask.getStatus());
        task.setLimitDate(reqTask.getLimitDate());
        return task;
    }

    /*
     * レコード1件取得処理
     */
    public Task selectTask(Integer id) {
        Task result = taskRepository.findById(id).orElse(null);
        return result;
    }

    /*
     * 投稿編集処理
     */
    public void updateTaskEntity(TaskForm reqTask) {
        Task saveTask = taskRepository.findById(reqTask.getId())
                .orElseThrow(() -> new RuntimeException("データがない"));
        saveTask.setContent(reqTask.getContent());
        saveTask.setLimitDate(reqTask.getLimitDate());
        taskRepository.save(saveTask);
    }

    /*
     * レコード削除
     */
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}

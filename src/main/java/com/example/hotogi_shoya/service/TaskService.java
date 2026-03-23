package com.example.hotogi_shoya.service;

import com.example.hotogi_shoya.controller.form.TaskForm;
import com.example.hotogi_shoya.repository.TaskRepository;
import com.example.hotogi_shoya.repository.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    /*
     * レコード全件取得処理
     */
    public List<TaskForm> findAllReport() {
        List<Task> results = taskRepository.findAllByOrderByLimitDateAsc();
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
            task.setLimit_date(result.getLimitDate());

            tasks.add(task);
        }
        return tasks;
    }

    /*
     * レコード追加
     */
    public void saveTask(TaskForm reqTask) {
        Task saveTask = setReportEntity(reqTask);
        taskRepository.save(saveTask);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Task setReportEntity(TaskForm reqTask) {
        // 現在日時を取得
        LocalDateTime nowDate = LocalDateTime.now();

        // 表示形式を指定
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS"); // ①
        String formatNowDate = dtf1.format(nowDate); // ②

        Task task = new Task();
        task.setId(reqTask.getId());
        task.setContent(reqTask.getContent());
        task.setLimitDate(reqTask.getLimit_date());
        task.setUpdatedDate(LocalDateTime.parse(formatNowDate, dtf1));
        task.setCreatedDate(LocalDateTime.parse(formatNowDate, dtf1));
        return task;
    }
}

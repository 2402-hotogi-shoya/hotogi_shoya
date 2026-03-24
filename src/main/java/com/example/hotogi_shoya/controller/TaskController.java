package com.example.hotogi_shoya.controller;

import com.example.hotogi_shoya.controller.form.TaskForm;
import com.example.hotogi_shoya.repository.entity.Task;
import com.example.hotogi_shoya.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public ModelAndView top(@RequestParam(required = false) String startDate,
                            @RequestParam(required = false) String endDate,
                            @RequestParam(required = false) String filterText,
                            @RequestParam(required = false) Short status,
                            Model model) {

        //値と内容の紐づけ用
        //TODO 後で共通化しとく
        Map<Short, String> map = new HashMap<>();
        map.put((short)1, "未着手");
        map.put((short)2, "実行中");
        map.put((short)3, "ステイ中");
        map.put((short)4, "完了");

        ModelAndView mav = new ModelAndView();
        // 画面遷移先を指定
        mav.setViewName("/top");


        //今日の日付取得
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        LocalDate start;
        LocalDate end;
        if (startDate == null || startDate.isEmpty()) {
            start = LocalDate.of(2022, 1, 1);
        } else {
            start = LocalDate.parse(startDate);
        }

        if (endDate == null || endDate.isEmpty()) {
            end = LocalDate.of(2100, 12, 31);
        } else {
            end = LocalDate.parse(endDate);
        }

        //blankチェック
        String filter = (filterText != null && !filterText.isBlank()) ? filterText : null;
        // 投稿を全件取得
        List<TaskForm> taskData = taskService.findAllReport(start, end, filter, status);


        mav.addObject("tasks", taskData);
        mav.addObject("map", map);
        mav.addObject("today", today);

        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        TaskForm taskForm = new TaskForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("taskModel", taskForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@Valid @ModelAttribute("taskModel") TaskForm taskForm, BindingResult result){
        // バリデーションエラーがある場合は入力画面に戻す
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/new");
            mav.addObject("taskModel", taskForm);
            return mav;
        }

        // 投稿をテーブルに格納
        taskService.saveTask(taskForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿編集処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable int id) {

        ModelAndView mav = new ModelAndView();
        //編集するコメントを取得
        Task task = taskService.selectTask(id);
        //編集するコメントをセット
        mav.addObject("taskModel", task);
        //画面遷移先を指定
        mav.setViewName("/edit");

        return mav;
    }

    /*
     * 投稿編集処理
     */
    @PostMapping("/update/{id}")
    public ModelAndView editContent(@PathVariable Integer id,
                                    @Valid @ModelAttribute("taskModel") TaskForm taskForm,
                                    BindingResult result) {

        // バリデーションエラーがある場合は入力画面に戻す
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/edit");
            mav.addObject("taskModel", taskForm);
            return mav;
        }

        //編集するコメントをセット
        taskService.updateTaskEntity(taskForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント新規投稿処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteTask(@PathVariable int id) {

        // 投稿をテーブルに格納
        taskService.deleteTask(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

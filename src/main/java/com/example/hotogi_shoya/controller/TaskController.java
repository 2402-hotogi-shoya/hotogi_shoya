package com.example.hotogi_shoya.controller;

import com.example.hotogi_shoya.controller.form.TaskForm;
import com.example.hotogi_shoya.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView top() {

        ModelAndView mav = new ModelAndView();
        // 画面遷移先を指定
        mav.setViewName("/top");

        // 投稿を全件取得
        List<TaskForm> taskData = taskService.findAllReport();

        //今日の日付取得
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        //値と内容の紐づけ用
        Map<Short, String> map = new HashMap<>();
        map.put((short)1, "未着手");
        map.put((short)2, "実行中");
        map.put((short)3, "ステイ中");
        map.put((short)4, "完了");

        mav.addObject("tasks", taskData);
        mav.addObject("map", map);
        mav.addObject("today", formattedDate);

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

}

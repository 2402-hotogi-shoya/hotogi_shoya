package com.example.hotogi_shoya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TaskController {

    @GetMapping
    public ModelAndView top() {

        ModelAndView mav = new ModelAndView();

        // 画面遷移先を指定
        mav.setViewName("/top");

        return mav;
    }
}

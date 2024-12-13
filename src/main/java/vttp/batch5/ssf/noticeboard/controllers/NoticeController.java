package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;


// Use this class to write your request handlers
@Controller
public class NoticeController {

    @GetMapping("")
    public String getNoticeBoard(Model model,HttpSession session) {
        
        return "notice";
    }
    
}

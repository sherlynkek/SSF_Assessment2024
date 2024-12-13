package vttp.batch5.ssf.noticeboard.controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Use this class to write your request handlers

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping(produces = "text/html")
    public String noticeBoard(@RequestParam(required = true, name = "title") String title,
    @RequestParam(required = true, name = "poster") String poster, 
    @RequestParam(required = true, name = "postDate") Date postDate,
    @RequestParam(required = true, name = "categories") String categories,
    @RequestParam(required = true, name = "text") String text,
    Model model) {
        
        model.addAttribute("title", title);
        model.addAttribute("poster", poster);
        model.addAttribute("postDate", postDate);
        model.addAttribute("categories", categories);
        model.addAttribute("text", text);

        return "notice";
    }
    
}

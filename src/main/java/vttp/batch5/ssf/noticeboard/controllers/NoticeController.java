package vttp.batch5.ssf.noticeboard.controllers;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

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
    
    @PostMapping(consumes = "application/json")
    public String postNoticeBoard(@Valid @ModelAttribute("notice") Notice entity, BindingResult result) {
        if(result.hasErrors()) {
            return "view3";
        }
        noticeService.postToNoticeServer();
        return "notice";
    }

    @GetMapping
    public String successful() {
        
        return "redirect:/notice";
    }

    @GetMapping
    public String error() {
        
        return "redirect:/notice";
    }

    @GetMapping("/health")
    public ModelAndView getHealth() {
        ModelAndView mav = new ModelAndView();

        try {
            checkHealth();

            mav.setViewName("OK");
            mav.setStatus(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            mav.setViewName("Service Unavailable");
            mav.setStatus(HttpStatusCode.valueOf(503));
        }

        return mav;
    }

    private ResponseEntity<String> checkHealth() {
        File f = new File("");
        if (f.exists() && f.isFile()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(400).body("{}");
    }
}

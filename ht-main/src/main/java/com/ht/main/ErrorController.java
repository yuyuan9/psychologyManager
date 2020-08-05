package com.ht.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    /**
     * 404页面
     */
    @GetMapping(value = "/404")
    public String error_404() {
        return "notFound/not_found.html";
    }

    /**
     * 500页面
     */
    @GetMapping(value = "/500")
    public String error_500() {
        return "notFound/service_error.html";
    }

}

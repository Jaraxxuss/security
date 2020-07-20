package by.itsupportme.security.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class TemplateController {
    @GetMapping("/login")
    @ResponseBody
    fun login(): String{
        return "ello"
    }

}
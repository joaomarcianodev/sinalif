package sinalif.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
public class AdminPagesController {

    @GetMapping()
    public String pageAdmin() {
        return "pages/adm/indexAdmin";
    }
}

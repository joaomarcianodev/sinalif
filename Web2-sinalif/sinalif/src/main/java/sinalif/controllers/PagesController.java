package sinalif.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class PagesController {

    @GetMapping(value = {"/adm", "/adm/"})
    public String pageAdmin() {
        return "pages/adm/indexAdmin";
    }

}

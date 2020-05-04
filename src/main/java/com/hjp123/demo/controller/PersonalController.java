package com.hjp123.demo.controller;

import com.hjp123.demo.bean.Dynamic;
import com.hjp123.demo.dto.PaginationDTO;
import com.hjp123.demo.mapper.DynamicMapper;
import com.hjp123.demo.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @RequestMapping("/Personal/{id}")
    public String PersonalIndex(@PathVariable Long id,
                                Model model,
                                @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "10") Integer size){
        if (id != null && !id.equals("")){
            PaginationDTO paginationDTO = personalService.selectAll(id,page,size);
            if (paginationDTO != null){
                model.addAttribute("pagination",paginationDTO);
            }
        }

        return "Personal";
    }

    @RequestMapping("/personalmodify/{id}")
    public String PersonalModifyIndex(@PathVariable Long id){
        System.out.println(id);
        return "personalmodify";
    }
}

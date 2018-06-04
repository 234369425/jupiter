package com.beheresoft.security.controller.backend;

import com.beheresoft.security.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aladi
 * @date 2018-06-04 22:33:51
 */
@RestController
@RequestMapping("/internal/application")
public class ApplicationController {

    public ApplicationController(){

    }

    @RequestMapping("/list.json")
    public Result list(){
        return Result.ok();
    }

    @RequestMapping("/create.json")
    public Result create(){
        return Result.ok();
    }

    @RequestMapping("/delete.json")
    public Result delete(){
        return Result.ok();
    }

}

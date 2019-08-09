package com.atguigu.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class HelloWold {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @GetMapping("/query")
    public Map<String,Object> map(){
        String sql = "select * from department";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps.get(0);
    }
}

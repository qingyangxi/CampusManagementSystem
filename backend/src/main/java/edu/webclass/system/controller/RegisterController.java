package edu.webclass.system.controller;

import edu.webclass.system.pojo.Emp;
import edu.webclass.system.pojo.Result;
import edu.webclass.system.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {
    @Autowired
    private EmpService empService;

    @PostMapping("/register")
    public Result save(@RequestBody Emp emp){
        log.info("注册员工, emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }
}

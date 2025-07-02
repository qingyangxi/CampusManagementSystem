package edu.webclass.system.controller;

import edu.webclass.system.pojo.Score;
import edu.webclass.system.pojo.Stu;
import edu.webclass.system.pojo.PageBean;
import edu.webclass.system.pojo.Result;
import edu.webclass.system.service.StuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class StuController {

    @Autowired
    private StuService stuService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "999") Integer pageSize,
                       String name,
                       String stuId,
                       Short education,
                       Integer classId
                       ){
        log.info("分页查询, 参数: {},{},{},{},{},{}",page,pageSize,name,stuId,education,classId);
        //调用service分页查询
        PageBean pageBean = stuService.page(page,pageSize,name,stuId,education,classId);
        return Result.success(pageBean);
    }
//
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("批量删除操作, ids:{}",ids);
        stuService.delete(ids);
        return Result.success();
    }
//
    @PostMapping
    public Result save(@RequestBody Stu stu){
        log.info("新增员工, stu: {}", stu);
        stuService.save(stu);
        return Result.success();
    }
//
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息, id: {}",id);
        Stu stu = stuService.getById(id);
        return Result.success(stu);
    }

    @PutMapping
    public Result update(@RequestBody Stu stu){
        log.info("更新员工信息 : {}", stu);
        stuService.update(stu);
        return Result.success();
    }

    @PutMapping("/point/{id}")
    public Result dealP(@PathVariable Integer id, @RequestBody Score score){
        log.info("违纪处理: id: {}, score: {}", id, score);
        stuService.dealP(id, score);
        return Result.success();
    }
}






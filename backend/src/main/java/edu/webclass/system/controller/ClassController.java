package edu.webclass.system.controller;

import edu.webclass.system.pojo.*;
import edu.webclass.system.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 班级管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "999") Integer pageSize,
                       String name,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询, 参数: {},{},{},{},{}",page,pageSize,name,begin,end);
        //调用service分页查询
        PageBean pageBean = classService.page(page,pageSize,name,begin,end);
        return Result.success(pageBean);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        log.info("根据id删除班级:{}",id);
        //调用service删除班级
        classService.delete(id);
        return Result.success();
    }

    /**
     * 新增班级
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Cls cls){
        log.info("新增班级: {}" , cls);
        //调用service新增班级
        classService.insert(cls);
        return Result.success();
    }


//
//    @DeleteMapping("/{ids}")
//    public Result delete(@PathVariable List<Integer> ids){
//        log.info("批量删除操作, ids:{}",ids);
//        classService.delete(ids);
//        return Result.success();
//    }
//
//    @PostMapping
//    public Result save(@RequestBody Emp emp){
//        log.info("新增员工, emp: {}", emp);
//        classService.save(emp);
//        return Result.success();
//    }
//
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询员工信息, id: {}",id);
        Cls cls = classService.selectById(id);
        return Result.success(cls);
    }
//
    @PutMapping
    public Result update(@RequestBody Cls cls){
        log.info("更新班级信息 : {}", cls);
        classService.update(cls);
        return Result.success();
    }
}






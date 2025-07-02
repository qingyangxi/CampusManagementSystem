package edu.webclass.system.controller;

import edu.webclass.system.pojo.Dept;
import edu.webclass.system.pojo.Result;
import edu.webclass.system.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理Controller
 */
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    //private static Logger log = LoggerFactory.getLogger(DeptController.class);
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门数据
     * @return
     */
    //@RequestMapping(value = "/depts",method = RequestMethod.GET) //指定请求方式为GET
    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");
        //调用service查询部门数据
        List<Dept> deptList =  deptService.list();
        return Result.success(deptList);
    }


    /**
     * 删除部门
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        log.info("根据id删除部门:{}",id);
        //调用service删除部门
        deptService.delete(id);
        return Result.success();
    }


    /**
     * 新增部门
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门: {}" , dept);
        //调用service新增部门
        deptService.insert(dept);
        return Result.success();
    }

    /**
     * 根据id查询部门
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        log.info("根据id查询部门:{} ", id);
        Dept dept = deptService.selectById(id);
        return Result.success(dept);
    }

    /**
     * 更新部门
     * @param dept
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("更新部门");
        deptService.update(dept);
        return Result.success();
    }


}

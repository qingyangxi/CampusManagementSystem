package edu.webclass.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.webclass.system.mapper.ClassMapper;
import edu.webclass.system.mapper.EmpMapper;
import edu.webclass.system.mapper.StuMapper;
import edu.webclass.system.pojo.Cls;
import edu.webclass.system.pojo.Dept;
import edu.webclass.system.pojo.Emp;
import edu.webclass.system.pojo.PageBean;
import edu.webclass.system.service.ClassService;
import edu.webclass.system.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize,String name, LocalDate begin, LocalDate end) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<Cls> clsList = classMapper.list(name, begin, end);
        Page<Cls> p = (Page<Cls>) clsList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }
//
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
        classMapper.deleteById(id); //根据ID删除部门数据
    }

    @Override
    public void insert(Cls cls) {

        classMapper.insert(cls);
    }

    @Override
    public void update(Cls cls) {
        classMapper.update(cls);
    }

    @Override
    public Cls selectById(Integer id) {
        return classMapper.selectById(id);
    }
//
//    @Override
//    public void save(Emp emp) {
//        emp.setCreateTime(LocalDateTime.now());
//        emp.setUpdateTime(LocalDateTime.now());
//        empMapper.insert(emp);
//    }
//
//    @Override
//    public Emp getById(Integer id) {
//        return empMapper.getById(id);
//    }
//
//    @Override
//    public void update(Emp emp) {
//        emp.setUpdateTime(LocalDateTime.now());
//
//        empMapper.update(emp);
//    }
//
//    @Override
//    public Emp login(Emp emp) {
//        return empMapper.getByUsernameAndPassword(emp);
//    }
}

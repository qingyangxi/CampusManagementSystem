package edu.webclass.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.webclass.system.mapper.StuMapper;
import edu.webclass.system.pojo.Score;
import edu.webclass.system.pojo.Stu;
import edu.webclass.system.pojo.PageBean;
import edu.webclass.system.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name,String stuId, Short education, Integer classId) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);

        //2. 执行查询
        List<Stu> stuList = stuMapper.list(page,pageSize,name,stuId,education,classId);
        Page<Stu> p = (Page<Stu>) stuList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        stuMapper.delete(ids);
    }
//
    @Override
    public void save(Stu stu) {
        stu.setIndisciplineTime(0);
        stu.setIndisciplinePoints(0);
        stu.setUpdateTime(LocalDateTime.now());
        stuMapper.insert(stu);
    }
//
    @Override
    public Stu getById(Integer id) {
        return stuMapper.getById(id);
    }

    @Override
    public void update(Stu stu) {
        stu.setUpdateTime(LocalDateTime.now());
        stuMapper.update(stu);
    }

    @Override
    public void dealP(Integer id, Score score) {
        stuMapper.dealP(id, score.getScore());
    }
//
//    @Override
//    public Stu login(Stu stu) {
//        return stuMapper.getByUsernameAndPassword(stu);
//    }
}

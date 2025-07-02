package edu.webclass.system.service.impl;

import edu.webclass.system.mapper.DeptMapper;
import edu.webclass.system.mapper.EmpMapper;
import edu.webclass.system.pojo.Dept;
import edu.webclass.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
//    @Autowired
//    private EmpMapper empMapper;

    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    //@Transactional(rollbackFor = Exception.class) //spring事务管理
    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
            deptMapper.deleteById(id); //根据ID删除部门数据

//            empMapper.deleteByDeptId(id); //根据部门ID删除该部门下的员工
    }

    @Override
    public void insert(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }

    @Override
    public void update(Dept dept) {
        //为dept对象设置更新时间，这部分工作也可以放到mapper进行处理。
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }

    @Override
    public Dept selectById(Integer id) {
        return deptMapper.selectById(id);
    }
}

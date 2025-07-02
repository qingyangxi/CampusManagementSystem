package edu.webclass.system.service;

import edu.webclass.system.pojo.Cls;
import edu.webclass.system.pojo.Dept;
import edu.webclass.system.pojo.Emp;
import edu.webclass.system.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface ClassService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean page(Integer page, Integer pageSize,String name,LocalDate begin,LocalDate end);

    /**
     * 删除班级
     * @param id
     */
    void delete(Integer id) throws Exception;

    /**
     * 新增班级
     * @param cls 班级信息
     */
    void insert(Cls cls);

    /**
     * 更新班级信息
     * @param cls 初始的班级信息
     * @return 修改后的班级信息
     */
    void update(Cls cls);

    /**
     * 根据班级的id查询班级信息
     * @param id 班级id
     * @return 班级信息
     */
    Cls selectById(Integer id);
    //
//    /**
//     * 批量删除
//     * @param ids
//     */
//    void delete(List<Integer> ids);
//
//    /**
//     * 新增员工
//     * @param emp
//     */
//    void save(Emp emp);
//
//    /**
//     * 根据ID查询员工
//     * @param id
//     * @return
//     */
//    Cls selectById(Integer id);
//
//    /**
//     * 更新员工
//     * @param emp
//     */
//    void update(Emp emp);
//
//    /**
//     * 员工登录
//     * @param emp
//     * @return
//     */
//    Emp login(Emp emp);
}

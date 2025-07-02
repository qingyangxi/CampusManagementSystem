package edu.webclass.system.service;

import edu.webclass.system.pojo.Emp;
import edu.webclass.system.pojo.PageBean;
import edu.webclass.system.pojo.Score;
import edu.webclass.system.pojo.Stu;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface StuService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    PageBean page(Integer page, Integer pageSize, String name,String stuId, Short education, Integer classId);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param stu
     */
    void save(Stu stu);
//
    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    Stu getById(Integer id);

    /**
     * 更新员工
     * @param stu
     */
    void update(Stu stu);

    /**
     * 违纪处理
     * @param id
     * @param score
     */
    void dealP(Integer id, Score score);
//
//    /**
//     * 员工登录
//     * @param emp
//     * @return
//     */
//    Emp login(Emp emp);
}

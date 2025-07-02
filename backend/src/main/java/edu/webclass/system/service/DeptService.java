package edu.webclass.system.service;

import edu.webclass.system.pojo.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {
    /**
     * 查询全部部门数据
     * @return
     */
    List<Dept> list();

    /**
     * 删除部门
     * @param id
     */
    void delete(Integer id) throws Exception;

    /**
     * 更新部门信息
     * @param dept 初始的部门信息
     * @return 修改后的部门信息
     */
    void update(Dept dept);

    /**
     * 根据部门的id查询部门信息
     * @param id 部门id
     * @return 部门信息
     */
    Dept selectById(Integer id);

    /**
     * 新增部门
     * @param dept 部门信息
     */
    void insert(Dept dept);
}

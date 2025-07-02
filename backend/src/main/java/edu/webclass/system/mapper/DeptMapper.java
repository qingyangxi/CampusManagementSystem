package edu.webclass.system.mapper;

import edu.webclass.system.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门管理
 */
@Mapper
public interface DeptMapper {
    /**
     * 查询全部部门
     * @return
     */
    @Select("select * from dept")
    List<Dept> list();

    /**
     * 根据ID删除部门
     * @param id
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    @Insert("insert into dept(name, create_time, update_time) values(#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 根据id查询部门
     *
     * @param id 部门id
     * @return 根据部门id查询得到的部门对象
     */
    @Select("select * from dept where id = #{id}")
    Dept selectById(Integer id);

    /**
     * 更新部门信息
     *
     * @param dept 待部门对象
     */
    @Update("update dept set name = #{name} , update_time = #{updateTime} where id= #{id}")
    void update(Dept dept);

}

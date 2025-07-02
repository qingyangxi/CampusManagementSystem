package edu.webclass.system.mapper;

import edu.webclass.system.pojo.Cls;
import edu.webclass.system.pojo.Dept;
import edu.webclass.system.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface ClassMapper {

    /**
     * 查询总记录数
     * @return
     */
    //@Select("select count(*) from emp")
    //public Long count();

    /**
     * 分页查询,获取列表数据
     * @param start
     * @param pageSize
     * @return
     */
    //@Select("select * from emp limit #{start},#{pageSize}")
    //public List<Emp> page(Integer start, Integer pageSize);

    /**
     * 员工信息查询
     * @return
     */
//    @Select("select * from class")
    public List<Cls> list(String name, LocalDate begin, LocalDate end);

    /**
     * 根据ID删除班级
     * @param id
     */
    @Delete("delete from class where id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增班级
     * @param cls
     */
    @Insert("insert into class(name, classroom, start_time, end_time, teacher_id) values(#{name}, #{classroom}, #{startTime}, #{endTime}, #{teacherId})")
    void insert(Cls cls);

    /**
     * 根据id查询班级
     *
     * @param id 班级id
     * @return 根据班级id查询得到的班级对象
     */
    @Select("select * from class where id = #{id}")
    Cls selectById(Integer id);

    /**
     * 更新班级信息
     *
     * @param cls 待班级对象
     */
//    @Update("update class set name = #{name} where id= #{id}")
     void update(Cls cls);
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
//    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
//            " values(#{username},#{name},#{gender},#{image},#{job},#{entrydate},#{deptId},#{createTime},#{updateTime})")
//    void insert(Emp emp);
//
//    /**
//     * 根据ID查询员工
//     * @param id
//     * @return
//     */
//    @Select("select * from emp where  id = #{id}")
//    Emp getById(Integer id);
//
//    /**
//     * 更新员工
//     * @param emp
//     */
//    void update(Emp emp);
//
//    /**
//     * 根据用户名和密码查询员工
//     * @param emp
//     * @return
//     */
//    @Select("select * from emp where username = #{username} and password = #{password}")
//    Emp getByUsernameAndPassword(Emp emp);
//
//    /**
//     * 根据班级ID删除该班级下的员工数据
//     * @param deptId
//     */
//    @Delete("delete  from emp where dept_id = #{deptId}")
//    void deleteByDeptId(Integer deptId);
}

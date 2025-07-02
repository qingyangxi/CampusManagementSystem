package edu.webclass.system.mapper;

import edu.webclass.system.pojo.Emp;
import edu.webclass.system.pojo.Stu;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface StuMapper {

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
    //@Select("select * from emp")
    public List<Stu> list(Integer page, Integer pageSize, String name, String stuId, Short education, Integer classId);

    /**
     * 批量删除
     * @param ids
     */
    void delete(List<Integer> ids);
//
    /**
     * 新增员工
     * @param stu
     */
    @Insert("insert into student(id, name, stu_id, gender, telephone, education, class_id, indiscipline_time, indiscipline_points, update_time) " +
            " values(#{id},#{name},#{stuId},#{gender},#{telephone},#{education},#{classId},#{indisciplineTime},#{indisciplinePoints}, #{updateTime})")
    void insert(Stu stu);

//
    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    @Select("select * from student where id = #{id}")
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
    void dealP(Integer id, Integer score);
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
//     * 根据部门ID删除该部门下的员工数据
//     * @param classId
//     */
//    @Delete("delete from student where class_id = #{classId}")
//    void deleteByClassId(Integer classId);
}

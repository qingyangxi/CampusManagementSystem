package edu.webclass.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stu {
    private Integer id; //ID
    private String name; //用户名
    private String stuId; //密码
    private Short gender; //性别 , 1 男, 2 女
    private String telephone; //图像url
    private Short education; //职位 , 1 班主任 , 2 讲师 , 3 学工主管 , 4 教研主管 , 5 咨询师
    private Integer classId; //部门ID
    private Integer indisciplineTime;
    private Integer indisciplinePoints;
    private LocalDateTime updateTime;
}

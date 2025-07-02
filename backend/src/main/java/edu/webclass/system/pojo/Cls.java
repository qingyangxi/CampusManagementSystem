package edu.webclass.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cls {
    private Integer id; //ID
    private String name; //姓名
    private String classroom; // 教室
    private LocalDateTime startTime; //创建时间
    private LocalDateTime endTime; //修改时间
    private Integer teacherId; //教师ID
}

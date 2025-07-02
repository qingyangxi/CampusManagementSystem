create table websystem.class
(
    id         int auto_increment comment '主键，自增ID'
        primary key,
    name       varchar(30) not null comment '班级名称，必填，长度4-30，唯一，限制汉字、数字、字母',
    classroom  varchar(20) null comment '班级教室，选填，长度1-20，限制汉字、数字、字母',
    start_time date        not null comment '开课时间，必填，格式如2025-01-01',
    end_time   date        not null comment '结课时间，必填，格式如2025-01-01',
    teacher_id varchar(50) not null comment '班主任，必填，下拉框选择，企业员工展示列表',
    constraint class_name
        unique (name),
    constraint chk_class_name_length
        check (char_length(`name`) between 4 and 30),
    constraint chk_classroom_length
        check ((`classroom` is null) or (char_length(`classroom`) between 1 and 20))
)
    comment '班级信息表';

create table websystem.dept
(
    id          int unsigned auto_increment comment '主键ID'
        primary key,
    name        varchar(10) not null comment '部门名称',
    create_time datetime    not null comment '创建时间',
    update_time datetime    not null comment '修改时间',
    constraint name
        unique (name)
)
    comment '部门表';

create table websystem.emp
(
    id          int unsigned auto_increment comment 'ID'
        primary key,
    username    varchar(20)                  not null,
    password    varchar(32) default '123456' null comment '密码',
    name        varchar(10)                  not null,
    gender      tinyint unsigned             not null,
    image       varchar(300)                 null comment '图像',
    job         tinyint unsigned             null comment '职位, 说明: 1 班主任,2 讲师, 3 学工主管, 4 教研主管, 5 咨询师',
    entrydate   date                         null comment '入职时间',
    dept_id     int unsigned                 null comment '部门ID',
    create_time datetime                     not null comment '创建时间',
    update_time datetime                     not null comment '修改时间',
    constraint username
        unique (username),
    constraint emp_dep_foreign
        foreign key (dept_id) references websystem.dept (id)
            on delete set null,
    constraint chk_emp_gender
        check (`gender` in (1, 2)),
    constraint chk_emp_job
        check (`job` in (1, 2, 3, 4)),
    constraint chk_emp_name_length
        check (char_length(`name`) between 2 and 10),
    constraint chk_emp_username_length
        check (char_length(`username`) between 2 and 20)
)
    comment '员工表';

create table websystem.student
(
    id                  int auto_increment comment '主键，自增ID'
        primary key,
    name                varchar(10)                  not null comment '学员姓名，必填，长度2-10，限制汉字、数字、字母',
    stu_id              char(10)                     not null comment '学号，必填，10位数字或字母，唯一',
    gender              tinyint unsigned             null,
    telephone           char(11)                     not null comment '手机号，必填，11位数字，唯一',
    education           tinyint unsigned             null,
    class_id            int                          null comment '所属班级，必填，关联 class 表的主键',
    indiscipline_time   tinyint unsigned default '0' null comment '违纪次数',
    indiscipline_points int unsigned     default '0' null comment '违纪扣分',
    update_time         datetime                     null comment '上次更新时间',
    constraint phone
        unique (telephone),
    constraint student_number
        unique (stu_id),
    constraint student_ibfk_1
        foreign key (class_id) references websystem.class (id)
            on delete set null,
    constraint chk_education_value
        check ((`education` in (1, 2, 3, 4, 5, 6)) or (`education` is null)),
    constraint chk_gender_value
        check (`gender` in (1, 2)),
    constraint chk_name_length
        check (char_length(`name`) between 2 and 10),
    constraint chk_stu_id_length
        check (char_length(`stu_id`) = 10),
    constraint chk_telephone_format
        check (regexp_like(telephone, '^[0-9]{11}$'))

)
comment ' 学员信息表 ';



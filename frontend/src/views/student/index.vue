<template>
  <div class="app-container">

    <!--搜索表单-->
    <el-form :inline="true" :model="searchStu" class="demo-form-inline">
      <el-form-item label="学员姓名">
        <el-input
          v-model="searchStu.name"
          placeholder="请输入员工姓名"
        ></el-input>
      </el-form-item>
      <el-form-item label="学号">
        <el-input
          v-model="searchStu.stuId"
          placeholder="请输入学生学号"
        ></el-input>
      </el-form-item>
      <el-form-item label="最高学历">
        <el-select v-model="searchStu.education" placeholder="请选择">
          <el-option label="初中" value="1"></el-option>
          <el-option label="高中" value="2"></el-option>
          <el-option label="大专" value="3"></el-option>
          <el-option label="本科" value="4"></el-option>
          <el-option label="硕士" value="5"></el-option>
          <el-option label="博士" value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属班级">
        <el-select v-model="searchStu.classId" placeholder="请选择">
          <el-option
            v-for="item in classList.rows"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <!--按钮-->
    <el-row>
      <el-button type="danger" size="medium" @click="deleteByIds">- 批量删除</el-button>
      <el-button type="primary" size="medium" @click="dialogVisible = true; stu = { };" >+ 新增学员</el-button>
    </el-row>

    <!--添加数据对话框表单-->
    <el-dialog ref="form" title="编辑（新增）学员" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="stu" :rules="rules" label-width="80px" size="mini">

        <el-form-item label="姓名" prop="name">
          <el-input v-model="stu.name" placeholder="姓名应为2-10个汉字数字字母组合"></el-input>
        </el-form-item>

        <el-form-item label="学号" prop="stuId">
          <el-input v-model="stu.stuId" placeholder="学号应为10位字母数字组合"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-select v-model="stu.gender" placeholder="请选择" style="width:100%">
            <el-option
              v-for="item in genderList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="手机号" prop="telephone">
          <el-input v-model="stu.telephone" placeholder="电话号仅能为11位纯数字"></el-input>
        </el-form-item>

        <el-form-item label="最高学历" prop="education">
          <el-select v-model="stu.education" placeholder="请选择">
            <el-option label="初中" :value="1"></el-option>
            <el-option label="高中" :value="2"></el-option>
            <el-option label="大专" :value="3"></el-option>
            <el-option label="本科" :value="4"></el-option>
            <el-option label="硕士" :value="5"></el-option>
            <el-option label="博士" :value="6"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="所属班级" prop="classId">
          <el-select v-model="stu.classId" placeholder="请选择">
            <el-option
              v-for="item in classList.rows"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="add">提交</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <br>

    <!--添加违纪表单-->
    <el-dialog ref="form" title="学员违纪处理" :visible.sync="badVisible" width="30%">
      <el-form ref="form" label-width="80px" size="mini">
          <el-form-item label="违纪扣分">
            <el-input v-model="dealScore"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="dealPoint">提交</el-button>
            <el-button @click="badVisible = false">取消</el-button>
          </el-form-item>
      </el-form>

    </el-dialog>

    <br>
    <!--表格-->
    <template>
      <el-table v-if="Object.keys(classNameMap).length > 0" :data="tableData" style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"  align="center"></el-table-column>
        <el-table-column  prop="name"  label="姓名"  align="center"></el-table-column>
        <el-table-column  prop="stuId"  label="学号"  align="center"></el-table-column>
        <el-table-column align="center" label="班级">
          <template slot-scope="scope">
             <span style="margin-right: 10px">
              {{ classNameMap[scope.row.classId] }}
             </span>
          </template>
        </el-table-column>


<!--        <el-table-column prop="image" label="头像" align="center">-->
<!--          <template slot-scope="{ row }">-->
<!--            <el-image style="width: auto; height: 40px; border: none; cursor: pointer" :src="row.image"></el-image>-->
<!--          </template>-->
<!--        </el-table-column>-->

        <el-table-column align="center" label="性别">
          <template slot-scope="scope">
            <span style="margin-right: 10px">
            {{scope.row.gender == "1" ? "男" : "女"}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="telephone"  label="手机号"  align="center"></el-table-column>

        <el-table-column align="center" label="最高学历">
          <template slot-scope="scope">
            <span style="margin-right: 10px" v-if="scope.row.education == 1">初中</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 2">高中</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 3">大专</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 4">本科</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 5">硕士</span>
            <span style="margin-right: 10px" v-if="scope.row.education == 6">博士</span>
          </template>
        </el-table-column>

        <el-table-column  prop="indisciplineTime"  label="违纪次数"  align="center"></el-table-column>
        <el-table-column  prop="indisciplinePoints"  label="违纪扣分"  align="center"></el-table-column>
<!--        <el-table-column  prop="updateTime"  label="最后操作时间"  align="center"></el-table-column>-->

<!--        <el-table-column align="center" label="日职日期">-->
<!--          <template slot-scope="scope">-->
<!--            {{ scope.row.entrydate }}-->
<!--          </template>-->
<!--        </el-table-column>-->
        <el-table-column align="center" label="最后操作时间">
          <template slot-scope="scope">
            {{scope.row.updateTime ? scope.row.updateTime.replace('T',' '):''}}
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="240">
          <template slot-scope="scope">
            <div style="display: flex; justify-content: center; gap: 6px; flex-wrap: wrap;">
              <el-button type="primary" size="small" @click="update(scope.row.id)">编辑</el-button>
              <el-button type="warning" size="small" @click="badupdate(scope.row.id)">违纪</el-button>
              <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
            </div>
          </template>
        </el-table-column>

      </el-table>
    </template>

    <!--分页工具条-->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :background="background"
      :current-page="currentPage"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="5"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalCount">
    </el-pagination>
  </div>
</template>

<script>
import { page, add, update, deleteById, selectById, selectByIdCls, dealPoints } from '@/api/stu.js'
import { findAll } from "@/api/stu.js";
import { getToken } from '@/utils/auth';
import { findAllCls } from '@/api/stu.js'
import { className } from 'postcss-selector-parser'

export default {
  data() {
    return {
      background: true,
      // 每页显示的条数
      pageSize: 5,
      // 总记录数
      totalCount: 100,
      // 当前页码
      currentPage: 1,
      // 添加数据对话框是否展示的标记
      dialogVisible: false,
      badVisible: false,
      // 品牌模型数据
      searchStu: {
        name: "",
        stuId: "",
        education: "",
        classId: ""
      },
      classList: [],
      stu: {
        name: "",
        stuId: "",
        gender: "",
        telephone: "",
        education: "",
        classId: "",
        indisciplinePoints: "",
        indisciplineTime:"",
      },
      rules: {
        name: [
          { required: true, message: '请输入学员姓名', trigger: 'blur' },
          { min: 2, max: 10, message: '姓名长度应为2-10个字符', trigger: 'blur' },
          { pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/, message: '姓名只能包含汉字、字母、数字', trigger: 'blur' }
        ],
        stuId: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { pattern: /^[A-Za-z0-9]{10}$/, message: '学号必须是10位字母或数字', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        telephone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^[0-9]{11}$/, message: '手机号必须是11位纯数字', trigger: 'blur' }
        ],
        education: [
          // 最高学历是选填，所以一般不加required，只加数据校验在后端处理
        ],
        classId: [
          { required: true, message: '请选择所属班级', trigger: 'change' }
        ]
      },
      stuList: [],
      genderList: [{id: 1,name: "男"},{id: 2,name: "女"}],
      jobList: [
        {
          id: 1,
          name: "班主任",
        },
        {
          id: 2,
          name: "讲师",
        },
        {
          id: 3,
          name: "学工主管",
        },
        {
          id: 4,
          name: "教研主管",
        }
      ],

      beginTime: "",
      endTime: "",
      entrydate: "",

      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],
      // 表格数据
      tableData: [],
      token: {token: getToken()},
      tableClass: "",
      classNameMap: {},
      dealScore: 0,
      dealStu: "",
    };
  },

  mounted() {
    this.page(); //当页面加载完成后，发送异步请求，获取数据
    this.loadClassList();
    findAll().then((result) => {
      this.stuList = result.data.data;
    });
  },

  methods: {
    className,
    selectByIdCls,
    // 查询分页数据
    page() {
      page(
        this.searchStu.name,
        this.searchStu.stuId,
        this.searchStu.education,
        this.searchStu.classId,
        this.currentPage,
        this.pageSize
      ).then((res) => {
        this.totalCount = res.data.data.total;
        this.tableData = res.data.data.rows;
      });
    },

    // 复选框选中后执行的方法
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    // 查询方法
    onSubmit() {
      this.currentPage = 1;
      this.page();
    },

    clear() {
      this.searchStu = {
        name: "",
        stuId: "",
        education: "",
        classId: "",
      };
      this.page();
    },
    // 添加数据
    // 添加数据
    add() {
      this.loadClassList()
      this.$refs.form.validate((valid) => {
        if (valid) {
          let operator;

          if (this.stu.id) {
            // 修改
            operator = update(this.stu);
          } else {
            operator = add(this.stu);
          }

          operator.then((resp) => {
            if (resp.data.code == "1") {
              this.dialogVisible = false;
              this.page();
              this.$message({ message: "恭喜你，保存成功", type: "success" });
              // 这里重置 stu，最好保留结构，防止后续表单异常
              this.stu = {
                name: '',
                stuId: '',
                gender: '',
                telephone: '',
                education: '',
                classId: ''
              };
            } else {
              this.$message.error(resp.data.msg);
            }
          });

        } else {
          this.$message.error('请检查表单填写是否正确');
          return false;
        }
      });
    },

    update(id) {
      this.loadClassList()
      //1. 打开窗口
      this.dialogVisible = true;
      //2. 发送请求

      selectById(id).then((result) => {
        if (result.data.code == 1) {
          this.stu = result.data.data;
          this.stu;
        }
      });
    },

    loadClassList() {
      findAllCls().then((result) => {
        this.classList = result.data.data;
        // 重新生成 classNameMap
        this.classNameMap = {};
        this.classList.rows.forEach(item => {
          this.classNameMap[item.id] = item.name;
        });
      });
    },

    badupdate(id) {
      this.dealStu = id;
      this.badVisible = true;
    },


    //分页
    handleSizeChange(val) {
      // 重新设置每页显示的条数
      this.pageSize = val;
      this.page();
    },

    handleCurrentChange(val) {
      // 重新设置当前页码
      this.currentPage = val;
      this.page();
    },

    //删除员工信息
    deleteById(id) {
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //2. 发送AJAX请求
        deleteById(id).then((resp) => {
          if (resp.data.code == 1) {
            //删除成功
            this.$message.success("恭喜你，删除成功");
            this.page();
          } else {
            this.$message.error(resp.data.msg);
          }
        });
      }).catch(() => {
        //用户点击取消按钮
        this.$message.info("已取消删除");
      });
    },

    // 批量删除员工信息
    deleteByIds() {
      // 弹出确认提示框
      this.$confirm("此操作将删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        //用户点击确认按钮
        //1. 创建id数组, 从 this.multipleSelection 获取即可
        for (let i = 0; i < this.multipleSelection.length; i++) {
          this.selectedIds[i] = this.multipleSelection[i].id;
        }

        //2. 发送AJAX请求
        deleteById(this.selectedIds).then((resp) => {
          if (resp.data.code == "1") {
            //删除成功
            this.$message.success("恭喜你，删除成功");
            this.page();
          } else {
            this.$message.error(resp.data.msg);
          }
        });
      }).catch(() => {
        //用户点击取消按钮
        this.$message.info("已取消删除");
      });
    },

    //文件上传相关
    handleAvatarSuccess(res, file) {
      this.stu.image = res.data;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    getClassName(classId) {
      selectByIdCls(classId).then((result) => {
        if (result.data.code == 1) {
          this.tableClass = result.data.data.name;
        }
      })
      return this.tableClass;
    },
    dealPoint() {
      if (!this.dealScore || isNaN(this.dealScore) || this.dealScore <= 0) {
        this.$message.error("请输入有效的扣分值！");
        return;
      }

      this.$confirm("此操作将扣除该学员违纪分数, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 将 dealScore 包装成对象
        const payload = { score: this.dealScore };

        dealPoints(this.dealStu, payload).then((resp) => {
          if (resp.data.code == 1) {
            this.$message.success("扣分成功");
            this.badVisible = false; // 关闭对话框
            this.dealScore = 0; // 清空输入框
            this.page(); // 刷新表格数据
          } else {
            this.$message.error(resp.data.msg);
          }
        });
      }).catch(() => {
        this.$message.info("已取消操作");
      });
    }
  },


  watch: {
    entrydate(val) {
      if (val && val.length >= 2) {
        this.beginTime = val[0];
        this.endTime = val[1];
      } else {
        this.beginTime = "";
        this.endTime = "";
      }
    },
  },
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}
</style>

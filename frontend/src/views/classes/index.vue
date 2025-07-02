<template>
  <div class="app-container">

    <!--搜索表单-->
    <el-form :inline="true" :model="searchClass" class="demo-form-inline">
      <el-form-item label="班级名称">
        <el-input
          v-model="searchClass.name"
          placeholder="请输入班级名称"
        ></el-input>
      </el-form-item>

      <el-form-item label="结课时间">
        <el-date-picker
          v-model="entrydate"
          clearable
          value-format="yyyy-MM-dd"
          type="daterange"
          placeholder="选择时间"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          style="width: 400px; margin-left: 20px"
        ></el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
         <el-button type="info" @click="clear">清空</el-button>
      </el-form-item>
    </el-form>

    <!--按钮-->
    <el-row>
      <el-button type="primary" size="medium" @click="dialogVisible = true; cls = {};" >+ 新增</el-button>
    </el-row>

    <!--添加数据对话框表单-->
    <el-dialog ref="form" title="修改/新增班级" :visible.sync="dialogVisible" width="30%">
      <el-form ref="form" :model="cls" :rules="rules" label-width="80px" size="mini">
        <el-form-item label="班级名称" prop="name">
          <el-input v-model="cls.name" placeholder="长度限制4-30"></el-input>
        </el-form-item>
        <el-form-item label="班级教室">
          <el-input v-model="cls.classroom"></el-input>
        </el-form-item>
        <el-form-item label="开课时间" prop="startTime">
          <el-date-picker
            v-model="cls.startTime"
            clearable
            type="date"
            placeholder="选择日期"
            size="small"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结课时间" prop="endTime">
          <el-date-picker
              v-model="cls.endTime"
              clearable
              type="date"
              placeholder="选择日期"
              size="small"
              style="width:100%"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="班主任" prop="teacherId">
          <el-select v-model="cls.teacherId" placeholder="请选择" style="width:100%">
            <el-option
              v-for="item in empList.filter(emp => emp.job === 1)"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="add">保存</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <br>
    <!--表格-->
    <template>
      <el-table :data="tableData" style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="index" width="100" label="序号" header-align="center" align="center"> </el-table-column>
        <el-table-column  prop="name"  label="班级名称"  align="center"></el-table-column>
        <el-table-column  prop="classroom" label="班级教室" align="center"></el-table-column>

        <el-table-column align="center" label="开课日期">
          <template slot-scope="scope">
            {{scope.row.startTime ? formatDate(scope.row.startTime) : '-'}}
          </template>
        </el-table-column>
        <el-table-column align="center" label="结课时间">
          <template slot-scope="scope">
            {{scope.row.endTime ? formatDate(scope.row.endTime) : '-'}}
          </template>
        </el-table-column>

        <el-table-column align="center" label="班主任">
          <template slot-scope="scope">
            <span v-if="scope.row.teacherId">{{ getTeacherName(scope.row.teacherId) }}</span>
            <span v-else>-</span>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="update(scope.row.id)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row.id)">删除</el-button>
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
import { page, add, update, deleteById, selectById, selectByIdEmp, findAll, findAllEmp} from "@/api/class.js";
import { getToken } from '@/utils/auth';


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
      // 品牌模型数据
      searchClass: {
        name: "",
      },
      cls: {
        name: '',
        classroom: '',
        startTime: '',
        endTime: '',
        teacherId: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入班级名称', trigger: 'blur' },
          { min: 4, max: 30, message: '班级名称非法', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '请选择开课时间', trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '请选择结课时间', trigger: 'change' }
        ],
        teacherId: [
          { required: true, message: '请选择班主任', trigger: 'change' }
        ]
      },
      classList: [],
      empList: [],
      startTime: "",
      endTime: "",
      entrydate: "",

      // 被选中的id数组
      selectedIds: [],
      // 复选框选中数据集合
      multipleSelection: [],
      // 表格数据
      tableData: [],
      token: {token: getToken()},
      // 教师缓存
      teacherCache: {}
    };
  },

  mounted() {
    this.page(); // 当页面加载完成后，发送异步请求，获取数据
    findAll().then((result) => {
      this.classList = result.data.data;
    }).catch(() => {
      this.$message.error("获取班级列表失败");
    });

    findAllEmp().then((result) => {
      if (result.data.code === 1 && Array.isArray(result.data.data.rows)) {
        this.empList = result.data.data.rows;
      } else {
        this.$message.error("获取教师列表失败");
      }
    }).catch(() => {
      this.$message.error("获取教师列表失败");
    });
  },

  methods: {
    // 查询分页数据
    page() {
      page(
        this.searchClass.name,
        this.startTime,
        this.endTime,
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

    clear(){
      this.searchClass = {name: ""};
      this.startTime = "",
      this.endTime = "";
      this.entrydate = "";
      this.page();
    },
    // 添加数据
    add() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          let operator;

          if (this.cls.id) {
            // 修改
            operator = update(this.cls);
          } else {
            operator = add(this.cls);
          }

          operator.then((resp) => {
            if (resp.data.code == "1") {
              this.dialogVisible = false;
              this.page();
              this.$message({ message: "恭喜你，保存成功", type: "success" });
              this.cls = {}; // 清空表单
            } else {
              this.$message.error(resp.data.msg);
            }
          });

        } else {
          this.$message.error('表单校验未通过，请检查输入内容');
          return false;
        }
      });
    },


    update(id) {
      // 打开窗口
      this.dialogVisible = true;

      // 发送请求获取班级信息
      selectById(id).then((result) => {
        if (result.data.code === 1) {
          // 确保返回的数据结构正确
          this.cls = result.data.data; // 如果返回的是单个对象
        } else {
          this.$message.error("获取班级信息失败");
        }
      }).catch(() => {
        this.$message.error("请求失败，请稍后重试");
      });
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
    deleteById(id){
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

    getTeacherName(teacherId) {
      if (!this.teacherCache) {
        this.teacherCache = {};
      }

      if (this.teacherCache[teacherId]) {
        return this.teacherCache[teacherId];
      } else {
        // 标记为加载中
        this.$set(this.teacherCache, teacherId, '加载中...');

        // 发起请求获取教师信息
        selectByIdEmp(teacherId).then(res => {
          console.log(res.data)
          if (res.data.code === 1 && res.data.data) {
            // 使用Vue的响应式更新，替代forceUpdate
            this.$set(this.teacherCache, teacherId, res.data.data.name);
          } else {
            this.$set(this.teacherCache, teacherId, '未知');
          }
        }).catch(() => {
          this.$set(this.teacherCache, teacherId, '获取失败');
        });

        return this.teacherCache[teacherId];
      }
    },

    formatDate(dateString) {
      if (!dateString) return '-';
      const date = new Date(dateString);
      return date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0');
    },
  },

  watch: {
    entrydate(val) {
      if (val && val.length >= 2) {
        this.startTime = val[0];
        this.endTime = val[1];
      } else {
        this.startTime = "";
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

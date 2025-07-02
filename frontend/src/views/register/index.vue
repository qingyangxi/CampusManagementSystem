<template>
  <div class="register-container">
    <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">员工注册</h3>
      </div>

      <el-form-item prop="image" class="avatar-item">
        <el-upload
            class="avatar-uploader"
            action="/api/upload"
            :headers="token"
            name="image"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <img v-if="registerForm.image" :src="registerForm.image" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          <div class="upload-hint">头像</div>
        </el-upload>
      </el-form-item>

      <el-form-item prop="username">
        <el-input
          ref="username"
          v-model="registerForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          :key="passwordType"
          ref="password"
          v-model="registerForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <el-form-item prop="name">
        <el-input
          v-model="registerForm.name"
          placeholder="员工姓名"
          name="name"
          tabindex="3"
        />
      </el-form-item>

      <el-form-item prop="gender">
        <el-select v-model="registerForm.gender" placeholder="请选择性别" style="width:100%" tabindex="4">
          <el-option label="男" :value="1" />
          <el-option label="女" :value="2" />
        </el-select>
      </el-form-item>

      <el-form-item prop="job">
        <el-select v-model="registerForm.job" placeholder="请选择职位" style="width:100%" tabindex="5">
          <el-option
            v-for="item in jobList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item prop="entrydate">
        <el-date-picker
            v-model="registerForm.entrydate"
            clearable
            type="date"
            placeholder="入职日期"
            size="small"
            style="width:100%"
        ></el-date-picker>
      </el-form-item>

      <el-form-item prop="deptId">
        <el-select v-model="registerForm.deptId" placeholder="请选择部门" style="width:100%" tabindex="6">
          <el-option
            v-for="item in deptList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button :loading="loading" type="primary" @click.native.prevent="handleRegister">注册</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { register } from '@/api/user'
import { getToken } from '@/utils/auth'
import { findAll } from '@/api/dept'

export default {
  name: 'Register',
  data() {
    // 用户名校验规则
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }

    // 密码校验规则
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码长度至少为6位'))
      } else {
        callback()
      }
    }


    return {
      registerForm: {
        username: '',
        password: '',
        name: '',
        gender: '',
        image: '',
        job: '',
        deptId: '',
        entrydate: '',
      },
      registerRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }],
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
        job: [{ required: true, message: '请选择职位', trigger: 'change' }],
        deptId: [{ required: true, message: '请选择部门', trigger: 'change' }],
        entrydate: [{ required: true, message: '请选择入职日期', trigger: 'change' }],
      },
      loading: false,
      passwordType: 'password',
      token: {
        'Authorization': getToken()
      },
      jobList: [
        { id: 1, name: "班主任" },
        { id: 2, name: "讲师" },
        { id: 3, name: "学工主管" },
        { id: 4, name: "教研主管" }
      ],
      deptList: []
    }
  },
  created() {
    // 获取部门列表
    this.getDepartmentList()
  },
  methods: {
    // 显示/隐藏密码
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },

    // 获取部门列表
    getDepartmentList() {
      findAll().then(response => {
        this.deptList = response.data.data
      }).catch(() => {
        this.$message.error("获取部门列表失败")
      })
    },

    // 头像上传成功
    handleAvatarSuccess(response) {
      if (response.code === 1) {
        this.registerForm.image = response.data
      } else {
        this.$message.error(response.msg || '上传失败')
      }
    },

    // 头像上传前校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    // 处理注册
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = true
          register(this.registerForm).then(response => {
            if (response.data.code === 1) {
              this.$message.success('注册成功，请登录')
              this.$router.push('/login')
            } else {
              this.$message.error(response.data.msg || '注册失败')
            }
            this.loading = false
          }).catch(() => {
            this.$message.error('注册失败，请检查网络或稍后重试')
            this.loading = false
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
$bg:#283443;
$light_gray:#fff;
$cursor: #5c7c9c;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .register-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.register-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $cursor;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }



  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }

  .el-date-editor input{
    padding-left: 30px;
  }

}
</style>

<style lang="scss" scoped>
$bgStart: #dce6f1;   // 雾蓝色起点
$bgEnd: #f8fbff;     // 浅白蓝色终点
$primary: #5c7c9c;   // 深一点的雾蓝
$light: rgba(255, 255, 255, 0.8); // 登录框半透明背景
$dark: #2c3e50;
$input-border: #b0c4de;
$cursor: #5c7c9c;

.register-container {
  padding-top: 60px;
  padding-bottom: 60px;
  min-height: 100vh;
  width: 100%;
  background: linear-gradient(135deg, $bgStart 0%, $bgEnd 100%); /* 雾蓝渐变背景 */
  overflow: visible;
  display: flex;
  align-items: center;
  justify-content: center;
  animation: fadeIn 1s ease; /* 整体页面淡入 */

  .register-form {
    width: 600px;
    background: $light;
    padding: 20px 50px 20px;
    border-radius: 16px;
    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(10px); /* 柔光效果 */
    animation: slideFadeIn 1s ease; /* 登录框出现动效 */
    position: relative;

    .login-link {
      position: absolute;
      bottom: 62px;
      right: 33px;
      font-size: 14px;
      color: $primary;
      cursor: pointer;
      user-select: none;

      &:hover {
        text-decoration: underline;
      }
    }

    .avatar-item {
      display: flex;
      justify-content: center;
      background: transparent !important;
      border: none !important;
      margin-bottom: 15px;
    }

    .avatar-uploader {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      width: 100px;
      height: 100px;
      display: flex;
      justify-content: center;
      align-items: center;

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

      .upload-hint {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: rgba(0,0,0,0.5);
        color: white;
        text-align: center;
        padding: 2px 0;
        font-size: 12px;
      }
    }

    .el-form-item {
      border-radius: 10px;
      background: white;
      margin-bottom: 25px;
      overflow: visible;

      .el-select .el-input__suffix {
        right: 20px;
      }

      .el-input__inner {
        border: 0px;
        padding-left: 15px;
        color: $dark;
        height: 45px;
        font-size: 16px;
        letter-spacing: 1px;
      }

      .el-button {
        border: 0px;
        background-color: $primary;
        border-color: $primary;
        width: 100%;
        height: 45px;
        font-size: 16px;
        letter-spacing: 1px;
        border-radius: 0px;
        margin-top: 0px;

        &:hover {
          background-color: darken($primary, 5%);
          border-color: darken($primary, 5%);
        }
      }
    }
  }

  .title-container {
    text-align: center;
    margin-bottom: 40px;

    .title {
      font-size: 28px;
      font-weight: bold;
      color: $primary;
      letter-spacing: 2px;
      font-family: 'PingFang SC', 'Microsoft YaHei', sans-serif;
    }
  }

  .svg-container {
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
    color: $primary;
    font-size: 18px;
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 18px;
    color: $primary;
    cursor: pointer;
  }
}

/* 页面整体淡入动画 */
@keyframes fadeIn {
  0% { opacity: 0; }
  100% { opacity: 1; }
}

/* 登录框滑入+淡入动画 */
@keyframes slideFadeIn {
  0% {
    opacity: 0;
    transform: translateY(-20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>

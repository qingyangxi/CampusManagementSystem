<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <h3 class="title">培训管理系统</h3>
      </div>

      <el-form-item prop="username">
<!--        <span class="svg-container">-->
<!--          <svg-icon icon-class="user" />-->
<!--        </span>-->
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="请输入用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
<!--        <span class="svg-container">-->
<!--          <svg-icon icon-class="password" />-->
<!--        </span>-->
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="请输入密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>

      <router-link to="/register" class="register-link">注册员工</router-link>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click="handleLogin">登录</el-button>

    </el-form>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { login } from '@/api/user'
import { setToken } from '@/utils/auth'
export default {
  name: 'Login',
  data() {

    //用户名校验规则
    const validateUsername = (rule, value, callback) => {
      if (!validUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }

    //用户名校验规则
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('密码长度至少为6位'))
      } else {
        callback()
      }
    }

    //数据模型
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },

  methods: {
    //展示密码
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

    //登录方法
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true

          //调用登录后端接口
          login(this.loginForm).then((result) => {
            console.log(result)
            if (result.data.code == 1) {
              this.$message.success("登录成功");
              setToken(result.data.data);
              console.log('login success');
              this.$router.push('/');
            } else {
              this.$message.error(result.data.msg || "登录失败");
              this.loading = false
            }
          }).catch(() => {
            this.$message.error("登录失败，请检查网络或稍后重试");
            this.loading = false;
          });
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#5c7c9c;
$cursor: #5c7c9c;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
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
      color: $light_gray;
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

.login-container {
  min-height: 100vh;
  width: 100%;
  background: linear-gradient(135deg, $bgStart 0%, $bgEnd 100%); /* 雾蓝渐变背景 */
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  animation: fadeIn 1s ease; /* 整体页面淡入 */

  .login-form {
    width: 400px;
    background: $light;
    padding: 40px 30px 30px;
    border-radius: 16px;
    box-shadow: 0 8px 20px rgba(0,0,0,0.1);
    backdrop-filter: blur(10px); /* 柔光效果 */
    animation: slideFadeIn 1s ease; /* 登录框出现动效 */
  }

  .register-link {
    position: absolute;
    bottom: 117px; /* 调整为密码框的右下角 */
    right: 33px;
    color: $primary; /* 蓝色 */
    font-size: 14px;
    cursor: pointer;
    text-decoration: none;
    font-weight: normal;

    &:hover {
      text-decoration: underline;
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

  .el-form-item {
    border: 1px solid $input-border;
    border-radius: 10px;
    background: white;
    margin-bottom: 20px;
    overflow: visible;
    position: relative;

    .el-input__inner {
      border: none;
      padding-left: 40px;
      color: $dark;
      height: 45px;
      font-size: 16px;
      letter-spacing: 1px;
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

  .el-button {
    background-color: $primary;
    border-color: $primary;
    width: 100%;
    height: 45px;
    font-size: 16px;
    letter-spacing: 1px;
    border-radius: 10px;
    margin-top: 20px;

    &:hover {
      background-color: darken($primary, 5%);
      border-color: darken($primary, 5%);
    }
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

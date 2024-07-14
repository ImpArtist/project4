<template>
  <div class="background-container">
    <div class="centered-text-title">
      <strong style="font-family: 楷体,serif; font-size: 5em;">数据中台系统</strong>
    </div>
    <div class="centered-text-login">
      <strong style="font-family: 楷体,serif; font-size: 3.5em;">登录</strong>
    </div>
    <div  class="login-container">
      <el-row style="margin-bottom: 10px;">
        <el-col :span="6">
          <strong style="font-family: 楷体,serif; font-size: 1.5em;">账号：</strong>
        </el-col>
        <el-col :span="18">
          <el-input
              @blur="accountOnBlur"
              placeholder="请输入用户账号"
              v-model="username"
              suffix-icon="el-icon-s-promotion"
              style="width: 260px;">
          </el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <strong style="font-family: 楷体,serif; font-size: 1.5em;">密码：</strong>
        </el-col>
        <el-col :span="18">
          <el-input
              type="password"
              placeholder="请输入密码"
              v-model="password"
              style="width: 260px;">
          </el-input>
        </el-col>
      </el-row>
    </div>
    <div class="login-button"  style="margin-top: 20px">
      <el-row>
        <el-button type="primary" round size="medium" @click="login" style="width: 150px;">   登录   </el-button>
      </el-row>
    </div>
  </div>
</template>

<style>
.background-container {
  position: relative;
  width: 100%;
  height: 100vh;
  background-image: linear-gradient(to top, rgba(255, 255, 255, 0), rgba(200, 200, 200, 1)), url('@/assets/images/xx.jpg');
  background-size: cover;
  background-position: center;
}

.centered-text-title {
  position: absolute;
  top: 15%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.centered-text-login {
  position: absolute;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.login-container {
  position: absolute;
  top: 43%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
.login-button {
  position: absolute;
  top: 55%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}
</style>

<script>
import axios from "axios";

export default {
  name: "welcome",
  data() {
    return {
      username: "",
      password: ""
      };
    },
    methods: {
      accountOnBlur() {
        console.log("accountOnBlur");
        },
      login() {
        if (this.username.trim() === "") {
          this.$message({
            message: "请输入用户名",
            type: "warning"
          });
          return;
        }
        if (this.password.trim() === "") {
          this.$message({
            message: "请输入密码",
            type: "warning"
          });
          return;
        }

        axios.post("/api/login", {
          username: this.username,
          password: this.password
        }).then(reponse => {
          if (reponse.data.code === 200) {
            this.$message({
              message: "登录成功",
              type: "success"
            });
            this.$router.push({
              path: "/menus"
            });
          } else {
            this.$message({
              message: "登录失败",
              type: "error"
            });
          }
        })
      }
    }
};
</script>

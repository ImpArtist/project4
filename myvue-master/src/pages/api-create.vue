<template>
  <div>
    <el-container>
      <!--      边栏侧-->
      <el-aside :width = "asideWidth" style=" min-height: 100vh; background-color: #001529;">
        <div style="height: 60px; line-height: 60px; display: flex;
        color: whitesmoke; align-items: center; justify-content: center;">
          <span style="font-size: 20px; font-weight: bold;">
            学院数据平台
          </span>


        </div>
        <el-menu router background-color="#001529" text-color="rgba(255, 255, 255, 0.6)" active-text-color="#ccddcc"
                 style="border:none;"  :default-active = "$route.path">
          <el-menu-item index="/menus">
            <i class="el-icon-s-platform"></i>
            <span slot="title">工作台</span>
          </el-menu-item>
          <el-menu-item index="/data-search">
            <i class="el-icon-s-data"></i>
            <span slot="title">数据查询与分析</span>
          </el-menu-item>
          <el-menu-item index="/chart-page">
            <i class="el-icon-data-line"></i>
            <span slot="title">报表查询</span>
          </el-menu-item>
          <el-submenu  open index="['/api-use-statistics', '/api-create', '/api-detail']">
            <template #title>
              <i class="el-icon-files"></i>
              <span>API管理</span>
            </template>
            <el-menu-item index="/api-use-statistics">API使用情况</el-menu-item>
            <el-menu-item index ="/api-create">API创建</el-menu-item>
            <el-menu-item index="/api-detail">API详细信息查询</el-menu-item>
          </el-submenu>
          <el-menu-item index="/database/manage">
            <i class="el-icon-coin"></i>
            <span slot="title">统一库管理</span>
          </el-menu-item>
          <el-menu-item index="/ability-prediction">
            <i class="el-icon-s-opportunity"></i>
            <span slot="title">学生学业能力维度观测</span>
          </el-menu-item>
        </el-menu>

      </el-aside>
      <el-container>
        <!--        头部-->
        <el-header>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item>首页</el-breadcrumb-item>
            <el-breadcrumb-item>API管理</el-breadcrumb-item>
            <el-breadcrumb-item>API创建</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0;  display: flex; align-items: center; justify-content: flex-end;">
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor:default" >
                <span style="margin-right: 5px;">小猫咪</span>
                <i class="el-icon-user-solid"></i>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

          </div>
        </el-header>
        <!--        主体-->
        <el-main>
          <el-form ref="form" :model="apiForm" label-width="30%">
            <el-form-item label="API名称:">
              <el-input style="margin-left: 20px; width: 500px;" v-model="apiForm.name"
                        placeholder="请输入简洁、清晰的API名称"
              @blur="checkAPIName"></el-input>
              <i v-if="!APINameExisted && APIEverChecked" class="el-icon-check" style="color: green; font-size: 20px;"></i>
              <i v-if="APINameExisted && APIEverChecked" class="el-icon-close" style="color: red; font-size: 14px;">{{ APIWrongReason }}</i>
            </el-form-item>
            <el-form-item label="API描述:">
              <el-input type="textarea" v-model="apiForm.desc" :rows="6"
                        placeholder="请输入API的描述，用于详细说明API的功能、参数、返回结果等信息。这个描述可以帮助其他开发者理解和正确使用API。"
                        style="margin-left: 20px; width: 500px; height: 140px;"></el-input>
            </el-form-item>
            <el-form-item label="SQL语言:">
              <el-input type="textarea" v-model="apiForm.sql" :rows="6"
                        placeholder="请输入具体的SQL语言,对数据库的操作包括查看，返回修改数据等"
                        @blur="checkSQL"
                        style="margin-left: 20px; width: 500px; height: 140px;"></el-input>
              <i v-if="SQLLegal && SQLEverChecked " class="el-icon-check" style="color: green; font-size: 20px;"></i>
              <i v-if="!SQLLegal && SQLEverChecked " class="el-icon-close" style="color: red; font-size: 14px;">您输入的SQL语言不合法</i>
            </el-form-item>

            <el-form-item label="数据库类型:">
              <el-select v-model="apiForm.databaseType" placeholder="请选择数据库类型" style="margin-left: 20px">
                  <el-option
                      v-for="item in databaseTypeList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="超时终止（可选）:">
              <el-input-number style="margin-left: 20px;" v-model="apiForm.timeout"
                               :min="0" :max="300" :step="1" step-strictly></el-input-number> ms
            </el-form-item>
            <el-form-item label="业务用户:">
              <el-select style="margin-left: 20px" v-model="apiForm.user"  placeholder="请选择">
                <el-option
                    v-for="item in userList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item style="margin-left: 15% ">
              <el-button type="primary" @click="onSubmit">立即创建</el-button>
              <el-button type="danger" @click="Reset">取消</el-button>
            </el-form-item>
          </el-form>
        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "api-create-page",
  data(){
    return{
      APIWrongReason: '',
      APINameExisted: false,
      SQLLegal: false,
      APIEverChecked: false,
      SQLEverChecked: false,
      isCollapse:false,
      asideWidth: '200px',
      apiForm: {
        sql: '',
        name: '',
        databaseType: '',
        timeout: 0,
        user: '',
        desc: ''
      },
      userList: [
        {
          value: '1',
          label: '张三'
        },
        {
          value: '2',
          label: '李四'
        },
        {
          value: '3',
          label: '王五'
        },
        {
          value: '4',
          label: '赵六'
        }
      ],
      user: [],
      databaseTypeList: [
        {
          value: 'mysql',
          label: 'mysql'
        }]
    }
  },
  methods:{
    onSubmit() {
      if(this.APINameExisted){
        this.$message.error('API名称不合法，请检查您的输入是否合法')
        return;
      }
      if(!this.SQLLegal){
        this.$message.error('SQL语言不合法，请检查您的输入是否合法')
        return;
      }
      if(this.apiForm.databaseType === ''){
        this.$message.error('请选择数据库类型')
        return;
      }
      let judge = false;
      let url = '';
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/create',
        data: {
          name: this.apiForm.name,
          info: this.apiForm.desc,
          command: this.apiForm.sql,
          business: this.apiForm.user
        }
      }).then(response => {
        judge = response.data.judge
        url = response.data.url
        if(judge){
          this.$alert(`您创建的API的URL为${url}，详情请查看“API详细信息查询”页面`, '创建API成功', {
            confirmButtonText: '确定',
            center: true,
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${ action }`
              });
            }
          });
          this.Reset()
        }
        else{
          this.$alert('创建API失败，请检查您的输入是否合法', '创建API失败', {
            confirmButtonText: '确定',
            center: true,
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${ action }`
                });
            }
          });
        }
      }).catch(error => {
        console.log(error);
      })

    },
    checkAPIName(){
      this.APIEverChecked = true;
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/checkname',
        data: {
          name: this.apiForm.name
        }
      }).then(response => {
        this.APINameExisted = !(response.data.judgeName && response.data.judgeSpace)
        if(!response.data.judgeName){
          this.APIWrongReason = 'API名称不合法'
        }
        if(!response.data.judgeSpace){
          this.APIWrongReason = 'API名称不能包含空格'
        }
        if(this.apiForm.name.length === 0){
          this.APIWrongReason = 'API名称不能为空'
        }
      }).catch(error => {
        console.log(error);
      })
    },
    checkSQL(){
      this.SQLEverChecked = true;
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/checksql',
        data: {
          sql: this.apiForm.sql
        }
      }).then(response => {
        this.SQLLegal = response.data
      }).catch(error => {
        console.log(error);
      })
    },
    Reset(){
      this.apiForm.sql = '';
      this.apiForm.name = '';
      this.apiForm.databaseType = '';
      this.apiForm.url = '';
      this.apiForm.authentication = '';
      this.apiForm.timeout = 0;
      this.apiForm.user = [];
      this.apiForm.desc = '';
      this.APIEverChecked = false;
      this.SQLEverChecked = false;
    }
  }
}

</script>

<style>

.el-menu--inline .el-menu-item {
  background-color: #000c17  !important;
}
.el-menu-item:hover, .el-submenu__title:hover{
  color: #eeffee !important;
}
.el-submenu__title{
  margin: 4px;
}
.el-menu-item.is-active {
  background-color: #40a9ff !important;
  border-radius: 7px;
  margin: 4px;
}
.el-menu-item{
  margin: 4px;
}
.el-menu--inline{
  background-color: #000c17 !important;
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}

</style>
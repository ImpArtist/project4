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
                 style="border:none;"  :default-active="activeMenu">
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
          <el-submenu open index="['/api-use-statistics', '/api-create', '/api-detail']">
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
            <el-breadcrumb-item>统一库管理</el-breadcrumb-item>
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
          <div style="margin-top: 10px">
            <el-breadcrumb separator-class="el-icon-arrow-right" style="font-family: 微软雅黑;">
              <el-breadcrumb-item style="font-size: 20px;">统一库管理</el-breadcrumb-item>
              <el-breadcrumb-item :to="{ path: '/database/manage' }" style="font-size: 20px;">数据库</el-breadcrumb-item>
              <el-breadcrumb-item style="font-size: 20px;"><span style="font-weight: bold; color: #40a9ff">数据库表</span></el-breadcrumb-item>
            </el-breadcrumb>
          </div>

          <div style="margin-top: 20px">
            <el-button @click="dialogFormVisible = true"  round class="custom-button">新建数据表</el-button>
          </div>

          <el-dialog title="新建数据表" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="数据表名" :label-width="formLabelWidth">
                <el-input v-model="form.Name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="数据表中文名" :label-width="formLabelWidth">
                <el-input v-model="form.ChineseName" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="CreateTable">确 定</el-button>
            </div>
          </el-dialog>

          <div class="table-square-container">
            <!-- 使用 v-for 指令动态生成小方块 -->
            <div class="table-square" v-for="(table, index) in TableList" :key="index" @click="TableDetail(table.tableName)">
              <div class="table-info">
                <div><strong>表格名称:</strong> {{ table.tableName }}</div>
                <div><strong>数据库类型:</strong> {{ table.dataBase }}</div>
                <div><strong>创建日期:</strong> {{ table.createTime }}</div>
              </div>
            </div>
          </div>

        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "menu-page",
  data(){
    return{
      isCollapse:false,
      asideWidth: '200px',
      activeMenu: '/database/manage',
      TableList:["api",
        "api_attribute",
        "api_record",
        "stu_info",
        "stu_info_attribute",
        "table_info"],
      dialogFormVisible: false,
      form: {
        Name: '',
        ChineseName:'',
      },
      formLabelWidth: '120px'
    }
  },
  methods:{
    TableDetail(TableName){
      this.$router.push({ name: 'database-table-manage-index', params: { TableName } });
    },
    CreateTable(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/stuAbility/createChart2',
        data: {
          dimension:this.dimension,
          stuNumber:this.StudentID,
        }
      }).then(response => {
        console.log(response.data)
        this.bar_option.series = response.data.series;
        this.bar_option.xAxis.data = response.data.xAxis;
        this.initializeBarChart();
      }).catch(error => {
        console.log(error);
      })
    },



  },
  mounted() {
    axios.post('http://localhost:10010/DbManage/getTables')
        .then(response => {
          this.TableList = response.data

        })
        .catch(error => {
          console.log(error)
        })
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
.table-info {
  font-size: 18px;
}
.table-square-container {
  display: flex;
  flex-wrap: wrap;
  .custom-button {
    background-color: #409eff; /* 按钮背景颜色 */
    color: #ffffff; /* 文字颜色 */
    border: none; /* 无边框 */
    padding: 8px 15px; /* 减小内边距 */
    font-size: 14px; /* 减小字体大小 */
    font-weight: bold; /* 字体加粗 */
    border-radius: 4px; /* 圆角稍微减小 */
    box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1); /* 减小阴影效果 */
    cursor: pointer; /* 鼠标悬停时显示手形图标 */
    transition: background-color 0.3s, box-shadow 0.3s, transform 0.3s; /* 添加变换过渡效果 */
    outline: none; /* 点击时不显示轮廓 */
  }

  .custom-button:hover {
    background-color: #3a8ee6; /* 鼠标悬停时的背景颜色 */
    box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.2); /* 鼠标悬停时的阴影效果 */
    transform: scale(1.05); /* 鼠标悬停时放大按钮 */
  }

  .custom-button:active {
    background-color: #3274d1; /* 鼠标按下时的背景颜色 */
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.15); /* 鼠标按下时的阴影效果 */
    transform: scale(0.95); /* 鼠标按下时缩小按钮 */
  }
}



.table-square {
  flex: 0 0 calc(20% - 20px); /* 让每个方块占据五分之一的宽度，并减少间隙 */
  padding: 40px 20px; /* 增加内边距，使内容更突出 */
  margin: 20px 25px; /* 上下间隙增加到20px，左右间隙保持15px */
  border: 1px solid #b0bec5; /* 边框颜色更柔和 */
  cursor: pointer; /* 鼠标移动到方块上显示手型光标 */
  text-align: center; /* 文本居中显示 */
  border-radius: 10px; /* 圆角边框，稍微调整圆角为10px */
  background-color: #f0f7f4; /* 浅蓝色背景 */
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.15); /* 添加轻微阴影效果 */
  transition: background-color 0.3s ease, box-shadow 0.3s ease; /* 平滑过渡效果 */
  font-family: Arial, sans-serif; /* 字体设置 */
  font-size: 16px; /* 字号设置 */
}

.table-square:hover {
  background-color: #e0f7fa; /* 悬停时背景色变化 */
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3); /* 悬停时阴影变化 */
}

</style>
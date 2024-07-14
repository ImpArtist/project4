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
        <el-menu router background-color="#001529" text-color="rgba(255, 255, 255, 0.6)"
                 active-text-color="#ccddcc"
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
              <el-breadcrumb-item style="font-size: 20px;"><span style="font-weight: bold; color: #40a9ff">数据库</span></el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div style="margin-top: 30px">
            <el-row style="font-family: 'Arial Black';  font-size: 18px;">
              数据库名：
              <el-input
                  placeholder="请输入内容"
                  v-model="searchData"
                  style="width: 300px;">
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
              </el-input>
              <el-switch
                  style="margin-left: 20px"
                  v-model="checkType"
                  active-text="全字匹配"
                  inactive-text="非全字匹配">
              </el-switch>
              <el-button type="primary" style="margin-left: 20px" icon="el-icon-search" circle></el-button>
            </el-row>
          </div>
          <div style="margin-top: 20px">
            <el-button round class="custom-button">新建数据库</el-button>
          </div>
          <div style="margin-top: 20px" class="square-container">
            <!-- 使用 v-for 指令动态生成小方块 -->
            <div class="square" v-for="databaseName in databasenamelists" :key="databaseName" @click="handleClick(databaseName)">
              {{ databaseName }}
            </div>
          </div>
        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
export default {
  name: "menu-page",
  data(){
    return{
      isCollapse:false,
      asideWidth: '200px',
      activeMenu: '/database/manage',
      searchData: '',
      checkType: false,
      databasenamelists: ["SQL数据库", ],
    }
  },
  methods:{
    handleClick(databaseName) {
      // 处理点击事件，可以根据需要跳转或执行其他操作
      this.$router.push('/database/table/manage');
    },

  }
}

</script>

<style>
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
.square-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px; /* 间隔10px */
}
.square {
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

.square:hover {
  background-color: #e0f7fa; /* 悬停时背景色变化 */
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.3); /* 悬停时阴影变化 */
}
</style>
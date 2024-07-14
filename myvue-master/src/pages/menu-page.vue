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
            <el-breadcrumb-item>基本介绍</el-breadcrumb-item>
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
          <div style="box-shadow: 0 0 20px rgba(0,0,0,.3); padding: 20px; border-radius: 10px; margin-bottom: 10px; margin-top:10px; font-size: 16px; font-family: '楷体', cursive;">
            <p>本数据中台目前只针对中台管理人员服务，大致功能为整合学院统一的数据库并为不同部门提供 API 以供接入获取对应的数据。</p>
            <div style="margin-top: 10px;"></div>
            <p>中台主要功能：</p>
            <ul>
              <li>数据查询：可以为中台管理人员方便得获取相应的数据，提供基础的查询功能以及一定的高级查询。</li>
              <li>数据分析：中台管理人员可以在对某一张表的某些数据进行监控其变化，或者在查询基础上对数据进行一定的统计及观察，提供定时报表推送，格式可个性化生成。</li>
              <li>API管理：中台管理人员可以对中台的API接口进行创建以及其他管理，并可以监控API调用情况。可以设置访问频率或者次数阈值等。</li>
              <li>统一库管理：中台管理人员可以连接或断开学院统一库，并可以对统一库中的表数据进行增删，也可以对其中的一些数据进行修改等。</li>
            </ul>
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
        asideWidth: '200px'
      }
    },
    methods:{

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
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
            <el-breadcrumb-item>API管理</el-breadcrumb-item>
            <el-breadcrumb-item>API使用情况</el-breadcrumb-item>
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
          <div style="margin-top: 30px">
            <el-row>
              <el-select v-model="apiName" filterable style="margin-left: 20px;
              margin-right: 20px; font-size: 25px;" placeholder="请选择API名称"
              @change="apiNameChange">
                <el-option
                    v-for="item in apiNames"
                    :key="item.api_name"
                    :label="item.api_name"
                    :value="item.api_name">
                </el-option>
              </el-select>
              <span style="font-size: 20px;" >
                状态：<span :style="{ color: statusColor }">{{status}}</span>
              </span>

              <span style="margin-left: 3%; font-size: 20px;" >
                业务用户：{{businessUser}}
              </span>

              <template>
                <span style="margin-left: 5%; font-size: 20px;">近期使用情况：</span>

                <el-select @change = "DrawCharts" style="font-size: 25px;" v-model="DateType" placeholder="请选择">
                  <el-option
                      v-for="item in DateTypes"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </template>

            </el-row>
            <el-row style=" margin-top: 20px;">
              <span style="margin-left: 20px; font-size: 20px">
                URL:{{API_URL}}
              </span>
            </el-row>
            <el-row  style=" margin-top: 20px;">
              <span style=" margin-left: 20px; font-size: 20px">
                功能描述：
              </span>
              <el-input
                  style="margin-top: 10px;"
                  type="textarea"
                  :autosize="{ minRows: 10, maxRows: 15}"
                  v-model="description"
                  readonly>
              </el-input>
            </el-row>
          </div>
          <div>
            <el-row  style="margin-top: 15px; display: flex; align-items: center; justify-content: flex-end;">
              <el-col :span="3" style="font-size: 20px">
                设置流量上限：
              </el-col>
              <el-col :span="5">
                <el-input v-model="streamNumber" width="200" placeholder="请输入内容"></el-input>
              </el-col>
              <el-col  :span="2" style="margin-left: 20px">
                <el-button type="primary" round>确定</el-button>
              </el-col>
              <el-button @click = "openAPI"  style="margin-left: 600px;" type="success" round>启用</el-button>
              <el-button @click = "closeAPI" type="danger" round>禁用</el-button>
            </el-row>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div id="bar-chart" style="width: 100%;height:400px;"></div>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div  id="pie-chart" style="width: 800px; height: 600px;"></div>
          </div>
        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import * as echarts from "echarts";

export default {
  name: "menu-page",
  data(){
    return{
      businessUser:'',
      API_URL:'',
      DateType:'最近一天',
      isCollapse:false,
      asideWidth: '200px',
      apiName:'',
      status:'运行',
      statusColor:'#67c23a',
      streamNumber:'',
      userName:'',
      description:'这里填写对应API的功能介绍和需要注意的事项',
      dateRange:[],
      DateTypes:[
        {
          value: '最近一天',
          label: '最近一天'
        },
        {
          value: '最近一个星期',
          label: '最近一周'
        },
        {
          value: '最近一个月',
          label: '最近一月'
        }
      ],
      userNames:[
        {
          value: 'user1',
          label: 'user1'
        },
        ],
      apiNames:[

        ],

      //画表
      bar_option : {//柱状图
        title: {
          text: '柱状图',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          left: 'left'
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [

        ]
      },

      pie_option: {//饼状图
        title: {
          text: '饼状图',
          subtext: '比例图',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [

        ]
      },

    }
  },
  methods:{
    apiNameChange(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/getConcreteInfo',
        data: {
          name: this.apiName
        }
      }).then(response => {
        console.log(response.data)
        this.description = response.data.api_info
        this.status = response.data.api_status
        this.API_URL = response.data.api_url
        this.businessUser = response.data.api_business
        if(response.data.api_status === '运行'){
          this.statusColor = '#67c23a'
        }
        else{
          this.statusColor = '#f56c6c'
        }

      }).catch(error => {
        console.log(error)
      })


      axios({
        method: 'post',
        url: 'http://localhost:10010/api/getConcreteChartsInfo',
        data: {
          type:this.DateType,
          name: this.apiName
        }
      }).then(response => {

        this.bar_option.series = response.data.bar
        this.bar_option.xAxis.data = response.data.xValues
        this.pie_option.series = response.data.pie
        this.initializePieChart()
        this.initializeBarChart()
      }).catch(error => {
        console.log(error);
      })


    },

    DrawCharts(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/getConcreteChartsInfo',
        data: {
          type:this.DateType,
          name: this.apiName
        }
      }).then(response => {


        this.bar_option.series = response.data.bar
        this.bar_option.xAxis.data = response.data.xValues
        this.pie_option.series = response.data.pie
        this.initializePieChart()
        this.initializeBarChart()
      }).catch(error => {
        console.log(error);
      })
    },

    initializeBarChart() {
      this.$nextTick(() => {
        let bartDom = document.getElementById('bar-chart');
        if (bartDom) {
          let barChart = echarts.init(bartDom);
          barChart.setOption(this.bar_option,true);
        }
      });
    },
    initializePieChart() {
      this.$nextTick(() => {
        let pieDom = document.getElementById('pie-chart');
        if (pieDom) {
          let pieChart = echarts.init(pieDom);
          pieChart.setOption(this.pie_option,true);
        }
      });
    },

    openAPI(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/open',
        data: {
          name: this.apiName
        }
      }).then(response => {
        console.log(response.data)
        this.status = '运行'
        this.statusColor = '#67c23a'

        this.$alert('已成功启动API', '成功', {
          confirmButtonText: '确定',
          callback: action => {
            if (action === 'confirm') {
              this.$message({
                type: 'success',
                message: 'API已成功启用'
              });
            }
          }
        });

      }).catch(error => {
        console.log(error)
      })
    },
    closeAPI(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/close',
        data: {
          name: this.apiName
        }
      }).then(response => {
        console.log(response.data)
        this.status = '禁用'
        this.statusColor = '#f56c6c'

        this.$alert('已成功禁用API', '成功', {
          confirmButtonText: '确定',
          callback: action => {
            if (action === 'confirm') {
              this.$message({
                type: 'success',
                message: 'API已成功禁用'
              });
            }
          }
        });

      }).catch(error => {
        console.log(error)
      })
    }

  },
  mounted(){
    axios({
      method: 'post',
      url: 'http://localhost:10010/api/getNameList',
      data: {
        value:'',
      }
    }).then(response => {
      this.apiNames = response.data;
      console.log(response.data)
    }).catch(error => {
      console.log(error);
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

</style>
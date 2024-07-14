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
            <el-breadcrumb-item>报表查询</el-breadcrumb-item>
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
          <div style="margin-top: 25px">
            <el-row style="font-family: 'Arial Black';  font-size: 20px;">
              报表名：
              <el-select style="margin-right: 50px" v-model="reportName"  placeholder="请选择">
                <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
<!--              报表格式：-->
<!--              <el-select  v-model="reportType" placeholder="请选择">-->
<!--                <el-option-->
<!--                    v-for="item in reportTypes"-->
<!--                    :key="item.value"-->
<!--                    :label="item.label"-->
<!--                    :value="item.value">-->
<!--                </el-option>-->
<!--              </el-select>-->
            </el-row>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div id="bar-chart" style="width: 100%;height:400px;"></div>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div id="line-chart" style="width: 100%;height:400px;"></div>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div id="pie-chart1" style="width: 800px; height: 600px;"></div>
            <div id="pie-chart2" style="width: 800px; height: 600px;"></div>
          </div>
        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "menu-page",
  data(){
    return{
      isCollapse:false,
      asideWidth: '200px',
      reportName: '',
      reportType: '',
      reportTypes: [
        {
          value: 'option1',
          label: '柱状图'
        },
        {
          value: 'option2',
          label: '饼状图'
        },
        {
          value: 'option3',
          label: '折线图'
        }
      ],
      options: [
        {
          value: 'option1',
          label: '报表1'
        },
        {
          value: 'option2',
          label: '报表2'
        },
        {
          value: 'option3',
          label: '报表3'
        },
        {
          value: 'option4',
          label: '报表4'
        },
        {
          value: 'option5',
          label: '报表5'
        }
      ],
      option1 : {
        title: {
          text: '分析结果',
          subtext: '柱状图',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: [
            "1班",
            "2班",
            "3班",
            "4班"
          ]
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'horizontal', // 设置为水平布局
          left: '8%', // 水平布局时可以调整 left 属性来控制位置
        },
        yAxis: {
          type: 'value'
        },
        "series": [
          {
            "name": "数量",
            "data": [
              7,
              8,
              9,
              7
            ],
            "type": "bar",
            "smooth": false
          },
          {
            "name": "最多出现次数",
            "data": [
              5,
              7,
              6,
              3
            ],
            "type": "bar",
            "smooth": false,
            "label": {
              "show": true,
              "position": "top",
              "formatter": function(params) { const labels = ["汉族", "汉族", "汉族", "汉族"]; return labels[params.dataIndex]; }
            }
          },
          {
            "name": "最少出现次数",
            "data": [
              1,
              1,
              1,
              1
            ],
            "type": "bar",
            "smooth": false,
            "label": {
              "show": true,
              "position": "top",
              "formatter": function(params) { const labels = ["满族", "满族", "满族", "壮族"]; return labels[params.dataIndex]; }
            }
          }
        ]
      },
      option2 : {
        title: {
          text: '分析结果',
          subtext: '折线图',
          left: 'center'
        },
        xAxis: {
          type: 'category',
          data: [
            "1班",
            "2班",
            "3班",
            "4班"
          ]
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'horizontal', // 设置为水平布局
          left: '8%', // 水平布局时可以调整 left 属性来控制位置
        },
        yAxis: {
          type: 'value'
        },
        "series": [
          {
            "name": "数量",
            "data": [
              7,
              8,
              9,
              7
            ],
            "type": "line",
            "smooth": false
          },
          {
            "name": "最多出现次数",
            "data": [
              5,
              7,
              6,
              3
            ],
            "type": "line",
            "smooth": false,
            "label": {
              "show": true,
              "position": "top",
              "formatter": function(params) { const labels = ["汉族", "汉族", "汉族", "汉族"]; return labels[params.dataIndex]; }
            }
          },
          {
            "name": "最少出现次数",
            "data": [
              1,
              1,
              1,
              1
            ],
            "type": "line",
            "smooth": false,
            "label": {
              "show": true,
              "position": "top",
              "formatter": function(params) { const labels = ["满族", "满族", "满族", "壮族"]; return labels[params.dataIndex]; }
            }
          }
        ]
      },
      option3 : {
        title: {
          text: '分析结果',
          subtext: '饼状图',
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
          {
            "name": "聚合值计数",
            "type": "pie",
            "radius": "50%",
            "data": [
              {
                "name": "满族",
                "value": 5
              },
              {
                "name": "壮族",
                "value": 4
              },
              {
                "name": "汉族",
                "value": 21
              },
              {
                "name": "回族",
                "value": 1
              }
            ],
            "emphasis": {
              "itemStyle": {
                "shadowBlur": 10,
                "shadowOffsetX": 0,
                "shadowColor": "rgba(0, 0, 0, 0.5)"
              }
            }
          }

        ]
      },
      option4 : {
        title: {
          text: '分析结果',
          subtext: '饼状图',
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
          {
            "name": "计数",
            "type": "pie",
            "radius": "50%",
            "data": [
              {
                "name": "4班",
                "value": 7
              },
              {
                "name": "3班",
                "value": 9
              },
              {
                "name": "2班",
                "value": 8
              },
              {
                "name": "1班",
                "value": 7
              }
            ],
            "emphasis": {
              "itemStyle": {
                "shadowBlur": 10,
                "shadowOffsetX": 0,
                "shadowColor": "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      },
    }
  },
  methods:{
    initializeBarChart() {
      this.$nextTick(() => {
        let bartDom = document.getElementById('bar-chart');
        if (bartDom) {
          let barChart = echarts.init(bartDom);
          barChart.setOption(this.option1,true);
        }
      });
    },
    initializeLineChart() {
      this.$nextTick(() => {
        let bartDom = document.getElementById('line-chart');
        if (bartDom) {
          let barChart = echarts.init(bartDom);
          barChart.setOption(this.option2,true);
        }
      });
    },
    initializePieChart1() {
      this.$nextTick(() => {
        let bartDom = document.getElementById('pie-chart1');
        if (bartDom) {
          let barChart = echarts.init(bartDom);
          barChart.setOption(this.option3,true);
        }
      });
    },
    initializePieChart2() {
      this.$nextTick(() => {
        let bartDom = document.getElementById('pie-chart2');
        if (bartDom) {
          let barChart = echarts.init(bartDom);
          barChart.setOption(this.option4,true);
        }
      });
    },
  },
  mounted() {
    this.initializeBarChart();
    this.initializeLineChart();
    this.initializePieChart1();
    this.initializePieChart2();
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
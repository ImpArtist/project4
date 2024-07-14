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
            <el-breadcrumb-item>学生学业能力维度观测</el-breadcrumb-item>
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
          <el-row>
            <div style=" margin-left: 10px; font-size: 20px; font-weight: bold; font-family: '微软雅黑', cursive;">
              选择学生：
              <el-select @change = "StudentDataShow" v-model="StudentID" filterable placeholder="请选择">
                <el-option
                    v-for="item in StudentIDList"
                    :key="item.stu_number"
                    :label="item.stu_number"
                    :value="item.stu_number">
                </el-option>
              </el-select>
              {{StudentData}}
            </div>
          </el-row>
          <el-row style="margin-top: 20px;">
            <div class="custom-content">
              <i class="el-icon-trophy"></i>
              学生能力
            </div>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="10">
              <el-card>
                <div style=" margin-left: 10px; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                  选择学生能力
                </div>
                <div class="checkbox-list">
                  <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-4" v-for="item in options" :key="item.value">
                      <el-checkbox v-model="item.checked" class="checkbox-item">
                        <el-tooltip class="item-tooltip" :content="item.label" effect="dark" placement="top">
                          <span class="label-text">{{ item.label }}</span>
                        </el-tooltip>
                      </el-checkbox>
                    </div>
                  </div>
                </div>
              </el-card>
            </el-col>
            <el-col :span="14">
              <el-card>

                <div class="block">
                  <el-cascader
                      v-model = "RadarShows"
                      :options="RadarShowList"
                      :props="props"
                      style="width: 300px"
                      clearable></el-cascader>
                  <el-button @click="DrawRadar" style="margin-left: 15px" type="primary" icon="el-icon-search" circle></el-button>
                </div>


                <div style="margin-top: 20px; display: flex; justify-content: center;">
                  <div  id="radar-chart" style="width: 1200px; height: 485px;"></div>
                </div>
              </el-card>
            </el-col>
          </el-row>

          <div>
            <el-row style="text-align: center; margin-left: 10px; margin-bottom: 15px; font-size: 20px; font-weight: bold; font-family: '楷体', cursive;">
              学生能力情况对比
            </el-row>
            <el-table :data="tableData"  class="centered-table" :fit="true" >
              <el-table-column
                  stripe
                  border
                  v-for="(column, index) in dynamicColumns"
                  :key="index"
                  :prop="column.attribute"
                  :label="column.translation"
                  :width="getColumnWidth(column.attribute)"
                  :resizable="column.resizable"
                  :show-overflow-tooltip="true"
              ></el-table-column>
            </el-table>
          </div>
          <div style="text-align: center; margin-left: 10px; margin-top: 20px; margin-bottom: 15px; font-size: 20px; font-weight: bold; font-family: '楷体', cursive;">
            维度选择：
            <el-select v-model="dimension" placeholder="请选择">
              <el-option
                  v-for="item in dimensions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <el-button @click="DrawBar" style="margin-left: 15px" type="primary" icon="el-icon-search" circle></el-button>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div id="bar-chart" style="width: 100%;height:400px;"></div>
          </div>

        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import axios from "axios";

export default {
  name: "menu-page",
  data(){
    return{
      props: { multiple: true },
      StudentData: '',
      StudentID: '',
      tableData: [],
      dimensions: [
        { label: '（课程业绩）公必绩点',
          value: 'stu_ability_compulsory_gpa',
          },
        { label: '（课程业绩）专选绩点',
          value: 'stu_ability_optional_gpa' },
        { label: '（课程业绩）专必绩点',
          value: 'stu_ability_major_gpa',
          },
        { label: '（综合竞赛）党建思政获奖（校级及以上数量）',
          value: 'stu_ability_party_award' },
        { label: '（专业竞赛）学科竞赛获奖（校级及以上数量）：指高校大学生计算机竞赛项目、MCM',
          value: 'stu_ability_subject_award',
          },
        { label: '（综合竞赛）艺术比赛获奖（校级及以上数量）',
          value: 'stu_ability_art_award' },
        { label: '（综合竞赛）体育比赛获奖（校级及以上数量）',
          value: 'stu_ability_sports_award' },
        { label: '（综合竞赛）实践创业竞赛获奖（校级及以上数量）：特指“赢在中大”、“挑战杯”、“互联网+”、省市级大学生网络攻防比赛',
          value: 'stu_ability_entrepreneurship_award',
          },
        { label: '（专业竞赛）学术成果获奖（校级及以上数量）：特指论文、技术获奖',
          value: 'stu_ability_academic_award' },
        { label: '（论文）高水平论文发表（数量）（数据范畴：CCF-A/CCF-B/CCF-C；中科院一区/二区/三区；CSSCI / CSCD / SCI / SSCI / EI / A&HCI ）',
          value: 'stu_ability_paper',
          },
        { label: '（社会服务）志愿服务时长（数量）',
          value: 'stu_ability_volunteer_time',
          },
        { label: '（知识产权）专利发明（数量）',
          value: 'stu_ability_patent' },
        { label: '（知识产权）软件著作权发明（数量）',
          value: 'stu_ability_copyright' },
        { label: '（专著）专著出版（数量）',
          value: 'stu_ability_monograph' },
      ],
      dimension: '',
      StudentIDList: [
        {
          stu_number: '1501010101'
        }],
      checked: false,
      checkedList: [], // 初始化为空数组，用来存储选中的复选框
      isCollapse:false,
      asideWidth: '200px',
      RadarShows:[],
      RadarShowList:[{
        value: 1,
        label: '本科生',
        children: []
      }, {
        value: 2,
        label: '研究生',
        children: []
      }],
      dynamicColumns: [
        {
          attribute: 'date',
          translation: '日期'
        },
        {
          attribute: 'name',
          translation: '姓名'
        },
        {
          attribute: 'address',
          translation: '地址'
        }
      ],


      options: [
        { label: '（课程业绩）公必绩点',
          value: 'stu_info_compulsory_gpa',
          checked: true},
        { label: '（课程业绩）专选绩点',
          value: 'stu_info_optional_gpa' },
        { label: '（课程业绩）专必绩点',
          value: 'stu_info_major_gpa',
          checked: true},
        { label: '（综合竞赛）党建思政获奖（校级及以上数量）',
          value: 'stu_info_party_award' },
        { label: '（专业竞赛）学科竞赛获奖（校级及以上数量）：指高校大学生计算机竞赛项目、MCM',
          value: 'stu_info_subject_award',
          checked: true},
        { label: '（综合竞赛）艺术比赛获奖（校级及以上数量）',
          value: 'stu_info_art_award' },
        { label: '（综合竞赛）体育比赛获奖（校级及以上数量）',
          value: 'stu_info_sports_award' },
        { label: '（综合竞赛）实践创业竞赛获奖（校级及以上数量）：特指“赢在中大”、“挑战杯”、“互联网+”、省市级大学生网络攻防比赛',
          value: 'stu_info_entrepreneurship_award',
          checked: true},
        { label: '（专业竞赛）学术成果获奖（校级及以上数量）：特指论文、技术获奖',
          value: 'stu_info_academic_award' },
        { label: '（论文）高水平论文发表（数量）（数据范畴：CCF-A/CCF-B/CCF-C；中科院一区/二区/三区；CSSCI / CSCD / SCI / SSCI / EI / A&HCI ）',
          value: 'stu_info_paper',
          checked: true},
        { label: '（社会服务）志愿服务时长（数量）',
          value: 'stu_info_volunteer_time',
          checked: true},
        { label: '（知识产权）专利发明（数量）',
          value: 'stu_info_patent' },
        { label: '（知识产权）软件著作权发明（数量）',
          value: 'stu_info_copyright' },
        { label: '（专著）专著出版（数量）',
          value: 'stu_info_monograph' },
      ],
      radar_option : {
        title: {
          left: 'center',
          top: '5%' // 调整这个值以确保标题不被挡住
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center',
          data: [
            'A Phone',
            'Another Phone',
          ]
        },
        radar:

          {
            indicator: [
              { name: 'Look', max: 150 },
              { name: 'Photo', max: 100 },
              { name: 'System', max: 130 },
              { name: 'Performance', max: 100 },
              { name: 'Screen', max: 200 }
            ],
            radius: 160,
            center: ['50%', '60%']
          },


        series: [

          {
            type: 'radar',
            radarIndex: 0,
            areaStyle: {},
            data: [
              {
                value: [70, 20, 60, 95, 95],
                name: 'A Phone'
              },
              {
                value: [95, 80, 95, 60, 15],
                name: 'Another Phone'
              }
            ]
          },
        ]
      },
      bar_option : {
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        legend: {
          data: []
        },
        series: [
          {
            data: [120, 200, 150, 80, 70, 110, 130],
            type: 'bar'
          }
        ]
      },
    }
  },
  methods:{
    see(){
      // Filter out checked items
      const checkedItems = this.options.filter(item => item.checked);
      // Log or use the checked items as needed
      console.log('Checked items:', checkedItems);
      // Example: Display checked items in an alert
      alert('Checked items:\n' + checkedItems.map(item => item.value).join('\n'));
    },

    getColumnWidth(prop) {
      const maxLength = this.getMaxColumnLength(prop);
      const titleWidth = prop.length * 15; // Assuming each character width is 15px
      const dynamicWidth = Math.max(maxLength * 15, titleWidth);
      return `${Math.min(Math.max(dynamicWidth, 80), 200)}px`; // Ensure the width is between 80px and 200px
    },
    getMaxColumnLength(prop) {
      return this.tableData.reduce((max, item) => {
        const value = String(item[prop] || ''); // Convert property value to string
        return Math.max(max, value.length);
      }, 0);
    },
    StudentDataShow(){
      axios.post('http://localhost:10010/stuAbility/getInfo',{
        stu_number:this.StudentID
      }).then(response => {
        this.StudentData = response.data;
      }).catch(error => {
        console.log(error)
      })
    },
    DrawRadar(){
      if(this.StudentID === ''){
        this.$message({
          message: '请选择学生',
          type: 'warning'
        })
        return;
      }
      // 使用 map 方法遍历 RadarShows 中的每个子数组，并提取最后一个元素
      const dimension = this.RadarShows.map(arr => arr[arr.length - 1]);

      const checkedItems = this.options.filter(item => item.checked);
      const attributes = checkedItems.map(item => item.value);


      axios({
        method: 'post',
        url: 'http://localhost:10010/stuAbility/createChart',
        data: {
          attributes:attributes,
          dimension:dimension,
          stuNumber:this.StudentID,
        }
      }).then(response => {
        console.log(response.data)
        this.radar_option.series = response.data.series;
        this.radar_option.radar.indicator = response.data.indicator;
        this.radar_option.legend.data = response.data.legend;
        this.dynamicColumns = response.data.mapping
        this.tableData = response.data.table;
        this.initializeRadarChart();
      }).catch(error => {
        console.log(error);
      })


    },

    DrawBar(){
      if(this.StudentID === ''){
        this.$message({
          message: '请选择学生',
          type: 'warning'
        })
        return;
      }
      console.log(this.dimension)
      console.log(this.StudentID)
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
        this.bar_option.xAxis.data = response.data.xValues;
        this.bar_option.legend.data = response.data.legend;
        this.initializeBarChart();
      }).catch(error => {
        console.log(error);
      })
    },

    initializeBarChart() {
      this.$nextTick(() => {
        let BarDom = document.getElementById('bar-chart');
        if (BarDom) {
          let BarChart = echarts.init(BarDom);
          BarChart.setOption(this.bar_option,true);
        }
      });
    },

    initializeRadarChart() {
      this.$nextTick(() => {
        let RadartDom = document.getElementById('radar-chart');
        if (RadartDom) {
          let RadarChart = echarts.init(RadartDom);
          RadarChart.setOption(this.radar_option,true);
        }
      });
    },

  },

  mounted() {

    axios.post('http://localhost:10010/stuAbility/selectList')
        .then(response => {
          this.RadarShowList = response.data;
        })
        .catch(error => {
          console.log(error)
        })

    axios.post('http://localhost:10010/stuAbility/getStuNumList')
        .then(response => {
          this.StudentIDList = response.data;
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

.custom-content {
  font-size: 18px; /* 调整字体大小 */
  color: green;   /* 更改字体颜色 */
}

.checkbox-list {
   padding: 10px;
 }

.checkbox-item {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
}

.col-xs-12.col-sm-6.col-md-4 {
  margin-bottom: 12px;
}

 .label-text {
   display: inline-block;
   max-width: 100%; /* Ensure it respects parent width */
   overflow: hidden;
   text-overflow: ellipsis; /* Display ellipsis (...) for overflow */
   white-space: nowrap; /* Prevent wrapping */
 }

.el-checkbox-label {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
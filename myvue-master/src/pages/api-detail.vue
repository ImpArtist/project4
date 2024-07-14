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
            <el-breadcrumb-item>API详细信息查询</el-breadcrumb-item>
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
          <div style="margin-top: 30px;">
            <el-row style="font-family: 'Arial Black';  font-size: 20px;">
              搜索内容：
              <el-input
                  placeholder="请输入内容"
                  v-model="searchData"
                  style="width: 300px;">
                <i slot="prefix" class="el-input__icon el-icon-search"></i>
              </el-input>

              <el-select v-model="searchType" style="margin-left: 20px" placeholder="请选择">
                <el-option
                    v-for="item in searchTypes"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
              <el-switch
                  style="margin-left: 20px"
                  v-model="checkType"
                  active-text="全字匹配"
                  inactive-text="非全字匹配">
              </el-switch>
              <el-button style="margin-left: 30px" @click = "search" round type="primary" icon="el-icon-search">搜索</el-button>
            </el-row>

          </div>
          <div style="margin-top: 20px">
            <el-table :data="pagedData"  class="centered-table" :fit="true" >
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
              <el-table-column label="操作" width="100px">
                <template v-slot="scope">
                  <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.row)"></el-button>
                </template>
              </el-table-column>
            </el-table>
            <div style="display: flex; align-items: center; justify-content: space-between;">
              <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page.sync="currentPage"
                  :page-sizes="[10, 20, 30, 40]"
                  :page-size="pageSize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="tableData.length"
              ></el-pagination>
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
      selectedRow: '',
      pageSize:10,
      currentPage: 1,
      isCollapse:false,
      asideWidth: '200px',
      searchData:'',
      checkType:false,
      searchType:'api_name',
      pagedData: [],
      searchTypes:[
        {
          value: 'api_name',
          label: '名称查询'
        },
        {
          value: 'api_business',
          label: '业务方查询'
        },
      ],
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
      tableData: [
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }
      ]
    }
  },
  methods:{
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
    handleSizeChange(val) {
      this.pageSize = val;
      this.handleCurrentChange(1); // 切换每页条数时回到第一页
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      const startIndex = (val - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      this.pagedData = this.tableData.slice(startIndex, endIndex);
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该API, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        // 用户点击确定按钮后执行的操作
        axios({
          method: 'post',
          url: 'http://localhost:10010/api/delete',
          data: {
            name: row.api_name
          }
        }).then(response => {
          let status = response.data;
          if (status) {
            this.$message.success('删除成功');
            this.search(); // 重新获取数据
            this.currentPage = 1; // 恢复到第一页
          } else {
            this.$message.error('删除失败');
          }
        }).catch(error => {
          console.log(error);
          this.$message.error('删除失败，请稍后重试');
        });
      }).catch(() => {
        // 用户点击取消按钮后执行的操作
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    search(){
      let type = ''
      if(this.checkType){
        type = '全字搜索'
      }
      axios({
        method: 'post',
        url: 'http://localhost:10010/api/selectInfo',
        data: {
          attribute: this.searchType,
          value: this.searchData,
          type: type
        }
      }).then(response => {
        this.tableData = response.data;
        this.pagedData =  this.tableData.slice(0, this.pageSize);
      }).catch(error => {
        console.log(error);
      })
    }
  },

  mounted() {
    axios.post('http://localhost:10010/api/info')
        .then(response => {
          this.dynamicColumns =  response.data.mapping;
          this.tableData = response.data.data;
          this.pagedData =  this.tableData.slice(0, this.pageSize);
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

</style>
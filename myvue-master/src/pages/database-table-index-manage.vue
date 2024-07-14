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
                 style="border:none;"  :default-active = "activeMenu">
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
          <el-menu-item index="/database/manage" >
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
              <el-breadcrumb-item :to="{ path: '/database/table/manage' }" style="font-size: 20px;">数据库表</el-breadcrumb-item>
              <el-breadcrumb-item style="font-size: 20px;"><span style="font-weight: bold; color: #40a9ff">数据库表元素</span></el-breadcrumb-item>
            </el-breadcrumb>
          </div>

          <el-row style="font-family: 'Arial Black'; margin-top: 20px;  font-size: 20px;">
            搜索内容：
            <el-input
                placeholder="请输入内容"
                v-model="searchData"
                style="width: 300px;">
              <i slot="prefix" class="el-input__icon el-icon-search"></i>
            </el-input>

            <el-select v-model="attribute" style="margin-left: 20px" placeholder="请选择">
              <el-option
                  v-for="item in dynamicColumns"
                  :key="item.attribute"
                  :label="item.attribute"
                  :value="item.attribute">
              </el-option>
            </el-select>
            <el-button style="margin-left: 30px" @click = "search" round type="primary" icon="el-icon-search">搜索</el-button>
          </el-row>

          <el-table :data="pagedData"  style="margin-top: 20px" class="centered-table" :fit="true" >
            <el-table-column
                stripe
                border
                v-for="(column, index) in dynamicColumns"
                :key="index"
                :prop="column.attribute"
                :label="column.translation"
                :width="getColumnWidth(column.attribute)"
                :resizable="column.resizable"
                :editable="true"
                :show-overflow-tooltip="true"
            >
              <template v-slot="scope">
                <span v-if="!scope.row.editable" >{{ scope.row[column.attribute] }}</span>
                  <span v-else>
                    <el-input v-model="scope.row[column.attribute]" size="mini"></el-input>
                  </span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="100px">
              <template v-slot="scope">
                <el-button
                    v-if="scope.row.isNew"
                    type="primary"
                    icon="el-icon-check"
                    @click="handleSave(scope.row)"
                >保存</el-button>
                <el-button
                    v-else
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDelete(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 新增按钮 -->
          <el-button style="margin-top: 10px;" type="primary" @click="addNewRow">新增</el-button>

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

          <div>
            <el-row style="margin-top: 100px;">
              <span style="font-family: 'Arial Black'; font-size: 18px;">数据表结构：</span>
            </el-row>
            <el-table :data="table_tableData"  style="margin-top: 20px" class="centered-table" :fit="true" >
              <el-table-column
                  stripe
                  border
                  v-for="(column, index) in table_dynamicColumns"
                  :key="index"
                  :prop="column.attribute"
                  :label="column.translation"

                  :resizable="column.resizable"
                  :editable="true"
                  :show-overflow-tooltip="true"
              >
              </el-table-column>
              <el-table-column label="操作" width="160px">
                <template v-slot="scope">
                  <div style="display: flex; align-items: center;">
                    <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
                    <el-button type="danger" icon="el-icon-delete" @click="handleDelete_Table(scope.row)">删除</el-button>
                  </div>
                </template>
              </el-table-column>
            </el-table>
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
  props: ['TableName'],
  data(){
    return{
      searchData:'',
      attribute: '',
      pageSize: 10,
      currentPage: 1,
      pagedData: [],
      isCollapse:false,
      asideWidth: '200px',
      activeMenu: '/database/manage',
      dynamicColumns:[],
      table_dynamicColumns:[],
      table_tableData:[],
      tableData:[],
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
    search(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/DbManage/getTableData',
        data: {
          tableName: this.TableName,
          attribute: this.attribute,
          value: this.searchData,
        }
      }).then(response => {
        this.tableData = response.data.data;
        this.pagedData =  this.tableData.slice(0, this.pageSize);
      }).catch(error => {
        console.log(error);
      })
    },
    handleDelete(row) {
      const attributeName = this.dynamicColumns[0].attribute;
      let data={
        tableName:  this.TableName,
        attribute: attributeName,
        value: row[attributeName],
      }
      console.log(data)
      this.$confirm('此操作将永久删除该API, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        // 用户点击确定按钮后执行的操作
        axios({
          method: 'post',
          url: 'http://localhost:10010/DbManage/deleteRecord',
          data: {
            tableName:  this.TableName,
            attribute: attributeName,
            value: row[attributeName],
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
    addNewRow() {
      // 新增空白行
      const newRow = { editable: true, isNew: true };
      // 初始化空白行数据，以确保每列都有初始值
      this.dynamicColumns.forEach(column => {
        newRow[column.attribute] = ''; // 可以根据需要设置初始值
      });
      this.pagedData.push(newRow);
    },
    handleSave(row) {
      // 处理保存操作，可以在这里进行保存逻辑
      // 确认保存后，将 isNew 设置为 false，表示行已保存
      //row.isNew = false;
      console.log(row)
      // 可以在这里触发保存到后端的逻辑
    },
    handleDelete_Table(row){
      console.log(row)
      this.$confirm('此操作将永久删除该API, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        // 用户点击确定按钮后执行的操作
        axios({
          method: 'post',
          url: 'http://localhost:10010/DbManage/deleteField',
          data: {
            tableName:  this.TableName,
            Field: row.Field
          }
        }).then(response => {
          let status = response.data;
          if (status) {
            this.$message.success('删除成功');
            axios({
              method: 'post',
              url: 'http://localhost:10010/DbManage/getTableStruct',
              data: {
                tableName: this.TableName,
              }
            }).then(response => {
              console.log(response.data)
              this.table_dynamicColumns = response.data.mapping;
              this.table_tableData = response.data.data


            }).catch(error => {
              console.log(error);
            })
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
    }

  },
  mounted() {
    axios({
      method: 'post',
      url: 'http://localhost:10010/DbManage/getTableData',
      data: {
        tableName: this.TableName,
      }
    }).then(response => {
      console.log(response.data)
      this.dynamicColumns = response.data.mapping;
      this.tableData = response.data.data
      this.pagedData =  this.tableData.slice(0, this.pageSize);

    }).catch(error => {
      console.log(error);
    })

    axios({
      method: 'post',
      url: 'http://localhost:10010/DbManage/getTableData',
      data: {
        tableName: this.TableName,
      }
    }).then(response => {
      console.log(response.data)
      this.dynamicColumns = response.data.mapping;
      this.tableData = response.data.data
      this.pagedData =  this.tableData.slice(0, this.pageSize);

    }).catch(error => {
      console.log(error);
    })

    axios({
      method: 'post',
      url: 'http://localhost:10010/DbManage/getTableStruct',
      data: {
        tableName: this.TableName,
      }
    }).then(response => {
      console.log(response.data)
      this.table_dynamicColumns = response.data.mapping;
      this.table_tableData = response.data.data


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
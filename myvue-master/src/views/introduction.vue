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
            <el-breadcrumb-item>数据查询分析</el-breadcrumb-item>
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
          <el-breadcrumb separator-class="el-icon-arrow-right" style="font-family: 楷体">
            <el-breadcrumb-item style="font-size: 20px;">数据查询分析</el-breadcrumb-item>
            <el-breadcrumb-item style="font-size: 20px;"><span style="font-weight: bold;">数据查询</span></el-breadcrumb-item>
          </el-breadcrumb>
          <div style="margin-top: 10px">
            <el-row>
              <span style="font-size: 20px">
                搜索方式：
              </span>
              <el-select v-model="searchType" placeholder="请选择">
                <el-option
                    v-for="item in searchTypeList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-row>
          </div>
          <div v-show="searchType === '基础搜索'" style="box-shadow: 0 0 10px rgba(0,0,0,.3); padding: 20px; border-radius: 10px; margin-bottom: 10px; margin-top:10px;">
            <!--            基础搜索第一行-->
            <el-row style=" display: flex; align-items: center;">
              <el-col :span="2" style="margin-left: 20%; font-size: 20px; font-weight: bold; font-family: '微软雅黑', cursive;">
                搜索内容:
              </el-col>
              <el-col :span="6">
                <el-input
                    placeholder="请输入内容"
                    prefix-icon="el-icon-search"
                    v-model="BasicSearchContent">
                </el-input>
              </el-col>
              <el-col  :span="2" style="margin-left: 5%;">
                <el-button type="primary" icon="el-icon-search" @click="BasicSearch">搜索</el-button>
              </el-col>
              <el-col  :span="2" style="margin-left: 0;">
                <el-button type="danger" icon="el-icon-delete" @click="BasicSearchReset">重置</el-button>
              </el-col>
            </el-row>

            <!--            基础搜索第二行-->
            <el-row style="margin-top: 10px; display: flex; align-items: center;">
              <el-col :span="1.5" style="margin-left: 5%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                库名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="BasicSearchDatabaseName" placeholder="请选择">
                  <el-option
                      v-for="item in databaseList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <!--              基础搜索的表名选择器-->
              <el-col :span="1.5" style=" margin-left: 3%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                表名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="BasicSearchTableName" placeholder="请选择" @change="BasicSearchTableChange">
                  <el-option
                      v-for="item in tableList"
                      :key="item.table_name"
                      :label="item.translation"
                      :value="item.table_name">
                  </el-option>
                </el-select>
              </el-col>
              <!--              基础搜索的属性选择器-->
              <el-col :span="1.5" style=" margin-left: 3%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                属性：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="BasicSearchIndexName"  placeholder="请选择" >
                  <el-option
                      v-for="item in BasicSearchIndexNameList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="2.5"  style="margin-left: 3%;">
                <el-switch
                    v-model="BasicSearchCheckType"
                    active-text="全字匹配"
                    inactive-text="非全字匹配">
                </el-switch>
              </el-col>
            </el-row>

            <el-row style="display: flex; margin-top: 15px; align-items: center;">
              <el-col :span="1.5" style="margin-left: 20%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示属性：
              </el-col>
              <el-col :span="5">
                <el-select
                    v-model="BasicShowIndexNameList"
                    multiple placeholder="请选择">
                  <el-option
                      v-for="item in BasicSearchIndexShowNameList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="3" style="margin-left: 6%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示个数(0为全选)：
              </el-col>
              <el-col :span="2">
                <el-input-number  v-model="TableShowNum"
                                  :min="0"  :max=tableData.length :step="1" step-strictly></el-input-number>
              </el-col>

            </el-row>
          </div>
          <!--          范围选择的搜索页面-->
          <div v-show="searchType === '范围搜索'" style="box-shadow: 0 0 10px rgba(0,0,0,.3); padding: 20px; border-radius: 10px; margin-bottom: 10px; margin-top:10px;">
            <el-row style=" display: flex; align-items: center;">
              <el-col :span="1.5" style="margin-left: 5%;font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                库名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RangeSearchDatabaseName" placeholder="请选择">
                  <el-option
                      v-for="item in databaseList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <!--                  范围搜索的表名选择器-->
              <el-col :span="1.5" style=" margin-left: 3%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                表名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RangeSearchTableName" @change="RangeSearchTableChange" placeholder="请选择">
                  <el-option
                      v-for="item in tableList"
                      :key="item.table_name"
                      :label="item.translation"
                      :value="item.table_name">
                  </el-option>
                </el-select>
              </el-col>
              <!--                  范围搜索的属性选择器-->
              <el-col :span="1.5" style=" margin-left: 3%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                属性：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RangeSearchIndexName" @change="RangeSearchIndexChange"  placeholder="请选择">
                  <el-option
                      v-for="item in RangeSearchIndexNameList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>

              <el-col  :span="2" style="margin-left: 3%;">
                <el-button type="primary" icon="el-icon-search"  @click="RangeSearch">搜索</el-button>
              </el-col>
              <el-col  :span="3">
                <el-button type="danger" icon="el-icon-delete"  @click="RangeSearchReset">重置</el-button>
              </el-col>
            </el-row>
            <el-row style="margin-top: 20px; display: flex; align-items: center; justify-content: center;">
              <el-date-picker v-model="DateRange"
                              v-if="rangeType === '1'"
                              type="daterange"
                              align="right"
                              unlink-panels
                              range-separator="至"
                              start-placeholder="开始日期"
                              end-placeholder="结束日期"
                              :picker-options="pickerOptions">
              </el-date-picker>
              <div v-if="rangeType === '2'" style="display: flex; align-items: center;">
                <el-col :span="8">
                  <el-input v-model="rangeMinValue" placeholder="最小值" ></el-input>
                </el-col>
                <el-col :span="3">
                  ————
                </el-col>
                <el-col :span="8">
                  <el-input v-model="rangeMaxValue" placeholder="最大值"></el-input>
                </el-col>
              </div>
            </el-row>

            <el-row style="display: flex; margin-top: 15px; align-items: center;">
              <el-col :span="1.5" style="margin-left: 20%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示属性：
              </el-col>
              <el-col :span="5">
                <el-select
                    v-model="RangeShowIndexNameList"
                    multiple placeholder="请选择">
                  <el-option
                      v-for="item in RangeSearchIndexShowNameList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="3" style="margin-left: 6%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示个数(0为全选)：
              </el-col>
              <el-col :span="2">
                <el-input-number  v-model="TableShowNum"
                                  :min="0"  :max="1000000000000" :step="1" step-strictly></el-input-number>
              </el-col>

            </el-row>
          </div>
          <!--          关联搜索的搜索界面-->
          <div v-show="searchType === '关联搜索'" style="box-shadow: 0 0 10px rgba(0,0,0,.3);
          padding: 20px; border-radius: 10px; margin-bottom: 10px; margin-top:10px;">
            <el-row style=" display: flex; align-items: center;">
              <el-col :span="1.5" style="margin-left:40%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                库名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RelatedSearchDatabaseName" placeholder="请选择">
                  <el-option
                      v-for="item in databaseList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
            <!--            关联搜索的表1选择器-->
            <el-row type="flex" justify="center" align="middle" style="margin-top: 15px">
              <el-col :span="1.5" style="font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                表1名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RelatedSearchTableName1" @change="RelatedSearchTableChange1" placeholder="请选择">
                  <el-option
                      v-for="item in tableList"
                      :key="item.table_name"
                      :label="item.translation"
                      :value="item.table_name">
                  </el-option>
                </el-select>
              </el-col>
              <div style="width: 20px;"></div> <!-- 添加间隔 -->
              <!--              关联搜索的表2选择器-->
              <el-col :span="1.5" style="font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                表2名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RelatedSearchTableName2" @change="RelatedSearchTableChange2" placeholder="请选择">
                  <el-option
                      v-for="item in tableList"
                      :key="item.table_name"
                      :label="item.translation"
                      :value="item.table_name">
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
            <el-row type="flex" justify="center" align="middle" style="margin-top: 15px">
              <!--              关联搜索第三行-->
              <el-col :span="1.5" style="font-size: 15px;  font-weight: bold; font-family: '微软雅黑', cursive;">
                属性1：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RelatedSearchIndexName1"
                           @change="RelatedSearchIndexChange1"
                           style="margin-right: 3%" placeholder="请选择">
                  <el-option
                      v-for="item in indexNameList1"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="2">
                <el-select v-model="RelatedSearchMethod"  placeholder="请选择">
                  <el-option
                      v-for="item in RelatedSearchMethodList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="1.5" style="font-size: 15px; margin-left: 10px;  font-weight: bold; font-family: '微软雅黑', cursive;">
                属性2：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="RelatedSearchIndexName2"
                           @change="RelatedSearchIndexChange2"
                           placeholder="请选择">
                  <el-option
                      v-for="item in indexNameList2"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
            <el-row style="display: flex; margin-top: 15px; align-items: center;">
              <el-col :span="1.5" style="margin-left: 20%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示属性：
              </el-col>
              <el-col :span="5">
                <el-select
                    v-model="RelatedShowIndexNameList"
                    multiple placeholder="请选择">
                  <el-option
                      v-for="item in RelatedSearchIndexShowNameList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="3" style="margin-left: 6%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示个数(0为全选)：
              </el-col>
              <el-col :span="2">
                <el-input-number  v-model="TableShowNum"
                                  :min="0"  :max="1000000000000" :step="1" step-strictly></el-input-number>
              </el-col>
            </el-row>

            <el-row type="flex" justify="center" align="middle" style="margin-top: 15px">
              <el-button type="primary" icon="el-icon-search"  @click="RelatedSearch">搜索</el-button>
              <el-button type="danger" icon="el-icon-delete"  @click="RelatedSearchReset">重置</el-button>
            </el-row>

          </div>
          <!--          分组搜索搜索页面-->
          <div v-show="searchType === '分组搜索'" style="box-shadow: 0 0 10px rgba(0,0,0,.3);
          padding: 20px; border-radius: 10px; margin-bottom: 10px; margin-top:10px;">
            <el-row style="margin-left: 30%; display: flex; align-items: center;">
              <!--              分组搜索第一行-->
              <el-col :span="1.5" style="font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                库名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="GroupSearchDatabaseName" placeholder="请选择">
                  <el-option
                      v-for="item in databaseList"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <!--              分组搜索的表名选择器-->
              <el-col :span="1.5" style=" margin-left: 5%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                表名：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="GroupSearchTableName" @change="GroupSearchTableChange" placeholder="请选择">
                  <el-option
                      v-for="item in tableList"
                      :key="item.table_name"
                      :label="item.translation"
                      :value="item.table_name">
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
            <!--            分组搜索第二行-->
            <el-row style=" display: flex; margin-top: 15px; align-items: center;">
              <el-col :span="1.5" style="margin-left: 5%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                分组属性：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="GroupSearchGroupIndexName"
                           @change=""
                           placeholder="请选择">
                  <el-option
                      v-for="item in GroupSearchGroupIndexNameList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="1.5" style="margin-left: 5%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                聚合类型：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="GroupSearchType"
                           multiple
                           @change=""
                           placeholder="请选择">
                  <el-option
                      v-for="item in GroupSearchAggregationTypeList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>

              <el-col :span="1.5" style="margin-left: 5%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                聚合属性：
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="GroupSearchAggregationIndex"
                           @change=""
                           placeholder="请选择">
                  <el-option
                      v-for="item in GroupSearchAggregationIndexList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
            </el-row>

            <el-row style="display: flex; margin-top: 15px; align-items: center;">
              <el-col :span="3" style="margin-left: 35%; font-size: 15px; font-weight: bold; font-family: '微软雅黑', cursive;">
                展示个数(0为全选)：
              </el-col>
              <el-col :span="2">
                <el-input-number  v-model="TableShowNum"
                                  :min="0"  :max="1000000000000" :step="1" step-strictly></el-input-number>
              </el-col>

            </el-row>
            <el-row style="display: flex; margin-top: 15px; align-items: center;">
              <el-col :span="10" style="margin-left: 25%; text-align: center;">
                <el-button type="primary" icon="el-icon-search" @click="GroupSearch">搜索</el-button>
                <el-button type="danger" icon="el-icon-delete" @click="GroupSearchReset">重置</el-button>
              </el-col>
            </el-row>
          </div>
          <div style="margin-top: 20px;">
            <el-table :data="pagedData" @sort-change="handleSortChange" class="centered-table" :fit="true" >
              <el-table-column
                  stripe
                  border
                  sortable
                  v-for="(column, index) in dynamicColumns"
                  :key="index"
                  :prop="column.attribute"
                  :label="column.translation"
                  :width="getColumnWidth(column.attribute)"
              ></el-table-column>
            </el-table>
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
          <div style="margin-top: 15px; display: flex; align-items: center; justify-content: flex-end;">
            <el-row>
              <el-col :span="3">
                <el-button type="warning" icon="el-icon-download">导出报告</el-button>
              </el-col>
            </el-row>
          </div>
          <hr style="margin-top: 35px">

          <div style="font-family:楷体;">
            <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-top: 20px;">
              <el-breadcrumb-item style="font-size: 20px;" >数据查询分析</el-breadcrumb-item>
              <el-breadcrumb-item style="font-size: 20px;"><span style="font-weight: bold;">数据分析</span></el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <div>
            <el-row style="margin-top: 15px; display: flex; align-items: center;">
              <el-col :span="2.5" style="font-size: 20px; font-weight: bold; font-family: '微软雅黑', cursive;">
                聚合值选择:
              </el-col>
              <el-col :span="0">

              </el-col>
              <el-col :span="4" style="">
                <el-select v-model="AnalysisAggregateValue"  placeholder="请选择">
                  <el-option
                      v-for="item in AnalysisAggregateValueList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>

              <el-col :span="2.5" style="margin-left: 5%; font-size: 20px; font-weight: bold; font-family: '微软雅黑', cursive;">
                分组设置:
              </el-col>
              <el-col :span="2.5">
                <el-select v-model="AnalysisGroupValue" placeholder="请选择">
                  <el-option
                      v-for="item in AnalysisGroupValueList"
                      :key="item.attribute"
                      :label="item.translation"
                      :value="item.attribute">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="1">

              </el-col>
              <el-col :span="3">
                <el-button type="primary" icon="el-icon-search" @click="Analyze">分析</el-button>
              </el-col>
            </el-row>
            <el-row style="margin-top: 20px">
              柱状图
              <el-switch
                  v-model="bar_display"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  style="margin-right: 2%">
              </el-switch>
              折线图
              <el-switch
                  v-model="line_display"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  style="margin-right: 2%">
              </el-switch>
              饼状图
              <el-switch
                  v-model="pie_display"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  style="margin-right: 2%">
              </el-switch>
            </el-row>

          </div>

          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div v-if="bar_display" id="bar-chart" style="width: 100%;height:400px;"></div>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div v-if="line_display" id="line-chart" style="width: 100%; height: 400px;"></div>
          </div>
          <div style="margin-top: 20px; display: flex; justify-content: center;">
            <div v-if="pie_display" id="pie-chart1" style="width: 800px; height: 600px;"></div>
            <div v-if="pie_display" id="pie-chart2" style="width: 800px; height: 600px;"></div>
          </div>
          <!--          <div style="margin-top: 20px; display: flex; justify-content: center;">-->
          <!--            <div v-if="pie_display" id="pie-chart2" style="width: 800px; height: 600px;"></div>-->
          <!--          </div>-->

          <div>
            <el-row style="margin-top: 20px; display: flex; justify-content: center; align-items: center;">
              <div v-html="formattedDescription" style="text-align: center; font-size: 16px; line-height: 1.5;"></div>
            </el-row>
          </div>

          <div
              v-if="(bar_display || pie_display || line_display) || everAnalyzed"
              style="display: flex; justify-content: center; align-items: center; margin-top: 10px; height: 100px;">
            <div style="display: flex; align-items: center;">
              <span style="margin-right: 10px;">自定义报表名：</span>
              <el-input
                  placeholder="请输入报表名称"
                  v-model="charts_name"
                  clearable
                  style="margin-right: 20px; width: 200px;"> <!-- 可以根据需要调整宽度 -->
              </el-input>
              <el-button @click = "subscribe" type="success" icon="el-icon-collection" round>订阅</el-button>
            </div>
          </div>

        </el-main>


      </el-container>

    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import * as echarts from 'echarts';

export default {
  name: "menu-page",
  data(){
    return{
      CurrentTableSearchType:'',
      begin:'',
      end:'',
      isCollapse:false,
      asideWidth: '200px',
      searchContent: '',
      databaseName: '',
      tableName: '',
      searchType: '基础搜索',
      checkType: false,
      aggregateValue: '',
      indexName1: '',
      indexName2: '',
      indexName: '',
      DateRange: '',
      rangeType: '2',
      rangeMinValue: '',
      tableName1: '',
      tableName2: '',
      rangeMaxValue: '',
      RangeSearchDatabaseName: '',
      RangeSearchTableName: '',
      RangeSearchIndexName: '',
      BasicSearchContent: '',
      BasicSearchDatabaseName: '',
      BasicSearchTableName: '',
      BasicSearchIndexName: '',
      BasicSearchCheckType: false,
      RelatedSearchDatabaseName: '',
      RelatedSearchTableName1: '',
      RelatedSearchTableName2: '',
      RelatedSearchIndexName1: '',
      RelatedSearchIndexName2: '',
      RelatedSearchMethod: '',
      GroupSearchDatabaseName: '',
      GroupSearchTableName: '',
      GroupSearchGroupIndexName: '',
      GroupSearchAggregationIndex: '',
      GroupSearchType: [],
      currentPage: 1,
      pageSize:10,
      BasicShowIndexNameList: [],
      GroupShowIndexNameList: [],
      RelatedShowIndexNameList: [],
      RangeShowIndexNameList: [],
      TableShowNum: 0,
      pagedData:[],
      AnalysisAggregateValue:'',
      AnalysisAggregateValueList:[],
      AnalysisGroupValue:'',
      AnalysisGroupValueList:[],

      RelatedSearchIndexShowNameList: [{
        value: '选项1',
        label: '展示属性1'
      }],
      BasicSearchIndexShowNameList: [{
        value: '选项1',
        label: '展示属性1'
      }],
      RangeSearchIndexShowNameList: [{
        value: '选项1',
        label: '展示属性1'
      }],
      GroupSearchIndexShowNameList: [{
        value: '选项1',
        label: '展示属性1'
      }],
      GroupSearchAggregationIndexList: [{
        value: '选项1',
        label: '聚合属性1'
      },
        {
          value: '选项2',
          label: '聚合属性2'
        },
        {
          value: '选项3',
          label: '聚合属性3'
        }],
      GroupSearchAggregationTypeList: [{
        attribute: 'AVG',
        translation: '平均值'
      },
        {attribute: 'MIN',
          translation: '最小值'
        },
        {attribute: 'COUNT',
          translation: '计数'
        },
        {
          attribute: 'MAX',
          translation: '最大值'
        },
        {
          attribute: 'SUM',
          translation: '求和'
        },
        {
          attribute: 'STD',
          translation: '标准差'
        }],
      GroupSearchGroupIndexNameList: [{
        value: '选项1',
        label: '分组属性1'
      },
        {
          value: '选项2',
          label: '分组属性2'
        },
        {
          value: '选项3',
          label: '分组属性3'
        }],
      RelatedSearchMethodList: [{
        value: '=',
        label: '＝'
      }, {
        value: '!=',
        label: '≠'
      }, {
        value: '>',
        label: '＞'
      }, {
        value: '<',
        label: '＜'
      }, {
        value: '>=',
        label: '≥'
      }, {
        value: '<=',
        label: '≤'
      }],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      BasicSearchIndexNameList: [{
        value: '选项1',
        label: '属性1'
      }],
      RangeSearchIndexNameList: [{
        value: '选项1',
        label: '属性1'
      }],
      GroupIndexNameList: [{
        value: '选项1',
        label: '属性1'
      }],

      indexNameList1: [{
        value: '选项1',
        label: '属性1'
      }, {
        value: '选项2',
        label: '属性2'
      }, {
        value: '选项3',
        label: '属性3'
      }, {
        value: '选项4',
        label: '属性4'
      }, {
        value: '选项5',
        label: '属性5'
      }],
      indexNameList2: [{
        value: '选项1',
        label: '属性1'
      }, {
        value: '选项2',
        label: '属性2'
      }, {
        value: '选项3',
        label: '属性3'
      }, {
        value: '选项4',
        label: '属性4'
      }, {
        value: '选项5',
        label: '属性5'
      }],
      databaseList: [{
        value: '选项1',
        label: '学院数据'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      // 数据表
      tableList: [{
        table_name: '表名1',
        translation: '表名1'
      }
      ],

      searchTypeList: [{
        value: '基础搜索',
        label: '基础搜索'
      }, {
        value: '关联搜索',
        label: '关联搜索'
      }, {
        value: '范围搜索',
        label: '范围搜索'
      },
        {
          value: '分组搜索',
          label: '分组搜索'
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
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄'
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄'
        }
      ],

      // 数据分析图表
      bar_display:false,
      line_display:false,
      pie_display:false,
      charts_name:'',
      everAnalyzed:false,
      chart_tableName:'',

      bar_option : {//柱状图
        title: {
          text: '柱状图',
          left: 'center',
          top: '5%' // 调整这个值以确保标题不被挡住
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
      line_option : { //折线图
        title: {
          text: '折线图',
          left: 'center',
          top: '5%' // 调整这个值以确保标题不被挡住
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          left: 'left'
        },
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
          name: '日期'
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '金额',
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: 'line',
            smooth: true
          },
          {
            name: '库存',
            data: [356, 987, 457, 768, 390, 680, 1920],
            type: 'line',
            smooth: true
          }
        ]
      },

      pie_option: {//饼状图1
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

      pie_option2: {//饼状图2
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
      description : ''
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
    BasicSearchReset(){
      this.BasicShowIndexNameList = []
      this.BasicSearchContent = ''
      this.BasicSearchDatabaseName = ''
      this.BasicSearchTableName = ''
      this.BasicSearchIndexName = ''
      this.BasicSearchCheckType = false
    },
    // 基础搜索表名变化，属性名跟着变化
    BasicSearchTableChange(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/table/attribute',
        data: {
          table: this.BasicSearchTableName
        }
      }).then(response => {
        this.BasicSearchIndexNameList = response.data
        this.BasicSearchIndexShowNameList = response.data

      }).catch(error => {
        console.log(error);
      })
    },

    BasicSearch(){
      if(this.BasicSearchDatabaseName === ''){
        this.$message.error('请选择库名')
        return;
      }
      if(this.BasicSearchTableName === ''){
        this.$message.error('请选择表名')
        return;
      }
      if(this.BasicSearchIndexName === ''){
        this.$message.error('请选择属性')
        return;
      }

      this.CurrentTableSearchType = '基础搜索'

      axios({
        method: 'post',
        url: 'http://localhost:10010/search/mapping',
        data: {
          table: this.BasicSearchTableName,
          attributes: this.BasicShowIndexNameList,
        }
      }).then(response => {
        this.dynamicColumns = response.data
        this.AnalysisGroupValueList = response.data
        this.AnalysisAggregateValueList = response.data
      }).catch(error => {
        console.log(error);
      })


      axios({
        method: 'post',
        url: 'http://localhost:10010/search/normal',
        data: {
          value: this.BasicSearchContent,
          databaseName: this.BasicSearchDatabaseName,
          table: this.BasicSearchTableName,
          attribute: this.BasicSearchIndexName,
          type: this.BasicSearchCheckType,
          attributes: this.BasicShowIndexNameList,
          order: '',
          count: this.TableShowNum,
          start: 0
        }
      }).then(response => {
        this.tableData = response.data
        this.handleCurrentChange(1); // 切换每页条数时回到第一页
      }).catch(error => {
        console.log(error);
      })

      this.chart_tableName = this.BasicSearchTableName
      this.AnalysisAggregateValue = ''
      this.AnalysisGroupValue = ''
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
    // 排序
    handleSortChange({ column, prop, order}) {
      if (this.CurrentTableSearchType === '基础搜索') {
        axios({
          method: 'post',
          url: 'http://localhost:10010/search/normal',
          data: {
            value: this.BasicSearchContent,
            databaseName: this.BasicSearchDatabaseName,
            table: this.BasicSearchTableName,
            attribute: this.BasicSearchIndexName,
            type: this.BasicSearchCheckType,
            attributes: this.BasicShowIndexNameList,
            order: prop,
            count: this.TableShowNum,
            start: 0,
            desc:order
          }
        }).then(response => {
          this.tableData = response.data
          this.handleCurrentChange(1); // 切换每页条数时回到第一页
        }).catch(error => {
          console.log(error);
        })
      }

      if (this.CurrentTableSearchType === '关联搜索') {
        axios({
          method: 'post',
          url: 'http://localhost:10010/search/connect',
          data: {
            databaseName: this.RelatedSearchDatabaseName,
            table1: this.RelatedSearchTableName1,
            table2: this.RelatedSearchTableName2,
            attribute1: this.RelatedSearchIndexName1,
            attribute2: this.RelatedSearchIndexName2,
            compareType: this.RelatedSearchMethod,
            attributes: this.RelatedShowIndexNameList,
            count: this.TableShowNum,
            desc:order,
            order: prop,
          }
        }).then(response => {
          console.log(response.data)
          this.tableData = response.data
          this.handleCurrentChange(1); // 切换每页条数时回到第一页
        }).catch(error => {
          console.log(error);
        })
      }


      if (this.CurrentTableSearchType === '分组搜索') {
        axios({
          method: 'post',
          url: 'http://localhost:10010/search/group',
          data: {
            databaseName: this.GroupSearchDatabaseName,
            table: this.GroupSearchTableName,
            group: this.GroupSearchGroupIndexName,
            aggregateTypes: this.GroupSearchType,
            aggregate: this.GroupSearchAggregationIndex,
            count: this.TableShowNum,
            order: prop,
            value: '',
            type:'',
            attribute:'',
            desc:order,
          }
        }).then(response => {
          this.tableData = response.data
          this.handleCurrentChange(1); // 切换每页条数时回到第一页
        }).catch(error => {
          console.log(error);
        })
      }


      if (this.CurrentTableSearchType === '范围搜索') {
        if(this.rangeType === '1'){
          axios({
            method: 'post',
            url: 'http://localhost:10010/search/range',
            data: {
              databaseName: this.RangeSearchDatabaseName,
              table: this.RangeSearchTableName,
              attribute: this.RangeSearchIndexName,
              attributes : this.RangeShowIndexNameList,
              dateRange: this.DateRange,
              begin: this.begin,
              end: this.end,
              order: prop,
              count: this.TableShowNum,
              start: 0,
              desc:order
            }
          }).then(response => {
            this.tableData = response.data
            this.handleCurrentChange(1); // 切换每页条数时回到第一页
          }).catch(error => {
            console.log(error);
          })
        }


        if (this.rangeType === '2'){
          axios({
            method: 'post',
            url: 'http://localhost:10010/search/range',
            data: {
              databaseName: this.RangeSearchDatabaseName,
              table: this.RangeSearchTableName,
              attribute: this.RangeSearchIndexName,
              attributes : this.RangeShowIndexNameList,
              dateRange: this.DateRange,
              begin: this.rangeMinValue,
              end: this.rangeMaxValue,
              order: prop,
              count: this.TableShowNum,
              start: 0,
              desc:order
            }
          }).then(response => {
            this.tableData = response.data
            this.handleCurrentChange(1); // 切换每页条数时回到第一页
          }).catch(error => {
            console.log(error);
          })
        }
      }

    },

    RangeSearchReset(){
      this.RangeSearchIndexShowNameList = ''
      this.RangeSearchDatabaseName = ''
      this.RangeSearchTableName = ''
      this.RangeSearchIndexName = ''
      this.DateRange = ''
      this.rangeType = '1'
      this.rangeMinValue = ''
      this.rangeMaxValue = ''
    },
    RangeSearchTableChange(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/table/attribute',
        data: {
          table: this.RangeSearchTableName
        }
      }).then(response => {
        this.RangeSearchIndexNameList = response.data
        this.RangeSearchIndexShowNameList = response.data
      }).catch(error => {
        console.log(error);
      })
    },
    RangeSearchIndexChange(){
      // 获取所选属性的对象
      const selectedItem = this.RangeSearchIndexNameList.find(item => item.attribute === this.RangeSearchIndexName);
      // 根据所选属性名称动态设置对应的Rangetype
      if (selectedItem) {
        if (selectedItem.class_name === "String") {
          this.rangeType = '2';
        } else if (selectedItem.class_name === "Date") {
          this.rangeType = '1';
        }
      }
    },
    RangeSearch(){
      if(this.RangeSearchDatabaseName === ''){
        this.$message.error('请选择库名')
        return;
      }
      if(this.RangeSearchTableName === ''){
        this.$message.error('请选择表名')
        return;
      }
      if(this.RangeSearchIndexName === ''){
        this.$message.error('请选择属性')
        return;
      }
      if(this.rangeType === '1'){
        if(this.DateRange === ''|| this.DateRange === null){
          this.$message.error('请选择日期范围')
          return;
        }
      }
      if(this.rangeType === '1'){
        this.begin = this.DateRange[0]
        this.end = this.DateRange[1]
      }

      this.CurrentTableSearchType = '范围搜索'
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/mapping',
        data: {
          table: this.RangeSearchTableName,
          attributes: this.RangeShowIndexNameList,

        }
      }).then(response => {
        this.dynamicColumns = response.data
        this.AnalysisGroupValueList = response.data
        this.AnalysisAggregateValueList = response.data
      }).catch(error => {
        console.log(error);
      })


      if(this.rangeType === '2'){
        axios({
          method: 'post',
          url: 'http://localhost:10010/search/range',
          data: {
            databaseName: this.RangeSearchDatabaseName,
            table: this.RangeSearchTableName,
            attribute: this.RangeSearchIndexName,
            attributes : this.RangeShowIndexNameList,
            dateRange: this.DateRange,
            begin: this.rangeMinValue,
            end: this.rangeMaxValue,
            order: '',
            count: this.TableShowNum,
            start: 0,
            desc:''
          }
        }).then(response => {
          this.tableData = response.data
          this.handleCurrentChange(1); // 切换每页条数时回到第一页
        }).catch(error => {
          console.log(error);
        })
      }


      if(this.rangeType === '1'){
        axios({
          method: 'post',
          url: 'http://localhost:10010/search/range',
          data: {
            databaseName: this.RangeSearchDatabaseName,
            table: this.RangeSearchTableName,
            attribute: this.RangeSearchIndexName,
            attributes : this.RangeShowIndexNameList,
            dateRange: this.DateRange,
            begin: this.DateRange[0],
            end: this.DateRange[1],
            order: '',
            count: this.TableShowNum,
            start: 0,
            desc:''
          }
        }).then(response => {
          this.tableData = response.data
          this.handleCurrentChange(1); // 切换每页条数时回到第一页
        }).catch(error => {
          console.log(error);
        })
      }
      this.chart_tableName = this.RangeSearchTableName
      this.AnalysisAggregateValue = ''
      this.AnalysisGroupValue = ''
    },


    RelatedSearchTableChange1(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/table/attribute',
        data: {
          table: this.RelatedSearchTableName1
        }
      }).then(response => {
        this.indexNameList1 = response.data
      }).catch(error => {
        console.log(error);
      })
    },


    RelatedSearchTableChange2(){

      if(this.RelatedSearchTableName1 === this.RelatedSearchTableName2){
        this.$message.error('请选择不同的表')
        this.RelatedSearchTableName2 = '' // 重置表2的值为空
        return;
      }

      axios({
        method: 'post',
        url: 'http://localhost:10010/search/table/attribute',
        data: {
          table: this.RelatedSearchTableName2
        }
      }).then(response => {
        this.indexNameList2 = response.data
      }).catch(error => {
        console.log(error);
      })

      axios({
        method: 'post',
        url: 'http://localhost:10010/search/connect/mapping',
        data: {
          table1: this.RelatedSearchTableName1,
          table2: this.RelatedSearchTableName2
        }
      }).then(response => {
        this.RelatedSearchIndexShowNameList = response.data
      }).catch(error => {
        console.log(error);
      })
    },
    RelatedSearchIndexChange1(){
      if(this.RelatedSearchIndexName1 === this.RelatedSearchIndexName2){
        this.$message.error('请选择不同的属性')
        this.RelatedSearchIndexName1 = '' // 重置属性2的值为空
        return
      }
    },
    RelatedSearchIndexChange2(){
      if(this.RelatedSearchIndexName1 === ''){
        this.$message.error('请选择属性1')
        this.RelatedSearchIndexName2 = '' // 重置属性2的值为空
      }
    },
    RelatedSearchReset(){
      this.RelatedSearchDatabaseName = ''
      this.RelatedSearchTableName1 = ''
      this.RelatedSearchTableName2 = ''
      this.RelatedSearchIndexName1 = ''
      this.RelatedSearchIndexName2 = ''
      this.RelatedSearchMethod = ''
    },


    RelatedSearch(){
      if(this.RelatedSearchDatabaseName === ''){
        this.$message.error('请选择库名')
        return;
      }
      if(this.RelatedSearchTableName1 === ''){
        this.$message.error('请选择表1名')
        return;
      }
      if(this.RelatedSearchTableName2 === ''){
        this.$message.error('请选择表2名')
        return;
      }
      if(this.RelatedSearchIndexName1 === ''){
        this.$message.error('请选择属性1')
        return;
      }
      if(this.RelatedSearchIndexName2 === ''){
        this.$message.error('请选择属性2')
        return;
      }
      if(this.RelatedSearchMethod === ''){
        this.$message.error('请选择关联方式')
        return;
      }
      this.CurrentTableSearchType = '关联搜索'


      axios({
        method: 'post',
        url: 'http://localhost:10010/search/connect/attribute/mapping',
        data: {
          table1: this.RelatedSearchTableName1,
          table2: this.RelatedSearchTableName2,
          attributes: this.RelatedShowIndexNameList,
        }
      }).then(response => {
        this.dynamicColumns = response.data
      }).catch(error => {
        console.log(error);
      })

      axios({
        method: 'post',
        url: 'http://localhost:10010/search/connect',
        data: {
          databaseName: this.RelatedSearchDatabaseName,
          table1: this.RelatedSearchTableName1,
          table2: this.RelatedSearchTableName2,
          attribute1: this.RelatedSearchIndexName1,
          attribute2: this.RelatedSearchIndexName2,
          compareType: this.RelatedSearchMethod,
          attributes: this.RelatedShowIndexNameList,
          count: this.TableShowNum,
          order: '',
        }
      }).then(response => {
        this.tableData = response.data
        this.handleCurrentChange(1); // 切换每页条数时回到第一页
      }).catch(error => {
        console.log(error);
      })

      if(this.RelatedShowIndexNameList.length === 0){
        this.AnalysisGroupValueList = this.RelatedSearchIndexShowNameList
        this.AnalysisAggregateValueList = this.RelatedSearchIndexShowNameList
      }
      else{
        // 根据RelatedShowIndexNameList中的值筛选出对应的多选框选项
        this.AnalysisGroupValueList = this.RelatedSearchIndexShowNameList.filter(item => this.RelatedShowIndexNameList.includes(item.attribute));
        this.AnalysisAggregateValueList = this.RelatedSearchIndexShowNameList.filter(item => this.RelatedShowIndexNameList.includes(item.attribute));
      }

      this.chart_tableName = this.RelatedSearchTableName1
      this.AnalysisAggregateValue = ''
      this.AnalysisGroupValue = ''

    },


    GroupSearchTableChange(){
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/table/attribute',
        data: {
          table: this.GroupSearchTableName
        }
      }).then(response => {
        this.GroupSearchGroupIndexNameList = response.data
        this.GroupSearchAggregationIndexList = response.data
        this.GroupSearchIndexShowNameList = response.data
      }).catch(error => {
        console.log(error);
      })
    },
    GroupSearchReset(){
      this.GroupSearchDatabaseName = ''
      this.GroupSearchTableName = ''
      this.GroupSearchGroupIndexName = ''
      this.GroupSearchAggregationIndex = ''
      this.GroupSearchType = ''
    },


    GroupSearch(){
      if(this.GroupSearchDatabaseName === ''){
        this.$message.error('请选择库名')
        return;
      }
      if(this.GroupSearchTableName === ''){
        this.$message.error('请选择表名')
        return;
      }
      if(this.GroupSearchGroupIndexName === ''){
        this.$message.error('请选择分组属性')
        return;
      }
      if(this.GroupSearchType === ''){
        this.$message.error('请选择聚合类型')
        return;
      }
      if(this.GroupSearchAggregationIndex === ''){
        this.$message.error('请选择聚合属性')
        return;
      }
      this.CurrentTableSearchType = '分组搜索'
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/group/mapping',
        data: {
          databaseName: this.GroupSearchDatabaseName,
          table: this.GroupSearchTableName,
          group: this.GroupSearchGroupIndexName,
          aggregateTypes: this.GroupSearchType,
          aggregate: this.GroupSearchAggregationIndex
        }
      }).then(response => {
        this.dynamicColumns = response.data
        this.AnalysisGroupValueList = response.data
        this.AnalysisAggregateValueList = response.data
      }).catch(error => {
        console.log(error);
      })

      axios({
        method: 'post',
        url: 'http://localhost:10010/search/group',
        data: {
          databaseName: this.GroupSearchDatabaseName,
          table: this.GroupSearchTableName,
          group: this.GroupSearchGroupIndexName,
          aggregateTypes: this.GroupSearchType,
          aggregate: this.GroupSearchAggregationIndex,
          count: this.TableShowNum,
          order: '',
          value: '',
          type:'',
          attribute:'',
          desc:'',
        }
      }).then(response => {
        this.tableData = response.data
        this.handleCurrentChange(1); // 切换每页条数时回到第一页
      }).catch(error => {
        console.log(error);
      })
      this.chart_tableName = this.GroupSearchTableName
      this.AnalysisAggregateValue = ''
      this.AnalysisGroupValue = ''
    },

    Analyze(){
      if(this.AnalysisGroupValue === ''){
        this.$message.error('请选择分组属性')
        return;
      }
      if(this.AnalysisAggregateValue === ''){
        this.$message.error('请选择聚合属性')
        return;
      }
      if(this.AnalysisGroupValue === this.AnalysisAggregateValue){
        this.$message.error('请选择不同的属性')
        return;
      }

      this.everAnalyzed = true

      axios({
        method: 'post',
        url: 'http://localhost:10010/search/analysis/bar',
        data: {
          group:this.AnalysisGroupValue,
          aggregate:this.AnalysisAggregateValue,
          data:this.tableData,
          table:this.chart_tableName
        }
      }).then(response => {
        response.data.series.forEach(seriesItem => {
          if (seriesItem.label && typeof seriesItem.label.formatter === 'string') {
            seriesItem.label.formatter = new Function('return ' + seriesItem.label.formatter)();
          }
        });

        this.bar_option.series = response.data.series
        this.bar_option.xAxis.data = response.data.xValues

        // 柱状图
        // let barDom = document.getElementById('bar-chart');
        // let barChart = echarts.init(barDom);
        // barChart.setOption(this.bar_option,true)
        this.initializeBarChart()
      }).catch(error => {
        console.log(error);
      })

      //折线图
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/analysis/line',
        data: {
          group:this.AnalysisGroupValue,
          aggregate:this.AnalysisAggregateValue,
          data:this.tableData,
          table:this.chart_tableName
        }
      }).then(response => {
        response.data.series.forEach(seriesItem => {
          if (seriesItem.label && typeof seriesItem.label.formatter === 'string') {
            seriesItem.label.formatter = new Function('return ' + seriesItem.label.formatter)();
          }
        });

        this.line_option.series = response.data.series
        this.line_option.xAxis.data = response.data.xValues

        this.initializeLineChart()
      }).catch(error => {
        console.log(error);
      })


      //饼状图
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/analysis/pie',
        data: {
          group:this.AnalysisGroupValue,
          aggregate:this.AnalysisAggregateValue,
          data:this.tableData,
          table:this.chart_tableName
        }
      }).then(response => {

        this.pie_option.series = response.data.series1
        this.pie_option2.series = response.data.series2
        console.log(this.pie_option.series)
        console.log(this.pie_option2.series)

        this.initializePieChart()
      }).catch(error => {
        console.log(error);
      })

      //饼状图
      axios({
        method: 'post',
        url: 'http://localhost:10010/search/analysis/all',
        data: {
          group:this.AnalysisGroupValue,
          aggregate:this.AnalysisAggregateValue,
          data:this.tableData,
          table:this.chart_tableName
        }
      }).then(response => {
        this.description = response.data
      }).catch(error => {
        console.log(error);
      })


    },

    //图表
    initializeBarChart() {
      this.$nextTick(() => {
        let bartDom = document.getElementById('bar-chart');
        if (bartDom) {
          let barChart = echarts.init(bartDom);
          barChart.setOption(this.bar_option,true);
        }
      });
    },
    initializeLineChart() {
      this.$nextTick(() => {
        let linetDom = document.getElementById('line-chart');
        if (linetDom) {
          let lineChart = echarts.init(linetDom);
          lineChart.setOption(this.line_option,true);
        }
      });
    },
    initializePieChart() {
      this.$nextTick(() => {
        let pieDom = document.getElementById('pie-chart1');
        if (pieDom) {
          let pieChart = echarts.init(pieDom);
          pieChart.setOption(this.pie_option,true);
        }
      });
      this.$nextTick(() => {
        let pieDom = document.getElementById('pie-chart2');
        if (pieDom) {
          let pieChart = echarts.init(pieDom);
          pieChart.setOption(this.pie_option2,true);
        }
      });
    },
    subscribe(){
      if(this.charts_name === ''){
        this.$message.error('请输入报表名称')
        return;
      }
      if(!this.everAnalyzed){
        this.$message.error('请先对表进行分析')
        return;
      }

    }

  },
  watch: {
    bar_display(newVal) {
      if (newVal) {
        this.initializeBarChart();
      }
    },
    line_display(newVal) {
      if (newVal) {
        this.initializeLineChart();
      }
    },
    pie_display(newVal) {
      if (newVal) {
        this.initializePieChart();
      }
    },
  },
  computed: {
    formattedDescription() {
      // 将逗号替换为换行符，并用 <br> 标签包裹
      return this.description.split(',').join('<br>');
    }
  },
  mounted() {
    axios.get('http://localhost:10010/search/table')
        .then(response => {
          this.tableList = response.data
        })
        .catch(error => {
          console.log(error)
        })
    this.pagedData =  this.tableData.slice(0, this.pageSize);
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
.custom-table {
  border-collapse: collapse; /* 合并边框 */
  border-color: #333; /* 原始线条颜色 */
}

.custom-table td,
.custom-table th {
  border: 1px solid; /* 设置单元格的边框 */
}

</style>

<style scoped>
/* 自定义表格样式 */
.centered-table .el-table th,
.centered-table .el-table td {
  text-align: center; /* 设置内容居中 */
}
</style>
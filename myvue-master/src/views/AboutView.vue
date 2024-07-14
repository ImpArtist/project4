<template>
  <div>
    <el-table
        :data="pagedData"
        style="width: 100%"
        @sort-change="handleSortChange"
    >
      <el-table-column prop="id" label="id" sortable width="180"></el-table-column>
      <el-table-column prop="date" label="日期" sortable width="180"></el-table-column>
      <el-table-column prop="name" label="姓名" sortable width="180"></el-table-column>
      <el-table-column prop="address" label="地址" sortable></el-table-column>
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
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      pagedData: [],
      currentPage: 1,
      pageSize: 10,
    };
  },
  created() {
    this.generateData();
    this.handleCurrentChange(1);
  },
  methods: {
    generateData() {
      // 新的示例数据，包含随机生成的ID，并且日期顺序已经被打乱
      this.tableData = [
        { id: 17, date: '2024-05-10', name: '王十二', address: '南京' },
        { id: 9, date: '2024-05-06', name: '孙八', address: '重庆' },
        { id: 4, date: '2024-05-19', name: '孙二十一', address: '合肥' },
        { id: 12, date: '2024-05-08', name: '吴十', address: '西安' },
        { id: 20, date: '2024-05-13', name: '钱十五', address: '天津' },
        { id: 8, date: '2024-05-11', name: '赵十三', address: '杭州' },
        { id: 15, date: '2024-05-02', name: '王五', address: '广州' },
        { id: 18, date: '2024-05-04', name: '赵六', address: '深圳' },
        { id: 1, date: '2024-05-15', name: '吴十七', address: '大连' },
        { id: 5, date: '2024-05-03', name: '李四', address: '上海' },
        { id: 19, date: '2024-05-05', name: '钱七', address: '成都' },
        { id: 14, date: '2024-05-20', name: '钱二十二', address: '南昌' },
        { id: 7, date: '2024-05-07', name: '周九', address: '武汉' },
        { id: 10, date: '2024-05-09', name: '郑十一', address: '长沙' },
        { id: 6, date: '2024-05-12', name: '孙十四', address: '苏州' },
        { id: 3, date: '2024-05-16', name: '郑十八', address: '沈阳' },
        { id: 11, date: '2024-05-01', name: '张三', address: '北京' },
        { id: 13, date: '2024-05-18', name: '赵二十', address: '石家庄' },
        { id: 2, date: '2024-05-17', name: '王十九', address: '济南' },
      ];
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.handleCurrentChange(1); // 切换每页条数时回到第一页
    },
    handleCurrentChange(val) {
      const startIndex = (val - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      this.pagedData = this.tableData.slice(startIndex, endIndex);
    },
    handleSortChange({ column, prop, order }) {
      console.log( prop, order);
    },
  },
};
</script>

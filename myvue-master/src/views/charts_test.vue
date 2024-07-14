<template>
  <el-table :data="tableData" class="centered-table" :fit="true">
    <el-table-column
        v-for="(column, index) in dynamicColumns"
        :key="index"
        :prop="column.attribute"
        :label="column.translation"
        :resizable="column.resizable"
        :show-overflow-tooltip="true"
    >
      <template v-slot="scope">
        <span v-if="!scope.row.editable" @dblclick="handleRowDoubleClick(scope.row)">{{ scope.row[column.attribute] }}</span>
        <span v-else>
          <el-input v-model="scope.row[column.attribute]" size="mini"></el-input>
        </span>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        { id: 1, name: '', age:0 , city: '', editable: true },
        { id: 2, name: 'Jane Smith', age: 25, city: 'Los Angeles', editable: false },
        { id: 3, name: 'Mike Johnson', age: 35, city: 'Chicago', editable: false },
        { id: 4, name: 'Emily Davis', age: 28, city: 'San Francisco', editable: false },
      ],
      dynamicColumns: [
        { attribute: 'name', translation: '姓名',  },
        { attribute: 'age', translation: '年龄', },
        { attribute: 'city', translation: '城市', },
      ],
      editingRow: null,
    };
  },
  methods: {
    handleRowDoubleClick(row) {
      // 双击行时切换编辑状态
      if (this.editingRow !== row) {
        // 关闭上一个编辑状态
        if (this.editingRow) {
          this.editingRow.editable = false;
        }
        // 开启当前行的编辑状态
        row.editable = true;
        this.editingRow = row;
      }
    },
    handleClickOutside(event) {
      // 点击表格空白区域时退出编辑状态
      const clickedElement = event.target;
      const isInsideTable = clickedElement.closest('.el-table');
      if (!isInsideTable) {
        if (this.editingRow) {
          this.editingRow.editable = false;
          this.editingRow = null;
        }
      }
    },
  },
  mounted() {
    // 监听整个文档的点击事件
    document.addEventListener('click', this.handleClickOutside);
  },
  beforeDestroy() {
    // 组件销毁前移除事件监听
    document.removeEventListener('click', this.handleClickOutside);
  },
};
</script>

<style scoped>
/* 根据需要添加样式 */
</style>

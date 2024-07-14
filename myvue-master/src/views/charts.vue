<template>
  <div>
    <el-row :gutter="10">
      <el-col :span="12">
        <el-card>
          <div style="width: 100%; height: 400px" id="line"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div style="width: 100%; height: 400px" id="bar"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="10" style="margin: 10px 0">
      <el-col :span="12">
        <el-card>
          <div style="width: 100%; height: 400px" id="pie"></div>
        </el-card>
      </el-col>
        <el-button type="primary" @click = "change">主要按钮</el-button>
    </el-row>

  </div>
  <div>
    aaaaaaa
  </div>

</template>

<script>
import * as echarts from 'echarts'

const option = {
  title: {
    text: '订单销售的趋势图',
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
}

const option1 = {
  title: {
    text: '订单销售的柱状图',
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
    data: ['水果', '零食', '饮料', '奶制品', '生活用品']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '金额',
      data: [820, 932, 901, 934, 1290, 1330, 1320],
      type: 'bar',
      smooth: true
    },
    {
      name: '销量',
      data: [100, 200, 204, 209, 590, 698, 700],
      type: 'line',
      smooth: true
    }
  ]
}

const option2 = {
  title: {
    text: '订单销售统计',
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
    {
      name: 'Access From',
      type: 'pie',
      center: ['50%', '60%'],
      radius: '50%',
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}

export default {
  name: "Charts",
  data() {
    return {}
  },
  methods: {
    change() {
        option.series[0].data = [100, 200, 300, 400, 500, 600, 700]
      let linetDom = document.getElementById('line');
      let lineChart = echarts.init(linetDom);
      lineChart.setOption(option)
    }
  },
  mounted() {  // 等待页面的元素全部加载完成之后再初始化

    // 折线图
    let linetDom = document.getElementById('line');
    let lineChart = echarts.init(linetDom);
    lineChart.setOption(option)

    // 柱状图
    let barDom = document.getElementById('bar');
    let barChart = echarts.init(barDom);
    barChart.setOption(option1)

    // 饼图
    let pieDom = document.getElementById('pie');
    let pieChart = echarts.init(pieDom);
    pieChart.setOption(option2)
  },
}
</script>

<style scoped>

</style>
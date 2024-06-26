package com.project;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.project.mapper.ChartMapper;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Object> implements ChartService {
    @Override
    public ResponseEntity<byte[]> getImage(String imageName) {
        try {
            // 从资源文件夹下读取图片，这里假设图片放在 resources/static/images/ 目录下
            Resource resource = new ClassPathResource("static/images/" + imageName + ".jpg");

            // 如果图片不存在，返回404错误
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 读取图片内容为字节数组
            byte[] imageBytes = Files.readAllBytes(Path.of(resource.getURI()));

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            // 返回图片字节数组和HTTP状态码
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public byte[] generateChartAsImage() throws TranscoderException, ScriptException {
        // 创建 ECharts 图表配置
        GsonOption option = new GsonOption();
        option.title("销售数据");

        // 设置 x 轴数据
        option.xAxis(new CategoryAxis().data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));

        // 设置 y 轴数据
        option.yAxis(new ValueAxis());

        // 添加数据系列
        Bar bar = new Bar("销量");
        bar.data(120, 200, 150, 80, 70, 110, 130);  // 设置柱状图数据
        option.series(bar);

        // 将图表配置转换为 SVG 字符串
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        // 执行 JavaScript 代码来解析 JSON 并使用 ECharts 渲染图表
        String jsCode = "var option = JSON.parse('" + option.toString().replace("'", "\\'") + "'); "
                + "var chart = echarts.init(document.createElement('div')); "
                + "chart.setOption(option); "
                + "var svg = chart.getRenderedSVG(); "
                + "svg;"; // 注意：这里需要根据实际的 ECharts 版本调整代码

        // 执行 JavaScript 代码
        String svg = (String) engine.eval(jsCode);

        System.out.println(svg);
        // 转换 SVG 到 PNG
        return convertSvgToPng(svg);
    }

    private byte[] convertSvgToPng(String svg) throws TranscoderException {
        // 创建 PNGTranscoder
        PNGTranscoder transcoder = new PNGTranscoder();

        // 设置输入
        TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)));

        // 设置输出
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(outputStream);

        try {
            // 转码并输出到字节数组
            transcoder.transcode(input, output);
            return outputStream.toByteArray();
        } finally {
            try {
                // 关闭输出流
                outputStream.close();
            } catch (IOException e) {
                // 处理关闭输出流时的异常
                e.printStackTrace();
            }
        }
    }

}

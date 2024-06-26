package com.project;

import com.alibaba.nacos.common.packagescan.resource.ClassPathResource;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.series.Bar;
import lombok.RequiredArgsConstructor;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.script.ScriptException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RequestMapping("/chart")
@RestController
@RequiredArgsConstructor
public class ChartController {
    private final ChartService Service;
    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() throws TranscoderException {
        // 生成 ECharts 图表并转换为 PNG 图片字节数组
        try {
            // 生成 ECharts 图表并转换为 PNG 图片字节数组
            byte[] imageBytes = Service.generateChartAsImage();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            // 返回图片字节数组和HTTP状态码
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (TranscoderException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }


}

package com.project;

import com.github.abel533.echarts.Option;
import org.apache.batik.transcoder.TranscoderException;
import org.springframework.http.ResponseEntity;

import javax.script.ScriptException;

public interface ChartService {
    ResponseEntity<byte[]> getImage(String imageName);

    byte[] generateChartAsImage() throws TranscoderException, ScriptException;
}

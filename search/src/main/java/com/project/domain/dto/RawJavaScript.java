package com.project.domain.dto;

public class RawJavaScript {
    private final String script;

    public RawJavaScript(String script) {
        this.script = script;
    }

    @Override
    public String toString() {
        return script;
    }
}

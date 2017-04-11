package com.apple.web;

/**
 *
 */
public interface IJSCommandExecuter {

    /**
     * 执行JS命令
     * @param jsCommand
     */
    void evaluateJS(String jsCommand);

    /**
     * JS引擎代码
     * @return
     */
    String jsEngineCode();

}

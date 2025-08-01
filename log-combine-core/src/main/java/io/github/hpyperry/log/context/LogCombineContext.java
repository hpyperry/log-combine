/*
 * Copyright 2022 韩鹏宇
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.github.hpyperry.log.context;

import io.github.hpyperry.log.entity.LogInfo;
import io.github.hpyperry.log.enums.LogLevel;
import org.slf4j.helpers.MessageFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Context for log combine
 * <p>CREATE_TIME: 2022/4/23 15:51
 *
 * @author hpyperry
 * @version 1.0.0
 */
public class LogCombineContext {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
    private final ThreadLocal<LogInfo> logLocalStorage = new ThreadLocal<>();

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public static LogCombineContext getContext() {
        return LogCombineContextHolder.instance;
    }

    /**
     * 添加日志到上下文
     *
     * @param msg        消息
     * @param line       当前行
     * @param level      日志等级
     * @param className  当前类名
     * @param param      可变长参数
     * @param threadName log线程名
     */
    public void addLog(String msg, Integer line, LogLevel level, String className, String threadName, Object... param) {
        LocalDateTime now = LocalDateTime.now();
        String time = dateTimeFormatter.format(now);
        String logMsg = String.format("\n%s - [%s] %s %s - [%s] - %s", time, threadName, level, className, line, msg);
        String message = MessageFormatter.arrayFormat(logMsg, param).getMessage();
        LogInfo logInfo = getLogLocalStorage();
        if (logInfo == null) {
            initContext(message);
        } else {
            String originMessage = logInfo.getMessage();
            if (originMessage != null) {
                message = originMessage + message;
            }
            logInfo.setMessage(message);
        }
    }

    /**
     * 初始化上下文
     */
    public void initContext(String message) {
        LogInfo logInfo = new LogInfo(message, 0);
        setLogLocalStorage(logInfo);
    }

    /**
     * 获取上下文的日志信息
     *
     * @return 返回日志信息
     */
    public LogInfo getLogLocalStorage() {
        return logLocalStorage.get();
    }

    /**
     * 设置日志信息到上下文
     *
     * @param logInfo 日志信息
     */
    public void setLogLocalStorage(LogInfo logInfo) {
        logLocalStorage.set(logInfo);
    }

    /**
     * 获取储存的日志信息
     *
     * @param immediatelyClear 立即清理
     * @return 返回日志
     */
    public String getLog(boolean immediatelyClear) {
        String message = null;
        LogInfo logInfo = getLogLocalStorage();
        if (logInfo != null) {
            message = logInfo.getMessage();
        }
        //是否需要立即清理
        if (immediatelyClear) {
            clear();
        }
        return message;
    }

    /**
     * 获取日志不清理ThreadLocal
     *
     * @return 返回日志信息
     */
    public String getLog() {
        return getLog(false);
    }

    /**
     * 清除内容
     */
    public void clear() {
        logLocalStorage.remove();
    }


    /**
     * single mode
     */
    private static class LogCombineContextHolder {
        private static final LogCombineContext instance = new LogCombineContext();
    }
}

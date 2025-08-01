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

package io.github.hpyperry.log.aspect;

import io.github.hpyperry.log.context.LogCombineHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 日志合并打印切面
 * CREATE_TIME: 2022/4/23 16:28
 *
 * @author hpyperry
 * @version 1.0.0
 */
@Slf4j
@Aspect
public class LogCombinePrintAspect {


    @Pointcut(value = "@annotation(io.github.hpyperry.log.annotation.LogCombine)")
    public void pointCut() {

    }

    @Before(value = "pointCut()")
    public void preLog() {
        LogCombineHelper.pushNest();
    }

    @After(value = "pointCut()")
    public void logPrint() {
        try {
            LogCombineHelper.popNest();
        } catch (Throwable e) {
            log.error("error:{},{}", e.getMessage(), e);
        } finally {
            LogCombineHelper.print();
        }
    }
}

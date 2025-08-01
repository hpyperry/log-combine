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

package io.github.hpyperry.sample;

import io.github.hpyperry.log.annotation.EnableLogCombine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <PRE>
 *
 * </PRE>
 * CREATE_TIME: 2022/4/23 16:44
 *
 * @author hpyperry
 * @version 1.0.0
 */
@EnableLogCombine
@SpringBootApplication
public class SampleEnter {
    public static void main(String[] args) {
        SpringApplication.run(SampleEnter.class, args);
    }
}

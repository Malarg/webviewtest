/*
 * Copyright (C) 2018 Gravity group
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gravitygroup.taingle.zenmoneyexample;

import android.annotation.TargetApi;
import android.webkit.WebResourceResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OptionsAllowResponse {
    static final SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy kk:mm:ss", Locale.US);

    @TargetApi(21)
    static WebResourceResponse build() {
        Date date = new Date();
        final String dateString = formatter.format(date);

        Map<String, String> headers = new HashMap<String, String>() {{
            put("Connection", "close");
            put("Content-Type", "text/plain");
            put("Date", dateString + " GMT");
            put("Access-Control-Allow-Origin", "localhost");
            put("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
            put("Access-Control-Max-Age", "600");
            put("Access-Control-Allow-Credentials", "true");
            put("Access-Control-Allow-Headers", "accept, authorization, Content-Type");
            put("Via", "1.1 vegur");
        }};

        return new WebResourceResponse("text/plain", "UTF-8", 200, "OK", headers, null);
    }
}

/*
 * Copyright (c) 2016 Wootric (https://wootric.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.wootric.androidsdk.objects;

import java.util.HashMap;

/**
 * Created by maciejwitowski on 11/24/15.
 */
public final class Score {
    private final int score;
    private final String surveyType;

    private static final HashMap<String, HashMap<String, Integer>> scoreRules = new HashMap<String, HashMap<String, Integer>>(){
        {
            put("NPS", new HashMap<String, Integer>() {{
                put("min", 0);
                put("max", 10);
                put("negative_type_max", 6);
                put("neutral_type_max", 8);
            }});
            put("CES", new HashMap<String, Integer>() {{
                put("min", 1);
                put("max", 7);
                put("negative_type_max", 3);
                put("neutral_type_max", 5);
            }});
            put("CSAT", new HashMap<String, Integer>() {{
                put("min", 1);
                put("max", 5);
                put("negative_type_max", 2);
                put("neutral_type_max", 3);
            }});
        }
    };

    public Score(int score, String surveyType) {
        this.score = score;
        this.surveyType = surveyType;
    }

    public boolean isDetractor() {
        if (surveyType != null & scoreRules.containsKey(surveyType)){
            return score >= scoreRules.get(surveyType).get("min") &&
                    score <= scoreRules.get(surveyType).get("negative_type_max");
        } else {
            return score >= scoreRules.get("NPS").get("min") &&
                    score <= scoreRules.get("NPS").get("negative_type_max");
        }
    }

    public boolean isPassive() {
        if (surveyType != null & scoreRules.containsKey(surveyType)){
            return score > scoreRules.get(surveyType).get("negative_type_max") &&
                    score <= scoreRules.get(surveyType).get("neutral_type_max");
        } else {
            return score > scoreRules.get("NPS").get("negative_type_max") &&
                    score <= scoreRules.get("NPS").get("neutral_type_max");
        }
    }

    public boolean isPromoter() {
        if (surveyType != null & scoreRules.containsKey(surveyType)){
            return score > scoreRules.get(surveyType).get("neutral_type_max") &&
                    score <= scoreRules.get(surveyType).get("max") ;
        } else {
            return score > scoreRules.get("NPS").get("neutral_type_max") &&
                    score <= scoreRules.get("NPS").get("max") ;
        }
    }

    public static int maximumScore(String surveyType) {
        if (surveyType != null & scoreRules.containsKey(surveyType)){
            return scoreRules.get(surveyType).get("max");
        } else {
            return scoreRules.get("NPS").get("max");
        }
    }

    public static int minimumScore(String surveyType) {
        if (surveyType != null & scoreRules.containsKey(surveyType)){
            return scoreRules.get(surveyType).get("min");
        } else {
            return scoreRules.get("NPS").get("min");
        }
    }
}

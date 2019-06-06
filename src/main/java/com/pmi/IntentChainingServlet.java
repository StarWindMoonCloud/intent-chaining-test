/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.pmi;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class IntentChainingServlet extends SkillServlet {

    public IntentChainingServlet() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new LocationIntentStartedHandler(),
                        new LocationIntentInProgressHandler(),
                        new LocationIntentCompletedHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler()
                        )
                .withSkillId("amzn1.ask.skill.16ab2b6d-b475-4903-9312-d3c12051679a")
                .build();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("ACCESS: {}", request.getRequestURI());
        long tsStart = System.currentTimeMillis();
        super.doPost(request, response);
        long tsEnd = System.currentTimeMillis();
        log.info("skill process latency {}", tsEnd - tsStart);
    }
}

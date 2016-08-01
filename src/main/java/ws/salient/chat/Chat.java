/*
 * Copyright 2016 the original author or authors
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
 */

package ws.salient.chat;

import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.SessionClock;

public class Chat {

    private final KieSession session;
    private final Properties properties;
    private final Context context;

    @Inject
    public Chat(KieSession session,
            KieBase base,
            Properties properties,
            ClassLoader classLoader,
            Locale locale,
            SessionClock clock,
            ZoneId zoneId) {
        this.session = session;
        ResourceBundle resources = null;
        for (KiePackage kpackage : base.getKiePackages()) {
            try {
                if (resources == null) {
                    resources = (PropertyResourceBundle) ResourceBundle.getBundle(kpackage.getName() + ".chat", locale, classLoader);
                }
            } catch (MissingResourceException ex) {
            }
        }
        this.properties = properties;
        context = new Context()
                .withZoneId(zoneId)
                .withClock(clock)
                .withResources(resources);
    }

    public void sendReply(Response response) {
        Message reply = new Message().withText(context.getText(response.getText(), response.getProperties()));
        reply.setResponse(response);
        reply.setReplyTo(response.getReplyTo());
        Render render = null;
        Attachable attachable = response.getAttachable();
        if (attachable != null) {
            if (attachable instanceof Renderable) {
                List<String> contents = ((Renderable) attachable).getContents(context);
                render = new Render(contents);
            }
        }
        LinkedHashMap params = new LinkedHashMap();
        params.put("reply", reply);
        params.put("render", render);
        params.put("properties", properties());
        session.startProcess("ws.salient.chat.SendReply", params);
    }

    public Map properties() {
        return properties.entrySet().stream().filter((entry) -> {
            return entry.getKey().toString().startsWith("ws.salient.chat.");
        }).collect(Collectors.groupingBy((entry) -> {
            return entry.getKey().toString().substring("ws.salient.chat.".length(), entry.getKey().toString().lastIndexOf("."));
        }, Collectors.toMap((key) -> {
            return key.getKey().toString().substring(key.getKey().toString().lastIndexOf(".") + 1);
        }, (value) -> {
            return value.getValue();
        })));
    }

    public Context getContext() {
        return context;
    }
    
    public String getText(String key, Map properties) {
        return context.getText(key, properties);
    }

    public String getText(String key) {
        return context.getText(key, null);
    }
    
}

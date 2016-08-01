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
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import org.kie.api.time.SessionClock;

public class Context {

    private ResourceBundle resources;
    private SessionClock clock;
    private ZoneId zoneId;

    public ResourceBundle getResources() {
        return resources;
    }

    public SessionClock getClock() {
        return clock;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public void setClock(SessionClock clock) {
        this.clock = clock;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public Context withResources(ResourceBundle resources) {
        this.resources = resources;
        return this;
    }

    public Context withClock(SessionClock clock) {
        this.clock = clock;
        return this;
    }

    public Context withZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    public String getText(String key, Map<String, ?> properties) {
        if (key != null) {
            try {
                String value = key;
                if (resources != null && resources.containsKey(key)) {
                    value = resources.getString(key);
                }
                if (properties != null) {
                    StrSubstitutor sub = new StrSubstitutor(new StrLookup() {
                        @Override
                        public String lookup(String key) {
                            Object value = (properties.containsKey(key) ? properties.get(key) : null);
                            if (value instanceof List) {
                                value = (((List) value).isEmpty() ? null : ((List) value).get(0));
                            }
                            return (value == null ? null : value.toString());
                        }
                    });
                    value = sub.replace(value);
                }
                return value;
            } catch (MissingResourceException ex) {
                return key;
            }
        } else {
            return null;
        }
    }

}

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

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("24h")
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, Double> scores;
    private Map<String, List> properties;
    private Message message;

    public Intent() {
        super();
    }

    public Intent(String intent, Double score) {
        super();
        scores = new LinkedHashMap();
        scores.put(intent, score);
    }

    public Map<String, Double> getScores() {
        return scores;
    }

    public void setScores(Map<String, Double> scores) {
        this.scores = scores;
    }

    public Intent withScore(String intent, Double score) {
        if (scores == null) {
            scores = new LinkedHashMap();
        }
        scores.put(intent, score);
        return this;
    }

    public Map<String, List> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, List> properties) {
        this.properties = properties;
    }

    public Intent put(String key, List value) {
        if (properties == null) {
            properties = new LinkedHashMap<>();
        }
        properties.put(key, value);
        return (Intent) this;
    }
    
    public String first() {
        String first = null;
        if (scores != null && !scores.isEmpty()) {
            first = scores.entrySet().iterator().next().getKey();
        }
        return first;
    }

    public Intent putSingle(String key, Object value) {
        if (properties == null) {
            properties = new LinkedHashMap<>();
        }
        properties.putIfAbsent(key, Arrays.asList(value));
        return (Intent) this;
    }

    public Intent put(String key, Object value) {
        if (properties == null) {
            properties = new LinkedHashMap<>();
        }
        if (!properties.containsKey(key)) {
            properties.put(key, new LinkedList());
        }
        ((List) properties.get(key)).add(value);
        return this;
    }

    public List get(String key) {
        return (properties == null ? null : properties.get(key));
    }

    public Object getFirst(String key) {
        return (properties != null
                && properties.containsKey(key)
                && !properties.get(key).isEmpty() ? properties.get(key).get(0) : null);
    }

    public boolean containsKey(String key) {
        return (properties == null ? false : properties.containsKey(key));
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Intent withMessage(Message message) {
        this.message = message;
        return this;
    }

    public Integer intScore(String name) {
        Integer salience = 0;
        if (scores.containsKey(name)) {
            salience = new Double(scores.get(name) * 100.0).intValue();
        }
        return salience - 1;
    }

    @Override
    public String toString() {
        return "Intent{" + "scores=" + scores + ", properties=" + properties + ", message=" + message + '}';
    }
}

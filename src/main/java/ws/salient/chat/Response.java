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
import java.util.List;
import java.util.Map;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("24h")
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Intent intent;
    private String text;
    private Map<String, Object> replyTo = new LinkedHashMap();
    private Map<String, List> properties = new LinkedHashMap();
    private Attachable attachable;
    
    public Response() {
    }

    public Response(Intent intent) {
        this.intent = intent;
        if (intent.getMessage() != null && intent.getMessage().getReplyTo() != null) {
            replyTo.putAll(intent.getMessage().getReplyTo());
        }
        if (intent.getProperties() != null) {
            properties.putAll(intent.getProperties());
        }
    }
    
    public Object getFirst(String key) {
        return (properties != null
                && properties.containsKey(key)
                && !properties.get(key).isEmpty() ? properties.get(key).get(0) : null);
    }
    
    public Response putSingle(String key, Object value) {
        if (properties == null) {
            properties = new LinkedHashMap<>();
        }
        properties.putIfAbsent(key, Arrays.asList(value));
        return (Response) this;
    }

    public Response(Intent intent, String text, Attachable attachable) {
       this(intent, text);
       this.attachable = attachable;
    }

    public Response(Intent intent, String text) {
        this(intent);
        this.text = text;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getText() {
        return text;
    }

    public Map<String, Object> getReplyTo() {
        return replyTo;
    }

    public Map<String, List> getProperties() {
        return properties;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Response withText(String text) {
        this.text = text;
        return this;
    }

    public void setReplyTo(Map<String, Object> replyTo) {
        this.replyTo = replyTo;
    }

    public void setProperties(Map<String, List> properties) {
        this.properties = properties;
    }

    public Response withKey(String key) {
        this.text = key;
        return this;
    }

    public Response withReplyTo(Map<String, Object> replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public Response withProperties(Map<String, List> properties) {
        this.properties = properties;
        return this;
    }

    public Attachable getAttachable() {
        return attachable;
    }

    public void setAttachable(Attachable attachable) {
        this.attachable = attachable;
    }
    
    public Response withAttachable(Attachable attachment) {
        this.attachable = attachment;
        return this;
    }

    @Override
    public String toString() {
        return "Response{" + "intent=" + intent + ", text=" + text + ", replyTo=" + replyTo + ", properties=" + properties + ", attachable=" + attachable + '}';
    }

    
    
}

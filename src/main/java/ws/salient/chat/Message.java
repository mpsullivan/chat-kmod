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
import java.util.LinkedHashMap;
import java.util.Map;
import org.drools.core.factmodel.traits.Traitable;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.EVENT)
@Expires("24h")
@Traitable
public class Message implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Response response;
    private String text;
    private Map<String, Object> replyTo;
    private Object attachment;

    public Message() {
        super();
    }

    public Message(String text, Map<String, Object> replyTo) {
        super();
        this.text = text;
        this.replyTo = replyTo;
    }

    public Message(Map<String, Object> replyTo) {
        super();
        this.replyTo = replyTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message withText(String text) {
        this.text = text;
        return this;
    }

    public Map<String, Object> getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Map<String, Object> replyTo) {
        this.replyTo = replyTo;
    }
    
    public Message withReplyTo(String key, Object value) {
        if (replyTo == null) {
            replyTo = new LinkedHashMap();
        }
        replyTo.put(key, value);
        return this;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Message{" + "text=" + text + ", replyTo=" + replyTo + ", attachment=" + attachment + '}';
    }

}

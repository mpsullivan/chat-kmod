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


public class Text implements Attachable, Serializable {
    
    private static final long serialVersionUID = 1L;

    private String text;

    public Text() {
    }

    public Text(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
    @Override
    public Object getAttachment(Context context) {
        Map attachment = new LinkedHashMap();
        attachment.put("text", context.getText(text, null));
        return attachment;
    }
    
    
}

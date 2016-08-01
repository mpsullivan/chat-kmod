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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class AttachableList extends LinkedList<Attachable> implements Attachable {

    public AttachableList() {
    }

    public AttachableList(Collection<? extends Attachable> c) {
        super(c);
    }    
    
    @Override
    public Object getAttachment(Context context) {
        List attachments = new LinkedList();
        this.stream().forEach((attachable) -> {
            Object attachment = attachable.getAttachment(context);
            attachments.add(attachment);
        });
        return attachments;
    }
    
}

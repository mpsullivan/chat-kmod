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
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Aliases implements Serializable {

    private List<Pattern> patterns;

    public Aliases() {
    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    public Aliases withPattern(Pattern pattern) {
        if (patterns == null) {
            patterns = new LinkedList();
        }
        patterns.add(pattern);
        return this;
    }

    public Aliases withRegex(String regex, int flags) {
        return withPattern(Pattern.compile(regex, flags));
    }

    public Aliases withAlias(String alias) {
        return withRegex("\\b" + Pattern.quote(alias) + "\\b", Pattern.CASE_INSENSITIVE);
    }

    public boolean anyMatch(String text) {
        if (patterns == null || text == null) {
            return false;
        }
        boolean match = patterns.stream().anyMatch((pattern) -> (pattern.matcher(text).matches()));
        return match;
    }

    @Override
    public String toString() {
        return "Aliases{" + "patterns=" + patterns + '}';
    }

}

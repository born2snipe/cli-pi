/**
 *
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package cli.pi;

import cli.pi.io.ClasspathFileReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArgParse4jHelpColorizerTest {
    private ArgParse4jHelpColorizer colorizer;

    @Before
    public void setUp() throws Exception {
        colorizer = new ArgParse4jHelpColorizer();
    }

    @Test
    public void shouldHighlightTheSectionHeadings() {
        String result = colorizer.colorize(ClasspathFileReader.readEntirely("help-with-optional-args.txt"));

        assertTrue(result.contains("@|green usage:|@"));
        assertTrue(result.contains("@|green optional arguments:|@"));
        assertFalse(result.contains("@|green   -d DATE, --date DATE   The date to use when determining the day of the week (expected format:|@"));
    }

    @Test
    public void shouldHighlightTheSectionHeadingsForPositionalArgs() {
        String result = colorizer.colorize(ClasspathFileReader.readEntirely("help-with-positional-args.txt"));

        assertTrue(result.contains("@|green positional arguments:|@"));
    }

}
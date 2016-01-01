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
package cli.pi.io;

import java.util.Arrays;
import java.util.List;

import static cli.pi.io.InputRequestor.YesOrNo.NO;
import static cli.pi.io.InputRequestor.YesOrNo.YES;

public class YesOrNoInputConverter implements InputConverter<InputRequestor.YesOrNo> {
    @Override
    public InputRequestor.YesOrNo convertFromInput(String input) {
        String upperInput = input.toUpperCase();

        if ("YES".equals(upperInput) || "Y".equals(upperInput)) {
            return YES;
        }

        if ("NO".equals(upperInput) || "N".equals(upperInput)) {
            return NO;
        }

        return null;
    }

    @Override
    public String convertToString(InputRequestor.YesOrNo value) {
        return value.name();
    }

    @Override
    public List<String> availableValues() {
        return Arrays.asList("YES", "Y", "NO", "N");
    }
}

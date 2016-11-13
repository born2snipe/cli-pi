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

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A thread that will listen for the user to press ENTER in the console window
 */
public abstract class PressingOfEnterListener extends Thread {
    public PressingOfEnterListener() {
        setName("pressing-of-enter-listener");
        setDaemon(true);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        AtomicBoolean keepLooping = new AtomicBoolean(true);

        while (keepLooping.get()) {
            scanner.nextLine();
            enterPressed();
        }
    }

    public abstract void enterPressed();
}

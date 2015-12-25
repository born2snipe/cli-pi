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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ClassPathFileReader {
    public static String readEntirely(String file) {
        InputStream input = null;
        try {
            input = read(file);
            return inputStreamToString(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(input);
        }
    }

    public static InputStream read(String file) {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        if (input == null) {
            throw new FileNotFoundInClassPathException(file);
        }
        return input;
    }

    private static void close(InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {

            }
        }
    }

    private static String inputStreamToString(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int len = -1;
        byte[] buf = new byte[1024];
        while ((len = input.read(buf)) != -1) {
            output.write(buf, 0, len);
        }
        return new String(output.toByteArray(), Charset.forName("UTF-8"));
    }

    private static class FileNotFoundInClassPathException extends RuntimeException {
        public FileNotFoundInClassPathException(String file) {
            super("Could not locate file in classpath: [" + file + "]");
        }
    }
}

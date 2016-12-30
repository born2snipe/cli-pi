package cli.pi.mock;

import cli.pi.CliLog;

import java.util.ArrayList;
import java.util.List;

public class MockCliLog extends CliLog {
    private ArrayList<String> lines = new ArrayList<String>();
    private ArrayList<String> cleanedLines = new ArrayList<String>();

    @Override
    public void println(String message) {
        lines.add(message);
        cleanedLines.add(stripOutColorInfoFrom(message));
    }

    public List<String> getLines() {
        return cleanedLines;
    }

    public List<String> getLinesWithColorInfo() {
        return lines;
    }

    public void clear() {
        lines.clear();
        cleanedLines.clear();
    }

    private String stripOutColorInfoFrom(String message) {
        return message.replaceAll("@\\|.+ (.+?)\\|@", "$1");
    }
}

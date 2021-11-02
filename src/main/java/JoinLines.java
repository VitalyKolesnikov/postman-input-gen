import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JoinLines {

    static final int AMOUNT = 100;
    static final String INPUT = "C:/Users/vkolesnikov/temp/input.csv";
    static final String OUTPUT = "C:/Users/vkolesnikov/temp/output.csv";
    static final String PARAM = "courseUnitRealizationIds";

    public static void main(String[] args) throws IOException {

        Set<String> lines = readLines(INPUT);

        int counter = 0;
        List<String> lineList = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT, true))) {
            writeLineToFile(writer, PARAM);

            for (String line : lines) {
                if (counter < AMOUNT) {
                    lineList.add(line);
                    counter++;
                } else {
                    writeLineToFile(writer, makeString(lineList));
                    lineList.clear();
                    lineList.add(line);
                    counter = 1;
                }
            }

            if (!lineList.isEmpty()) {
                writeLineToFile(writer, makeString(lineList));
            }
        }

    }

    static Set<String> readLines(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void writeLineToFile(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    static String makeString(List<String> list) {
        var builder = new StringBuilder();
        builder.append("\"");
        String delimiter = "";
        for (String str : list) {
            builder.append(delimiter);
            delimiter = ",";
            builder.append("\"\"");
            builder.append(str);
            builder.append("\"\"");
        }
        builder.append("\"");
        return builder.toString();
    }

}

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Input {
    public ArrayList<ArrayList<String>> getInput() {
        try {
            ArrayList<String> file = (ArrayList<String>) Files.readAllLines(Paths.get("input.txt"));
            ArrayList<ArrayList<String>> commands = new ArrayList<>();
            for (String each_command : file) {
                if (!Objects.equals(each_command, "")) {
                    ArrayList<String> split = new ArrayList<>(Arrays.asList(each_command.split("\t")));
                    commands.add(split);
                }
            }
            return commands;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

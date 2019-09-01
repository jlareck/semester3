import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Game {

    public static void main(String argv[]) throws FileNotFoundException {
        File file = new File("/Users/mykolamedynsky/Desktop/3semester/Game/src/films.txt");
        Scanner scanner = new Scanner(file);
        var strings = new ArrayList<String>();
        while (scanner.hasNextLine()){
            String string = scanner.nextLine();
            strings.add(string);
            System.out.println(string);

        }
    }


}

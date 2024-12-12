package CLI;
import CLI.ConsoleV2;

public class MainConsole {

    public static void main(String[] args) {
        Console console = new Console(args);
        System.out.println(console.execute());
        System.exit(0);
    }
}

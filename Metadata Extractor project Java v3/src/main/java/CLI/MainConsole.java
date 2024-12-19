package CLI;

import com.drew.imaging.ImageProcessingException;

import java.io.IOException;

public class MainConsole {

    public static void main(String[] args) throws ImageProcessingException, IOException {
        Console console = new Console(args);
        System.out.println(console.execute());
        System.exit(0);
    }
}
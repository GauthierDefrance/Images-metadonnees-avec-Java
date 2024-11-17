package Test;
import java.io.IOException;

import Test.Bob;

public class BobTester {
	private Bob b;
	public static void main(String[] args) {
		try {
			Bob b = new Bob(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

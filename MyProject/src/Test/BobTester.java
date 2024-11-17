package Test;
import java.io.IOException;

import Test.Bob;

public class BobTester {
	private Bob b;
	public static void main(String[] args) {
		try {
			Bob b = new Bob(args[0]);
			System.out.println(b.getMetaData());
			System.out.println(b.getMime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

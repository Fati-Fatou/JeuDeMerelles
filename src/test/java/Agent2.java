import java.util.Scanner;

public class Agent2 {

	public static void main(String[] args) {
		ParsingAgent agent = new RandomAgent();
		
		Scanner scanner = new Scanner(System.in);

		agent.mainLoop(scanner, System.out);
    }
}

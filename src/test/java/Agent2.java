import java.util.Scanner;

public class Agent2 {

	public static void main(String[] args) {
		FirstMoveAgent agent = new FirstMoveAgent();
		
		Scanner scanner = new Scanner(System.in);

		agent.mainLoop(scanner, System.out);
    }
}

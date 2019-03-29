import java.util.Scanner;

public class Agent1 {
    
	public static void main(String[] args) {
		//ParsingAgent agent = new SimpleMillAgent();
		ParsingAgent agent = new RandomAgent();
		
		Scanner scanner = new Scanner(System.in);

		agent.mainLoop(scanner, System.out);
    }
}

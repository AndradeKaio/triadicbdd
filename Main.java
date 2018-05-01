import net.sf.javabdd.BDD;

public class Main {
	public static void main(String [] args) {
		ContextToBDD ctb = new ContextToBDD(3, 2, 3, "src/cxt.cxt");
		BDD context = null;
		
		context = ctb.createBDD();
		context.printDot();
	}
}

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;


public class Teste {
	
	
	
	
	
	
	
	
	 public BDD incidencesToBDD() {
		 BDD context = null;
		 
		 return context;
	 }
	 
	 
	public static void main(String[] args) {
		
		
		 BDDFactory bDDFactory;
		 bDDFactory = BDDFactory.init(1000, 1000);
		 bDDFactory.setVarNum(30);
		 

		 BDD bddContext = null;
		 
		 BDD bddVarA1V = bDDFactory.ithVar(1);
		 BDD bddVarA1F = bDDFactory.nithVar(1);
		 BDD bddVarA2V = bDDFactory.ithVar(2);
		 BDD bddVarA2F = bDDFactory.nithVar(2);
		 BDD bddVarA3V = bDDFactory.ithVar(3);
		 BDD bddVarA3F = bDDFactory.nithVar(3);
		 BDD bddVarA4V = bDDFactory.ithVar(4);
		 BDD bddVarA4F = bDDFactory.nithVar(4);

		 
		 /* ??
		 BDD ob1 = 	bddVarA1V.and(bddVarA2F).and(bddVarA3V);
		 BDD ob2 = 	bddVarA1F.and(bddVarA2V).and(bddVarA3F);
		 BDD ob3 = 	bddVarA1V.and(bddVarA2V).and(bddVarA3V);
	 	*/
		 BDD ob1 = 	bddVarA1V.and(bddVarA2F).and(bddVarA3V);
		 BDD ob2 = 	bddVarA1F.and(bddVarA2V).and(bddVarA3F);
		 BDD ob3 = 	bddVarA1F.and(bddVarA2F).and(bddVarA3V);
		 
		 
		 bddContext = ob1;
		 bddContext = bddContext.or(ob2);
		 bddContext = bddContext.or(ob3);
		 
		 bddContext.printSet();
		 bddContext.printDot();
		 
		 BDD result = bddVarA3V;
		 //result = result.and(bddVarA2V);
		 result = result.and(bddContext);
		 
		 result.printDot();
		 result.printSet();
		 
		 
		 
		 
	 			
	}
}

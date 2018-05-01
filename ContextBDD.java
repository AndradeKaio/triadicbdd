import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

public class ContextBDD {

	
	private static BDDFactory bDDFactory;
	
	private final static String CONTEXT_FILE = "src/cxt.cxt";
	
	private static short atrDim;
	private static short objDim;
	private static short cndDim;
	
	private static List<Incidences> context;
	
	
	public static List<String> readContext() {
		
		
		
		List<String> cxt = new ArrayList<String>();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(CONTEXT_FILE);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
				cxt.add(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return cxt;
	}
	
	
	
	public static BDD recoverAtr(BDD atr) {
		BDD result = null;
		
		return result;
	}
	public static BDD recoverCnd(BDD cnd) {
		BDD result = null;
		
		return result;
	}
	
	
	
	
	
	
	
	
	/*
	 * Receives a list of incidents from a triad context in trias format and returns a BDD. 
	 */
	
	public static BDD createBDD(List<String> cxt) {
		
		short dim = (short) (atrDim * cndDim);
		BDD context = null;
		BDD falseAtr [] = new BDD [dim+1];
		BDD trueAtr  [] = new BDD [dim+1];
		BDD obj		 [] = new BDD [objDim+1];
		boolean [] initObj = new boolean[objDim+1];
		boolean [][] inc = new boolean[objDim][dim];
		/*
		for (short i = 0; i < dim+1; i++) {
			trueAtr[i] = bDDFactory.ithVar(i);
			falseAtr[i] = bDDFactory.nithVar(i);
		}*/
		



		int size = cxt.size();
		String line = null;
		int p, o, a, c = 0;
		for (int i = 0; i < size; i++) {
			line = cxt.get(i);
			
			o = Character.getNumericValue(line.charAt(0));
			a = Character.getNumericValue(line.charAt(2));
			c = Character.getNumericValue(line.charAt(4));
			
			
			
			
			// Posicao da variavel que recebera and com o objeto ou o inicializara
			p = (c*atrDim)- Math.abs(a-atrDim);
			// Incidencia inicializada na posicao respectiva
			trueAtr[p] = bDDFactory.ithVar(p);
			
			inc[o-1][p-1] = true;
			
			// Se o objeto ja foi inicializado, operacao and
			if(initObj[o] == true) {		
				obj[o] = obj[o].and(trueAtr[p]);
			}
			// Se nao, inicializar o objeto e marcar como incializado
			else {
				obj[o] = trueAtr[p];
				initObj[o] = true;
			}
		}
		

		/*
		for (int i = 0; i <= dim; i++) {
			if(trueAtr[i] == null) {
				System.out.println(i+ " é nulo!");
			}
		}*/
		
		for (int i = 0; i < objDim; i++) {
			for (int j = 0; j < dim; j++) {
				if(inc[i][j] == false) {
					falseAtr[j+1] = bDDFactory.nithVar(j+1);
					obj[i+1] = obj[i+1].and(falseAtr[j+1]);
				}
			}
		}
		
		if(obj.length > 0) {
			context = obj[1];
			for(int i = 2; i<=objDim; i++) {
				context = context.or(obj[i]);
			}
		}
		
		return context;
	}
	
	public static void main(String[] args) {
		

		bDDFactory = BDDFactory.init(1000, 1000);
		bDDFactory.setVarNum(30);
		/*
		BDD [] trueAtr   = new BDD[6];
		BDD [] falseAtr  = new BDD[6];
		BDD [] obj		 = new BDD[3];
		for (int i = 0; i < 6; i++) {
			trueAtr[i] = bDDFactory.ithVar(i);
			falseAtr[i] = bDDFactory.nithVar(i);
		}
		

		
		obj[0] = trueAtr[0].and(falseAtr[1]).and(falseAtr[2]).and(trueAtr[3]).and(trueAtr[4]).and(falseAtr[5]);
		obj[1] = trueAtr[0].and(falseAtr[1]).and(trueAtr[2]).and(falseAtr[3]).and(falseAtr[4]).and(trueAtr[5]);
		obj[2] = falseAtr[0].and(trueAtr[1]).and(falseAtr[2]).and(trueAtr[3]).and(trueAtr[4]).and(falseAtr[5]);
		
		BDD bddContext = obj[2];
		bddContext = bddContext.or(obj[1]);
		bddContext = bddContext.or(obj[0]);
		bddContext.printDot();
		
		*/
		atrDim = 2;
		cndDim = 3;
		objDim = 3;
		List<String> listContext = readContext();
		BDD bddContext = createBDD(listContext);
		
		bddContext.printDot();
		

	}
}

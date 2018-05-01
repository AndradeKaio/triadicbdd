import java.util.List;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

/**
 * PONTIFICIA UNIVERSIDADE CATOLICA DE MINAS GERAIS
 * This program implements a representation of a triadic context (U, T, K) using a Binary Decision Diagram structure.
 * The context given follows the TRIAS algoritm pattern in a text file.
 * Some operations was implement to recover attributes and condicions shared by some objects.
 * 
 * @author  Kaio Henrique
 * @version 1.0
 * @since   2018-05-01
 * deandradekaio@gmail.com
 *
 */
public class BDDContext {

	/* public attributes */
	public int atrDim;
	public int objDim;
	public int cndDim;
	public int dim;
	
	/* private attributes */
	private BDD fAtr[];
	private BDD tAtr[];
	private BDD obj[];
	private boolean iObj[];
	
	private BDD context;
	private BDDFactory bDDFactory;
	
	// context incidences
	private List<Incidences> incidences;
	
	// Null constructor
	public BDDContext() {
		
	}
	
	/**
	 * BDD context constructor
	 * @param atrDim dimension of attributes
	 * @param objDim dimension of objects
	 * @param cndDim dimension of conditions
	 */
	public BDDContext(int atrDim, int objDim, int cndDim) {
		this.atrDim = atrDim;
		this.objDim = objDim;
		this.cndDim = cndDim;
		this.dim    = atrDim * cndDim;
		
		this.fAtr = new BDD[dim];
		this.tAtr = new BDD[dim];
		this.obj  = new BDD[objDim];
		this.iObj = new boolean[atrDim*cndDim];
		
		context = null;
		
		bDDFactory = BDDFactory.init(1000, 1000);
		bDDFactory.setVarNum(dim);
		
		incidences = ContextReader.readContext();
	}
	
	
	
	/**
	 * Receibe current attribute and condicion and returns
	 * the bdd var position.
	 * @param a current attribute
	 * @param c current doncidcion
	 * @return position of a bdd var
	 */
	public int position(int a, int c) {
		return (c*atrDim)- Math.abs(a-atrDim);
	}
	
	/**
	 * 
	 * @return BDD context.
	 */
	public BDD createBDD() {
		BDD context = null;
		int count = 0;
		int p = 0;
		int size  = incidences.size();
		Incidences<Integer, Integer, Integer> inc = null;
		
		BDD var = null;
		
		for (int o = 0; o < objDim; o++) {
			for (int a = 0; a < atrDim*cndDim; a++) {
				
				// if list of incidents isn't empty
				if(count < size) {
					inc = incidences.get(count);
					
					// init BDD vars
					tAtr[p] = bDDFactory.ithVar(p);
					fAtr[p] = bDDFactory.nithVar(p);
					
					// aux receive false BDD var
					var = fAtr[p];
					
					
					// if context position is equals to incidence of list, then
					// create a marking
					if((int)inc.getObject() == o && (int)inc.getAttribute() == a) {
						// get var index
						p = position(a, (int)inc.getCondition());
						var = tAtr[p];					
					}

				}else {
					fAtr[a] = bDDFactory.nithVar(a);
					var = fAtr[a];
				}
				
				if(iObj[o])
					// current object receives a AND operation
					obj[o] = obj[o].and(var);
				// if not
				else {
					// current object its initialized
					obj[o] = var;
					iObj[o] = true;
				}
				count++;	
			}
		}	
		return context;
	}
	
	
	/**
	 * Receives a atribute index and return
	 * all objects that share that atribute.
	 * @param atr attribute search
	 * @return BDD resulted by AND operation.
	 */
	public BDD recoverAtr(int atr) {
		BDD result = null;
		if(tAtr[atr] != null)
			result = tAtr[atr];
		
		return context.and(result);
	}
	
	/**
	 * 
	 * @param atr
	 * @return
	 */
	public BDD recoverAtr(int [] atr) {
		BDD result = null;

		for (int i = 0; i < atr.length; i++) {
			
		}
		
		return context.and(result);
	}
	
	public BDD recoverCnd(int cnd) {
		BDD result = null;
		
		return result;
	}
	
	
	
}

import java.util.List;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDDFactory;

/**
 * PONTIFICIA UNIVERSIDADE CATOLICA DE MINAS GERAIS
 * This program implements a representation of a triadic context (U, T, K) using a Binary Decision Diagram structure.
 * The context given follows the TRIAS algorithm pattern in a text file.
 * Some operations was implement to recover attributes and condition shared by some objects.
 * 
 * @author  Kaio Henrique
 * @version 1.0
 * @since   2018-05-01
 * deandradekaio@gmail.com
 *
 */
public class ContextToBDD {

	/* public attributes */
	public int atrDim;
	public int objDim;
	public int cndDim;
	public int dim;
	public String contextFile;
	
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
	public ContextToBDD() {
		
	}
	
	/**
	 * BDD context constructor
	 * @param atrDim dimension of attributes
	 * @param objDim dimension of objects
	 * @param cndDim dimension of conditions
	 */
	public ContextToBDD(int objDim, int atrDim, int cndDim, String contextFile) {
		this.atrDim = atrDim;
		this.objDim = objDim;
		this.cndDim = cndDim;
		this.dim    = atrDim * cndDim;
		this.contextFile = contextFile;
		
		this.fAtr = new BDD[dim];
		this.tAtr = new BDD[dim];
		this.obj  = new BDD[objDim];
		this.iObj = new boolean[dim];
		
		context = null;
		
		bDDFactory = BDDFactory.init(1000, 1000);
		bDDFactory.setVarNum(dim);
		
		incidences = ContextReader.readContext(contextFile);
	}
	
	
	
	/**
	 * Receive current attribute and condition and returns
	 * the BDD var position.
	 * @param a current attribute
	 * @param c current condition
	 * @return position of a BDD var
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
		Incidences<Integer, Integer, Integer> inc = incidences.get(0);
		
		BDD var = null;
			
		for (int o = 0; o < objDim; o++) {
			for (int a = 0; a < dim; a++) {
				
				fAtr[a] = bDDFactory.nithVar(a);
				var = fAtr[a];
				
				// if list of incidents isn't empty
				if(count < size) {
					// get var index
					p = position((int)inc.getAttribute(), (int)inc.getCondition());

					// if context position is equals to incidence of list, then
					// create a marking
					if((int)inc.getObject() == (o+1) && (a+1) == p) {

						tAtr[p-1] = bDDFactory.ithVar(p-1);
						var = tAtr[p-1];

						count++;
						if(count < size)
							inc = incidences.get(count);
					}

				}
				// if object isn't initialized
				if(iObj[o]) 
					// current object receives a AND operation
					obj[o] = obj[o].and(var);
				// if not
				else {
					// current object its initialized
					obj[o] = var;
					iObj[o] = true;
				}	
			}
		}
		
		context = obj[0];
		for (int i = 1; i < obj.length; i++) {
			context = context.or(obj[i]);
		}
		return context;
	}
	
	
	/**
	 * Receives a attribute index and return
	 * all objects that share that attribute.
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
	 * Receive a list of attributes and return 
	 * the objects that share theses attributes.
	 * @param atr
	 * @return
	 */
	public BDD recoverAtr(int [] atr) {
		BDD result = null;
		
		result = tAtr[atr[0]];
		for (int i = 1; i < atr.length; i++) {
			result = result.and(tAtr[atr[i]]);
		}
		
		return context.and(result);
	}
	
	public BDD recoverCnd(int cnd) {
		BDD result = null;
		
		return result;
	}
	
	
	
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContextReader {

	
	
	public static List<Incidences> readContext(String contextFile) {
			
			List<Incidences> incidences = new ArrayList<>();
			
			
			BufferedReader br = null;
			FileReader fr = null;
	
			try {
				fr = new FileReader(contextFile);
				br = new BufferedReader(fr);
	
				String line;
				int o, a, c = 0;
				while ((line = br.readLine()) != null) {
					//System.out.println(sCurrentLine);

					o = Character.getNumericValue(line.charAt(0));
					a = Character.getNumericValue(line.charAt(2));
					c = Character.getNumericValue(line.charAt(4));
					incidences.add(new Incidences<Integer, Integer, Integer>(o, a, c));
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
			return incidences;
		}
}

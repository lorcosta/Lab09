package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> idMap;
	private BordersDAO dao= new BordersDAO();
	
	public Model() {
		idMap=new HashMap<Integer,Country>();
		dao.loadAllCountries(idMap);

	}
	/**
	 * Crea il grafo aggiungendo vertici e archi
	 * @param anno
	 */
	public void createGraph(int anno) {
		//trovo gli archi
		//Gli archi mi vengono restituiti dal database che li seleziona in base all'anno
		List<Border> archi=dao.getCountryPairs(anno, idMap);
		//da questi archi devo prendere i vertici da aggiungere al grafo
		List<Country> vertici=new ArrayList<>();
		for(Border b:archi) {
			if(!vertici.contains(b.getC1()))
				vertici.add(b.getC1());
			if(!vertici.contains(b.getC2()))
				vertici.add(b.getC2());
		}
		grafo= new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		//aggiungo i vertici al grafo
		Graphs.addAllVertices(this.grafo,vertici);
		//aggiungo gli archi al grafo
		for(Border b:archi) {
			this.grafo.addEdge(b.getC1(),b.getC2());
		}
		System.out.println("#Vertici:"+this.grafo.vertexSet().size()+"-#Archi:"+this.grafo.edgeSet().size());
		
	}
	public String statiConfinanti(){
		String result="Stato:#stati confinati\n";
		for(Country c: this.grafo.vertexSet()) {
			result+=""+c.getCountryName()+":"+this.grafo.degreeOf(c)+"\n";
		}
		//cerco le componenti connesse e le stampo
		ConnectivityInspector<Country, DefaultEdge> inspector=new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		List<Set<Country>> compConnesse=inspector.connectedSets();
		result+="Le componenti connesse sono "+compConnesse.size();
		return result;
	}
	public List<Country> listaStati(){
		return dao.loadAllCountries();
	}
	
	public Set<Country> cercaSitiRaggiungibili(Country source){
		ConnectivityInspector<Country, DefaultEdge> inspector=new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		Set<Country> sitiRaggiungibili=inspector.connectedSetOf(source);
		return sitiRaggiungibili;
	}
	
	public List<Country> cercaSitiRaggiungibiliInProfondita(Country source){
		List<Country> visita= new ArrayList<>();
		DepthFirstIterator<Country, DefaultEdge> dfv=new DepthFirstIterator<>(this.grafo,source);
		while(dfv.hasNext()) {
			visita.add(dfv.next());
		}
		return visita;
	}
	
	public List<Country> cercaRicorsivamenteSitiRaggiungibili(Country source){
		List<Country> visita=new ArrayList<>();
		//fin tanto che ci sono nuovi vertici da scoprire
		visita.add(source);
		cerca(visita);
		return visita;
	}
	private void cerca(List<Country> visita) {//visita sarebbe la mia soluzione parziale
		for(Country c:Graphs.neighborSetOf(this.grafo, visita.get(visita.size()-1))){//se l'ultimo arco aggiunto ha dei 'prossimi'
			if(!visita.contains(c)) {
				visita.add(c);
				cerca(visita);
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package com.dexterlabs.xpaths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetAllXpaths {
	private final static List<String> path = new ArrayList<>();
	private final static List<String> all = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		 org.jsoup.nodes.Document doc = Jsoup.connect("https://w3schools.com/").get();
		 parse(doc);
		/* Elements elements = doc.body().select("*");
		ArrayList all = new ArrayList();
		        for (org.jsoup.nodes.Element element : elements) {
		            if (!element.ownText().isEmpty()) {

		                StringBuilder path = new StringBuilder(element.nodeName());
		                String value = element.ownText();
		                Elements p_el = element.parents();

		                for (org.jsoup.nodes.Element el : p_el) {
		                    path.insert(0, el.nodeName() + '/');
		                }
		                all.add(path + " = " + value + "\n");
		                System.out.println(path +" = "+ value);
		            }
		        }
*/
		        
		 List<String> allElementsXpath = getAll();
		 
		 for(int i =0 ;i<allElementsXpath.size();i++){
			 System.out.println(allElementsXpath.get(i).toString());
		 }
		
	}
	
	
	public static List<String> getAll() {
	    return Collections.unmodifiableList(all);
	}

	public static void parse(Document doc) {
	    path.clear();
	    all.clear();
	    parse(doc.children());
	}

	private static void parse(List<Element> elements) {
	    if (elements.isEmpty()) {
	        return;
	    }
	    Map<String, List<Element>> grouped = elements.stream().collect(Collectors.groupingBy(Element::tagName));

	    for (Map.Entry<String, List<Element>> entry : grouped.entrySet()) {
	        List<Element> list = entry.getValue();
	        String key = entry.getKey();
	        if (list.size() > 1) {
	            int index = 1;
	            // use paths with index
	            key += "[";
	            for (Element e : list) {
	                path.add(key + (index++) + "]");
	                handleElement(e);
	                path.remove(path.size() - 1);
	            }
	        } else {
	            // use paths without index
	            path.add(key);
	            handleElement(list.get(0));
	            path.remove(path.size() - 1);
	        }
	    }

	}

	private static void handleElement(Element e) {
	    String value = e.ownText();
	    if (!value.isEmpty()) {
	        // add entry
	        all.add(path.stream().collect(Collectors.joining("/")) + " = " + value);
	    }
	    // process children of element
	    parse(e.children());
	}

}

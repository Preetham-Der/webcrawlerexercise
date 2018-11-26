package webcrawler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;



public class LinkFinderAction extends RecursiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5279045342802325412L;
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");
	private String url;
    private LinkHandler cr;
    private String singleDomainName;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinderAction(String url, LinkHandler cr,String singleDomainName) {
        this.url = url;
        this.cr = cr;
        this.singleDomainName=singleDomainName;
    }
    
    

    @Override
    public void compute() {
    	String href = url.toLowerCase();
 
        if (shouldVisit(href)) {
            try {
                List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
                URL uriLink = new URL(url);
                Parser parser = new Parser(uriLink.openConnection());
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
                List<String> sublinks = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    LinkTag extracted = (LinkTag) list.elementAt(i);

                    if (!extracted.extractLink().isEmpty()
                            && !cr.visited(extracted.extractLink())) {

                        actions.add(new LinkFinderAction(extracted.extractLink(), cr,this.singleDomainName));
                    }
                    if(isExternal(extracted.extractLink()) || !isFilteredStatic(extracted.extractLink())) {
                    	sublinks.add(extracted.extractLink());
                    }
                }                                               
                cr.addVisited(url,sublinks);

                if ((cr.size()% 100) == 0) {
                    System.out.println("Time for visiting "+ cr.size()+" distinct links= " + (System.nanoTime() - t0));                   
                }

                //invoke recursively
                invokeAll(actions);
            } catch (Exception e) {
                //ignore 404, unknown protocol or other server errors
            }
        }
    }
    
    public boolean isExternal(String url) {
    	return (singleDomainName!=null && !singleDomainName.isEmpty())? !url.startsWith(singleDomainName):false;
    }
    
    public boolean isFilteredStatic(String url) {
    	return FILTERS.matcher(url).matches();
    	/*return !(url.endsWith("css") || url.endsWith("jpg") 
    			|| url.endsWith("gif") || url.endsWith("js") 
    			|| url.endsWith("css") || url.endsWith("jpg")
    			|| url.endsWith("png") || url.endsWith("mp3")
    			|| url.endsWith("mp4") || url.endsWith("zip"));*/
    }
    
    
    public boolean isVisited(String url) {
    	return this.cr.visited(url);
    }
    
    public boolean shouldVisit(String url) {
    	return !isExternal(url) && !isFilteredStatic(url) && !isVisited(url);
    }

}

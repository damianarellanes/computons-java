package category.finset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Damian Arellanes
 */
public class Function {
  
  public final int[] image; // This is the actual mapping
  public final int domain;
  public final int codomain;
  private final HashMap<Integer, ArrayList<Integer>> fibers;  
  
  public Function(int[] map, int codomain, 
    HashMap<Integer, ArrayList<Integer>> fibers) {
    
    this.image = map.clone();
    this.domain = this.image.length;
    this.codomain = codomain;
    this.fibers = fibers;
  }
  
  public int at(int x) {
        
    if (x >= image.length) throw new IllegalArgumentException("Outside domain");    
    return image[x];
  }
    
  public boolean hasEndpoints(Finset domain, Finset codomain) {
    return (this.domain == domain.size && this.codomain == codomain.size);
  }
  
  public boolean hasDomain(Finset domain) {
    return (this.domain == domain.size);
  }
  
  public boolean hasCodomain(Finset codomain) {
    return (this.codomain == codomain.size);
  }
  
  public ArrayList<Integer> fiberOf(int x) {    
    return (fibers.containsKey(x)) ? fibers.get(x) : new ArrayList<>();
  }
  
  @Override
  public String toString() {
    return Arrays.toString(image);
  }
}

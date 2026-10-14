package category.finset;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Damian Arellanes
 */
public final class Misc {
  
  // This is basically an affine function f(x)=ax+c
  // With a=1 and c=imageStart
  public static Function offsetFunction(int imageStart, int imageEnd, 
    int codomain) {
    
    if(imageEnd < imageStart) 
      throw new IllegalArgumentException("Image end >= Image start");
        
    int[] map = new int[imageEnd - imageStart];
    HashMap<Integer, ArrayList<Integer>> fibers = new HashMap<>();
    for(int i = imageStart, j = 0; i < imageEnd; i++, j++) {
      
      map[j] = i;
      
      if (!fibers.containsKey(map[j])) fibers.put(map[j], new ArrayList<>());
      fibers.get(map[j]).add(j);
    }
    
    return new Function(map, codomain, fibers);
  }
  
  // Defines the higher-order function g . f (i.e., g after f)
  public static Function compose(Function f, Function g) {
    
    if (f.codomain != g.domain)
      throw new IllegalArgumentException("Cod(f) must match Dom(g)");
    
    int[] map = new int[f.domain];
    HashMap <Integer, ArrayList<Integer>> fibers = new HashMap<>();
    for(int x = 0; x < f.domain; x++) {
      
      map[x] = g.at(f.at(x));
      fibers.putIfAbsent(map[x], new ArrayList<>());
      fibers.get(map[x]).add(x);
    }
    
    return new Function(map, g.codomain, fibers);
  }
}

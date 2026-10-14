package category.comp.morphisms;

import category.comp.objects.Computon;
import category.finset.Function;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Damian Arellanes
 */

public class Morphism {
  
  public final Computon source;
  public final Computon target;
  public final Function alphaU;
  public final Function alphaP;
  public final Function alphaI;
  public final Function alphaO;
  public final Function alphaSigma;
  public final Function alphaD;
  
  public Morphism (Computon source, Computon target, Function u, Function p, 
    Function i, Function o) {
    
    this.source = source;
    this.target = target;        
    this.alphaU = u;
    this.alphaP = p;
    this.alphaI = i;
    this.alphaO = o;
    
    if (source.colours.size > target.colours.size)
      throw new IllegalArgumentException("Sigma0 is not a subset of Sigma1");
    if (source.devices.size > target.devices.size)
      throw new IllegalArgumentException("Devices0 is not a subset of Devices1");
    
    // Creates the inclusion mappings for colours and devices
    int[] coloursMap = new int[source.colours.size];
    HashMap<Integer, ArrayList<Integer>> coloursFiber = new HashMap<>();
    int[] devicesMap = new int[source.devices.size];
    HashMap<Integer, ArrayList<Integer>> devicesFiber = new HashMap<>();
    
    int max = Math.max(source.colours.size, source.devices.size);    
    for (int element = 0; element < max; element++) {      
      
      if (element < source.colours.size) {
        coloursMap[element] = element;        
        coloursFiber.put(element, new ArrayList<>(List.of(element)));
      }
      if (element < source.devices.size) {
        devicesMap[element] = element;
        devicesFiber.put(element, new ArrayList<>(List.of(element)));
      }      
    }
    // Finishes the creation of the inclusion mappings    
    this.alphaSigma = new Function(coloursMap, target.colours.size, coloursFiber);
    this.alphaD = new Function(devicesMap, target.devices.size, devicesFiber);
  }
  
  // Only use this if the sigma and d functions are shown to be inclusions
  public Morphism (Computon source, Computon target, Function u, Function p, 
    Function i, Function o, Function sigma, Function d) {    
    
    if (source.colours.size > target.colours.size)
      throw new IllegalArgumentException("Sigma0 is not a subset of Sigma1");
    if (source.devices.size > target.devices.size)
      throw new IllegalArgumentException("Devices0 is not a subset of Devices1");
    
    this.source = source;
    this.target = target;        
    this.alphaU = u;
    this.alphaP = p;
    this.alphaI = i;
    this.alphaO = o;    
    this.alphaSigma = sigma;
    this.alphaD = d;
  }
  
  public Boolean hasEndpoints (Computon domain, Computon codomain) {
    return domain == source && codomain == target;
  }
  
  /*public Boolean isInVecI (int domainPort) {
    
    if(domainPort < 0 || domainPort >= source.ports.size)
      throw new IllegalArgumentException("Domain port outside boundaries.");
    
    return target.bP[alphaP.at(domainPort)] > source.bP[domainPort];        
  }
  
  public Boolean isInVecO (int domainPort) {
    
    if(domainPort < 0 || domainPort >= source.ports.size)
      throw new IllegalArgumentException("Domain port outside boundaries.");
    
    return target.Pb[alphaP.at(domainPort)] > source.Pb[domainPort];        
  }*/ 
}

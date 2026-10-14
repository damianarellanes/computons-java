package operation;

import category.comp.objects.Computon;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Damian Arellanes
 */
public class FlatComputon {
  
  public final Computon structure;
  
  // Serves as hashcode to identify units with shared input ports
  public final Set<Integer>[] inputsOfUnit; 
  
  public FlatComputon (Computon computon) {
    
    this.structure = computon;
    this.inputsOfUnit = new HashSet[computon.units.size];
    
    // Constructs the mapping: unit -> input ports set
    for (int inflow = 0; inflow < computon.inflows.size; inflow++) {
      
      if(inputsOfUnit[computon.tau.at(inflow)] == null) 
        inputsOfUnit[computon.tau.at(inflow)] = new HashSet<>(); 
      inputsOfUnit[computon.tau.at(inflow)].add(computon.s.at(inflow));
    }
  }
  
  @Override
  public String toString () {
    return structure.toString();
  }
}

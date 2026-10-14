package operation;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Damian Arellanes
 */
public class State {
    
  public final Object[] map;
  private BitSet isReady; 
  private final FlatComputon flat;
  private final Config config;  
  
  public State (FlatComputon flatComputon, Config config) {
    this.map = new Object[flatComputon.structure.ports.size];
    this.isReady = new BitSet(flatComputon.structure.units.size);
    this.flat = flatComputon;
    this.config = config;
  }
  
  public Boolean isInitial () {
    
    for(int port = 0; port < flat.structure.ports.size; port++) {
      
      if (!flat.structure.isInport.get(port)) {
        
        if (map[port] == null) continue;
        else return false;
      }   
      
      checkType(port, map[port]);      
    }    
    return true;
  }
  
  // A state is final if there are not any ready units
  public Boolean isFinal () {
    return isReady.cardinality() == 0; 
  }
  
  public void checkType (int port, Object value) {
    
    // Type checking: throws an exception if the port value is invalid
    if (value.getClass() != config.types.get(flat.structure.c.at(port)))
      throw new IllegalStateException(
        "Invalid type value at port " + port + ". Expected type: " 
          + config.types.get(flat.structure.c.at(port))
      );
  }
  
  public BitSet updateReadyUnits () {
    
    this.isReady = new BitSet(flat.structure.units.size);
    Map<Set<Integer>, Integer> seen = new HashMap<>();
    for(int unit = 0; unit < flat.structure.units.size; unit++) {
            
      // Randomly chooses an enabled unit among those sharing input ports
      if (isEnabled(unit)) {
        
        Set<Integer> inputs = flat.inputsOfUnit[unit];
        
        // If no shared inputs have been seen yet, choose the current unit
        if (!seen.containsKey(inputs)) {

          seen.put(inputs, unit);
          isReady.set(unit);
        } 
        // If shared input have been seen and rand value <= 0.5, 
        // replace the previous unit with the current unit; else, leave previous
        else if (Math.random() <= 0.5)  { 

          isReady.clear(seen.get(inputs));
          seen.put(inputs, unit);
          isReady.set(unit);
        }
      }
    }

    return this.isReady;    
  }

  private Boolean isEnabled (int unit) {
    
    // All units are assumed to be enabled initially
    // it checks tau^{-1}(u) for each unit 
    Boolean isEnabled = true;
    for (int inflow : flat.structure.tau.fiberOf(unit)) {

      // If a port in •u has no value, u cannot be enabled
      if (map[flat.structure.s.at(inflow)] == null) {
        isEnabled = false;
        break;
      }
    }
    return isEnabled;
  }
  
  public BitSet getReadyUnits () {
    return this.isReady;
  }
  
  @Override
  public String toString () {
    return Arrays.deepToString(map);
  }
}

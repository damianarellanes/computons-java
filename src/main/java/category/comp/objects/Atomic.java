package category.comp.objects;

import category.finset.FBuilder;
import category.finset.Finset;
import category.finset.FunctionBuilder;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.HashMap;

/**
 * @author Damian Arellanes
 * |U|=1 (like a primitive but without restrictions)
 * Useful for creating replicating units
 */
public class Atomic extends Computon {

  public Atomic(Finset ports, Finset inflows, Finset outflows, Finset colours, 
    Finset devices, FunctionBuilder s, FunctionBuilder t, 
    SurjectiveFunctionBuilder sigma, SurjectiveFunctionBuilder tau, FBuilder c, 
    SurjectiveFunctionBuilder r, FBuilder f, BitSet isInport, BitSet isOutport, 
    int[] bP, int[] Pb, HashMap<Integer, String>[] inLabels) {
    
    super(new Finset(1), ports, inflows, outflows, colours, devices, s, t, 
      sigma, tau, c, r, f, isInport, isOutport, bP, Pb, inLabels);
    
    // An atomic computon must alway have a non-empty set of devices
    // Zero must be included, representing the control echoing    
    if (devices.size <= 0)
      throw new IllegalArgumentException("An atomic must have devices");    
  }  
}
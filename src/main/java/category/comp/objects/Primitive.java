package category.comp.objects;

import category.finset.FBuilder;
import category.finset.Finset;
import category.finset.InjectiveFunctionBuilder;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.HashMap;

/**
 * @author Damian Arellanes
 * |U|=1, ports=inflows+outflows, s injective, t injective
 */
public class Primitive extends Computon {

  public Primitive(Finset ports, Finset inflows, Finset outflows, Finset colours, 
    Finset devices, InjectiveFunctionBuilder s, InjectiveFunctionBuilder t, 
    SurjectiveFunctionBuilder sigma, SurjectiveFunctionBuilder tau, FBuilder c, 
    SurjectiveFunctionBuilder r, FBuilder f, BitSet isInport, BitSet isOutport, 
    int[] bP, int[] Pb, HashMap<Integer, String>[] inLabels) {
    
    super(new Finset(1), ports, inflows, outflows, colours, devices, s, t, 
      sigma, tau, c, r, f, isInport, isOutport, bP, Pb, inLabels);
    
    // A primitive computon must alway have a non-empty set of devices
    // Zero must be included, representing the control echoing    
    if (devices.size <= 0)
      throw new IllegalArgumentException("A primitive must have devices");
    
    // Verifies whether all ports lie at the interface
    if (ports.size != inflows.size + outflows.size)
      throw new IllegalArgumentException("All ports must lie at the interface.");
  }  
}
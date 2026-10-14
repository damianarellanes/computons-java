package category.comp.objects;

import category.finset.EmptyFunctionBuilder;
import category.finset.FBuilder;
import category.finset.Finset;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.HashMap;

/**
 * @author Damian Arellanes
 * |U|=|I|=|O|=0
 * Hence, |bP|=|Pb|=0
 */
public class Trivial extends Computon {

  public Trivial(Finset ports, Finset colours, Finset devices, 
    FBuilder c, BitSet isInport, BitSet isOutport) {
    
    super(new Finset(0), ports, new Finset(0), new Finset(0), colours, devices, 
        new EmptyFunctionBuilder(ports), new EmptyFunctionBuilder(ports), 
        new SurjectiveFunctionBuilder(new Finset(0), new Finset(0)), 
        new SurjectiveFunctionBuilder(new Finset(0), new Finset(0)), c, 
        new SurjectiveFunctionBuilder(new Finset(0), new Finset(0)), 
        new EmptyFunctionBuilder(devices), 
        isInport, isOutport, new int[ports.size], new int[ports.size], 
        new HashMap[0]);
  }
}

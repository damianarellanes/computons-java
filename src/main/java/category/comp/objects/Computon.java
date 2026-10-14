package category.comp.objects;

import category.finset.FBuilder;
import category.finset.Finset;
import category.finset.Function;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.Map;

/**
 *
 * @author Damian Arellanes
 */
public class Computon {

  public final Finset units;
  public final Finset ports;
  public final Finset inflows;
  public final Finset outflows;
  public final Finset colours;
  public final Finset devices;
  public final Function s;
  public final Function t;
  public final Function sigma;
  public final Function tau;
  public final Function c;
  public final Function r;
  public final Function f;
      
  public final BitSet isInport, isOutport;  
  public final int[] bP, Pb;
  
  public final Map<Integer, String>[] inLabels;

  public Computon (Finset units, Finset ports, Finset inflows, Finset outflows, 
    Finset colours, Finset devices, FBuilder s, FBuilder t, 
    SurjectiveFunctionBuilder sigma, SurjectiveFunctionBuilder tau, 
    FBuilder c, SurjectiveFunctionBuilder r, FBuilder f, 
    BitSet isInport, BitSet isOutport, int[] bP, int[] Pb, 
    Map<Integer, String>[] inLabels) {    
    
    this.units = units;
    this.ports = ports;
    this.inflows = inflows;
    this.outflows = outflows;
    this.colours = colours;
    this.devices = devices;
    this.s = s.get();
    this.t = t.get();
    this.sigma = sigma.get();
    this.tau = tau.get();
    this.c = c.get();
    this.r = r.get();
    this.f = f.get();
    
    this.isInport = isInport;
    this.isOutport = isOutport;
    this.bP = bP;
    this.Pb = Pb;
    this.inLabels = inLabels;
  }
  
  public boolean hasInternalPort (int port) {    
    return !isInport.get(port) && !isOutport.get(port);
  }
  
  @Override
  public String toString() {
    return 
      "c = " + c.toString() + "\n" +
      "s = " + s.toString() + "\n" +
      "t = " + t.toString() + "\n" +
      "sigma = " + sigma.toString() + "\n" +
      "tau = " + tau.toString() + "\n" +
      "r = " + r.toString() + "\n" +
      "f = " + f.toString() + "\n" +
      "inports = " + isInport + "\n" +
      "outports = " + isOutport + "\n";
  }  
}

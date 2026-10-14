package category.comp.objects;

import category.finset.Finset;
import category.finset.FunctionBuilder;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.HashMap;

/**
 *
 * @author Damian Arellanes
 */
public class AtomicBuilder extends ComputonBuilder <Atomic> {
  
  private final Finset units, ports, inflows, outflows, colours, devices;
  private final SurjectiveFunctionBuilder sigma, tau, r;
  private final FunctionBuilder s, t, c, f;
    
  private final BitSet isInport, isOutport;
  private Boolean hasControlInports, hasControlOutports;
  private final int[] bP, Pb;
  private final HashMap<Integer, String>[] inLabels;
  
  public AtomicBuilder (int ports, int inflows, int outflows, int colours, 
    int devices) {
        
    this.units = new Finset(1);
    this.ports = new Finset(ports);
    this.inflows = new Finset(inflows);
    this.outflows = new Finset(outflows);
    this.colours = new Finset(colours);
    this.devices = new Finset(devices);    
        
    this.sigma = new SurjectiveFunctionBuilder(this.outflows, this.units);
    this.tau = new SurjectiveFunctionBuilder(this.inflows, this.units);
    this.s = new FunctionBuilder(this.inflows, this.ports);
    this.t = new FunctionBuilder(this.outflows, this.ports);
    this.c = new FunctionBuilder(this.ports, this.colours);
    this.r = new SurjectiveFunctionBuilder(this.inflows, this.outflows);
    this.f = new FunctionBuilder(this.outflows, this.devices);
    
    this.isInport = new BitSet(ports);
    this.isOutport = new BitSet(ports);
    this.hasControlInports = false;
    this.hasControlOutports = false;
    this.bP = new int[ports];
    this.Pb = new int[ports];
    this.inLabels = new HashMap[1]; this.inLabels[0] = new HashMap();    
  }
  
  public void label(int port, String label) {
    this.inLabels[0].put(port, label);
  }

  @Override
  public void s(int inflow, int port) {
    
    if(this.s.hasValueAt(inflow)) 
      throw new IllegalArgumentException("Inflows cannot be re-attached.");    
    
    this.Pb[port] = 1;
    this.s.set(inflow, port);    
    this.isInport.set(port);    
  }

  @Override
  public void t(int outflow, int port) {
    
    if(this.t.hasValueAt(outflow)) 
      throw new IllegalArgumentException("Outflows cannot be re-attached.");    
    
    this.bP[port] = 1;    
    this.t.set(outflow, port);
    this.isOutport.set(port);
  }

  @Override
  public void sigma(int outflow, int unit) {
    
    this.sigma.set(outflow, unit);
    
    // If the target port of outflow is 0-typed then we have a control outport
    if(this.c.at(this.t.at(outflow)) == 0) hasControlOutports = true;
  }

  @Override
  public void tau(int inflow, int unit) {
    
    this.tau.set(inflow, unit);
    
    // If the source port of inflow is 0-typed then we have a control inport
    if(this.c.at(this.s.at(inflow)) == 0) hasControlInports = true;
  }

  @Override
  public void c(int port, int colour) {
    this.c.set(port, colour);
  }

  @Override
  public void r(int inflow, int outflow) {
    this.r.set(inflow, outflow);
  }

  @Override
  public void f(int outflow, int device) {
    this.f.set(outflow, device);
  }
  
  @Override
  public boolean allUnitsControlInOut () {            
    if(!hasControlInports) throw new IllegalArgumentException(
      "An atomic's unit must have control inports."
    );
    if(!hasControlOutports) throw new IllegalArgumentException(
      "An atomic's unit must have control outports."
    );    
    return true;
  }
  
  @Override
  public boolean controlInportsOutports () {
    
    if(!hasControlInports) throw new IllegalArgumentException(
      "An atomic must have control inports."
    );
    if(!hasControlOutports) throw new IllegalArgumentException(
      "An atomic must have control outports."
    );    
    return true;
  }
  
  @Override
  public boolean deviceEncapsulation () {
    return true;
  }

  @Override
  public Atomic get() {                
    
    this.s.finalise();
    this.t.finalise();
    this.c.finalise();
    this.sigma.finalise();
    this.tau.finalise();
    this.r.finalise();
    this.f.finalise();   
    
    if(this.inLabels[0].size() < isInport.cardinality())
      throw new IllegalStateException("All inports must have a label");
    
    return new Atomic (ports, inflows, outflows, colours, devices, 
      s, t, sigma, tau, c, r, f, isInport, isOutport, bP, Pb, inLabels);    
  }
}

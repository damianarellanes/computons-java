package category.comp.objects;

import category.finset.Finset;
import category.finset.FunctionBuilder;
import category.finset.InjectiveFunctionBuilder;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.HashMap;

/**
 *
 * @author Damian Arellanes
 */
public class PrimitiveBuilder extends ComputonBuilder <Primitive> {
  
  private final Finset units, ports, inflows, outflows, colours, devices;
  private final InjectiveFunctionBuilder s, t;
  private final SurjectiveFunctionBuilder sigma, tau, r;
  private final FunctionBuilder c, f;
    
  private final BitSet isInport, isOutport;
  private Boolean hasControlInports, hasControlOutports;
  private final int[] bP, Pb;
  private final HashMap<Integer, String>[] inLabels;
  
  public PrimitiveBuilder (int ports, int inflows, int outflows, int colours, 
    int devices) {
        
    this.units = new Finset(1);
    this.ports = new Finset(ports);
    this.inflows = new Finset(inflows);
    this.outflows = new Finset(outflows);
    this.colours = new Finset(colours);
    this.devices = new Finset(devices);    
        
    this.sigma = new SurjectiveFunctionBuilder(this.outflows, this.units);
    this.tau = new SurjectiveFunctionBuilder(this.inflows, this.units);
    this.s = new InjectiveFunctionBuilder(this.inflows, this.ports);
    this.t = new InjectiveFunctionBuilder(this.outflows, this.ports);
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
    
    int previousPort = -1;
    if(this.s.hasValueAt(inflow) && this.s.at(inflow) != port) 
      previousPort = this.s.at(inflow);
          
    this.s.set(inflow, port);
        
    // The port argument cannot be an outport because is now in Im(s)
    this.isOutport.clear(port); 
    // So, by a theorem, it becomes an inport
    this.isInport.set(port); 
    // So, the only unit is now in port• but not in •port 
    this.Pb[port] = 1; this.bP[port] = 0;
        
    // When the previous port is not anymore in Im(s)
    if(previousPort != -1 && this.s.fiberAt(previousPort).isEmpty()) {                        
      this.isOutport.set(previousPort); // It becomes an outport      
      this.isInport.clear(previousPort); // So, by a theorem, cannot be inport
      // So, the only unit is now in •port but not in port•
      this.bP[previousPort] = 1; this.Pb[previousPort] = 0;
    }
  }

  @Override
  public void t(int outflow, int port) {
    
    int previousPort = -1;
    if(this.t.hasValueAt(outflow) && this.t.at(outflow) != port) 
      previousPort = this.t.at(outflow);
          
    this.t.set(outflow, port);
        
    // The port argument cannot be an inport because is now in Im(t)
    this.isInport.clear(port); 
    // So, by a theorem, it becomes an outport
    this.isOutport.set(port); 
    // So, the only unit is now in •port but not in port•
    this.bP[port] = 1; this.Pb[port] = 0;
        
    // When the previous port is not anymore in Im(t)
    if(previousPort != -1 && this.t.fiberAt(previousPort).isEmpty()) {                        
      this.isInport.set(previousPort); // It becomes an inport      
      this.isOutport.clear(previousPort); // So, by a theorem, cannot be outport
      // So, the only unit is now in port• but not in •port
      this.Pb[previousPort] = 1; this.bP[previousPort] = 0;
    }
  }

  @Override
  public void sigma(int outflow, int unit) {
    
    if (!t.isFinalised())
      throw new IllegalStateException("Function t must be built before sigma");
    if (!c.isFinalised())
      throw new IllegalStateException("Function c must be built before sigma");
    
    this.sigma.set(outflow, unit);
    
    // If the target port of outflow is 0-typed then we have a control outport
    // By the injectivity of t, there is only one target port (ensured by set)
    if(this.c.at(this.t.at(outflow)) == 0) hasControlOutports = true;
  }

  @Override
  public void tau(int inflow, int unit) {
    
    if (!s.isFinalised())
      throw new IllegalStateException("Function s must be built before sigma");
    if (!c.isFinalised())
      throw new IllegalStateException("Function c must be built before sigma");
    
    this.tau.set(inflow, unit);
    
    // If the source port of inflow is 0-typed then we have a control inport
    // By the injectivity of s, there is only one source port (ensured by set)
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
  
  public void finalise_s () {
    this.s.finalise();
  }
  
  public void finalise_t () {
    this.t.finalise();
  }
  
  public void finalise_c () {
    this.c.finalise();
  }
  
  @Override
  public boolean allUnitsControlInOut () {
    // As each port is either inport or outport: 
    // no need to verify whether the unique unit has control ins and outs 
    // Suffices to check whether the computon has control inports and outports
    return true;
  }
  
  @Override
  public boolean controlInportsOutports () {
    
    if(!hasControlInports) throw new IllegalArgumentException(
      "A primitive must have control inports."
    );
    if(!hasControlOutports) throw new IllegalArgumentException(
      "A primitive must have control outports."
    );    
    return true;
  }
  
  @Override
  public boolean deviceEncapsulation () {
    return true;
  }

  @Override
  public Primitive get() {                
    
    // Functions s, t and c are not checked because: 
    // they should have been finalised before tau and sigma
    this.sigma.finalise();
    this.tau.finalise();
    this.r.finalise();
    this.f.finalise();   
    
    if(this.inLabels[0].size() < isInport.cardinality())
      throw new IllegalStateException("All inports must have a label");
    
    return new Primitive (ports, inflows, outflows, colours, devices, 
      s, t, sigma, tau, c, r, f, isInport, isOutport, bP, Pb, inLabels);    
  }
}

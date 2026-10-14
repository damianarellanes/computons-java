package category.comp.objects;

import category.finset.FBuilder;
import category.finset.Finset;
import category.finset.FunctionBuilder;
import java.util.BitSet;

/**
 *
 * @author Damian Arellanes
 */
public class TrivialBuilder extends ComputonBuilder <Trivial> {
  
  private Boolean hasControlPorts;
  private final Finset ports, colours, devices;
  private final FBuilder c;
  
  private final BitSet isInport, isOutport;
  
  public TrivialBuilder (int ports, int colours, int devices) {
        
    this.ports = new Finset(ports);    
    this.colours = new Finset(colours);
    this.devices = new Finset(devices);    
    this.c = new FunctionBuilder(this.ports, this.colours);    
    this.hasControlPorts = false;
    
    this.isInport = new BitSet(ports);
    this.isOutport = new BitSet(ports);
  }

  @Override
  public void s(int inflow, int port) {
    throw new UnsupportedOperationException("Trivials have an empty s-function");
  }

  @Override
  public void t(int outflow, int port) {
    throw new UnsupportedOperationException("Trivials have an empty t-function");
  }

  @Override
  public void sigma(int outflow, int unit) {
    throw new UnsupportedOperationException("Trivials have an empty σ-function");
  }

  @Override
  public void tau(int inflow, int unit) {
    throw new UnsupportedOperationException("Trivials have an empty τ-function");
  }

  @Override
  public void c(int port, int colour) {
    
    if(colour != 0 && this.c.hasValueAt(port) && this.c.at(port) == 0) 
      hasControlPorts = false;
    else if (colour == 0) hasControlPorts = true;    
    
    // Each port is inport and outport simultaneously    
    isInport.set(port); isOutport.set(port);
    
    this.c.set(port, colour);
  }

  @Override
  public void r(int inflow, int outflow) {
    throw new UnsupportedOperationException("Trivials have an empty r-function");
  }

  @Override
  public void f(int outflow, int device) {
    throw new UnsupportedOperationException("Trivials have an empty f-function");
  }
  
  @Override
  public boolean allUnitsControlInOut () {
    return true; // Trivial computons have no units
  }
  
  @Override
  public boolean controlInportsOutports () {
    if(!hasControlPorts)
      throw new IllegalArgumentException("A trivial must have control port(s)");
    return true;
  }
  
  @Override
  public boolean deviceEncapsulation () {
    return true; // Trivial computons don't perform any behaviour
  }

  @Override
  protected Trivial get() {
    
    this.c.finalise();
    return new Trivial (ports, colours, devices, c, isInport, isOutport);
  }
}

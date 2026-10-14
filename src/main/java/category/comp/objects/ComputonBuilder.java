package category.comp.objects;

/**
 * @author Damian Arellanes 
 * @param <T> represents a concrete computon type (Trivial, Primitive or Atomic)
 * For efficiency, computon conditions need to be verified in the builders 
 */
public abstract class ComputonBuilder <T extends Computon> {
  
  public abstract void s (int inflow, int port);
  public abstract void t (int outflow, int port);
  public abstract void sigma (int outflow, int unit);
  public abstract void tau (int inflow, int unit);
  public abstract void c (int port, int colour);
  public abstract void r (int inflow, int outflow);  
  public abstract void f (int outflow, int device);        
  
  protected abstract boolean allUnitsControlInOut ();  
  protected abstract boolean controlInportsOutports ();
  protected abstract boolean deviceEncapsulation ();   
  protected abstract T get ();
  
  private void validate (T computon) {
    
    // Verifies non-emptiness for ports, colours and devices
    // The colour 0 is for the control type
    // The device 0 is the control echoing device
    if (computon.ports.size <= 0)
      throw new IllegalArgumentException("The set of ports cannot be empty");
    if (computon.colours.size <= 0) // One colour necessarily includes 0
      throw new IllegalArgumentException("The set of colours cannot be empty");
        
    // Each unit has at least one control input and at least one control output    
    // There is at least one control inport and at least one control outport
    // sigma . r = tau    
    if(!allUnitsControlInOut()) throw new IllegalStateException(
      "Units must have control ins and outs"
    );
    if(!controlInportsOutports()) throw new IllegalStateException(
      "Computons must have control inports and outports"
    );
    if(!deviceEncapsulation()) throw new IllegalStateException(
      "Devices should read/write within the same unit"
    );
  }
  
  public final T build () { 
    
    T computon = get();
    validate(computon);    
    return computon;
  }
}

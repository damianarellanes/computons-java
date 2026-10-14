package category.comp.diagrams;

import category.comp.morphisms.InMarker;
import category.comp.morphisms.OutMarker;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public final class TDiagram {
  
  public final InMarker itIn, checkIn, exitIn;
  public final OutMarker entryOut, itOut,checkOut;
  public final ComputonSpan lSpan, rSpan;
  
  public TDiagram (OutMarker entryOut, InMarker itIn, OutMarker itOut, 
      InMarker checkIn, OutMarker checkOut, InMarker exitIn) {
    
    if(itIn.target != itOut.target)         
      throw new IllegalArgumentException(
        "The targets of it+ and it- must match"
      );    
    if(checkIn.target != checkOut.target)         
      throw new IllegalArgumentException(
        "The targets of check+ and check- must match"
      );
    
    if(entryOut.source != itIn.source || itIn.source != checkOut.source)
      throw new IllegalArgumentException(
        "The sources of entry-, it+ and check- must match"
      );    
    if(checkIn.source != itOut.source || itOut.source != exitIn.source)
      throw new IllegalArgumentException(
        "The sources of check+, it- and exit+ must match"
      );            
    
    this.entryOut = entryOut;    
    this.itIn = itIn;
    this.itOut = itOut;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.exitIn = exitIn;
    
    this.lSpan = new ComputonSpan(
      getEntry(), getCheck(), getApex0(), entryOut, checkOut
    );
    this.rSpan = new ComputonSpan(
      getCheck(), getExit(), getApex1(), checkIn, exitIn
    );
  }
  
  public Computon getIt () {
    return itIn.target;
  }
  
  public Trivial getApex0 () {
    return (Trivial) itIn.source;
  }
  
  public Trivial getApex1 () {
    return (Trivial) itOut.source;
  }
  
  public Computon getEntry () {
    return entryOut.target;
  }
  
  public Computon getCheck () {
    return checkOut.target;
  }
  
  public Computon getExit () {
    return exitIn.target;
  }
}

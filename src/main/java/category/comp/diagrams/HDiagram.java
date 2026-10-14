package category.comp.diagrams;

import category.comp.morphisms.InMarker;
import category.comp.morphisms.OutMarker;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public class HDiagram {
  
  public InMarker itIn, checkIn, exitIn;
  public OutMarker itOut, entryOut, checkOut;  
  
  public HDiagram (InMarker itIn, OutMarker itOut, OutMarker entryOut, 
    InMarker checkIn, OutMarker checkOut, InMarker exitIn) {
    
    if(itIn.target != itOut.target)         
      throw new IllegalArgumentException(
        "The targets of it+ and it- must match"
      );    
    if(checkIn.target != checkOut.target)         
      throw new IllegalArgumentException(
        "The targets of check+ and check- must match"
      );
    
    if(entryOut.source != itIn.source)
      throw new IllegalArgumentException(
        "The source of entry- must be the source of it+"
      );
    if(exitIn.source != itIn.source)
      throw new IllegalArgumentException(
        "The source of exit+ must be the source of it+"
      );        
    if(checkIn.source != itOut.source)
      throw new IllegalArgumentException(
        "The source of check+ must be the source of it-"
      );
    if(checkOut.source != itIn.source)
      throw new IllegalArgumentException(
        "The source of check- must be the source of it+"
      );
    
    this.entryOut = entryOut;
    this.itIn = itIn;
    this.itOut = itOut;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.exitIn = exitIn;            
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

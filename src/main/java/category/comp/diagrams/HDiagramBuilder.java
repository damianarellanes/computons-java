package category.comp.diagrams;

import category.comp.morphisms.InMarker;
import category.comp.morphisms.InMarkerBuilder;
import category.comp.morphisms.OutMarker;
import category.comp.morphisms.OutMarkerBuilder;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public class HDiagramBuilder {
  
  private final Trivial apex0, apex1;
  private final Computon it, entry, check, exit;
  private final InMarkerBuilder itInBuilder, checkInBuilder, exitInBuilder;
  private final OutMarkerBuilder itOutBuilder, entryOutBuilder, checkOutBuilder; 

  public HDiagramBuilder(Trivial apex0, Trivial apex1, Computon entry,
    Computon it, Computon check, Computon exit) {
    
    this.apex0 = apex0;
    this.apex1 = apex1;
    this.it = it;    
    this.entry = entry;
    this.check = check;
    this.exit = exit;
    
    this.itInBuilder = new InMarkerBuilder(this.apex0, this.it);
    this.itOutBuilder = new OutMarkerBuilder(this.apex1, this.it);
    this.entryOutBuilder = new OutMarkerBuilder(this.apex0, this.entry);
    this.checkInBuilder = new InMarkerBuilder(this.apex1, this.check);
    this.checkOutBuilder = new OutMarkerBuilder(this.apex0, this.check);
    this.exitInBuilder = new InMarkerBuilder(this.apex0, this.exit);
  }
  
  // This constructs the span for (it -> check)
  // The pushout of this span will yield computation to execute it or exit
  public void set_Span_Right(int checkInport, int pApex1, int itOutport) {
    
    // This constructs the span (check+, it-)
    // By a theorem, every span of markers is pushable
    // So, no need to verify this condition
    
    checkInBuilder.set_P(pApex1, checkInport);
    itOutBuilder.set_P(pApex1, itOutport);
  }
  
  // This constructs the span for the join branching structure
  // The pushout of this span will yield computation to join the check execution
  public void set_Span_Up(int entryOutport, int pApex0, int checkOutport) {
    
    // This constructs the span (entry-, check-)
    // By a theorem, every span of (out-)markers is pushable
    // So, no need to verify this condition
    
    entryOutBuilder.set_P(pApex0, entryOutport);
    checkOutBuilder.set_P(pApex0, checkOutport);
  }
  
  // This constructs the span for the branching struct that decides termination
  // The pushout of this span will yield computation to execute it or exit
  public void set_Span_Down(int exitInport, int pApex0, int itInport) {
    
    // This constructs the span (exit+, it+)
    // By a theorem, every span of (in-)markers is pushable
    // So, no need to verify this condition
    
    exitInBuilder.set_P(pApex0, exitInport);
    itInBuilder.set_P(pApex0, itInport);
  }
  
  public HDiagram build() {  
    
    InMarker itIn = (InMarker) itInBuilder.build();
    OutMarker itOut = (OutMarker) itOutBuilder.build();
    OutMarker entryOut = (OutMarker) entryOutBuilder.build();
    InMarker checkIn = (InMarker) checkInBuilder.build();
    OutMarker checkOut = (OutMarker) checkOutBuilder.build();
    InMarker exitIn = (InMarker) exitInBuilder.build();
    
    return new HDiagram(itIn, itOut, entryOut, checkIn, checkOut, exitIn);        
  } 
}

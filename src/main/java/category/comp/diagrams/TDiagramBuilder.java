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
public class TDiagramBuilder {
  
  private final Trivial apex0, apex1;
  private final Computon it, entry, check, exit;
  private final InMarkerBuilder itInBuilder, checkInBuilder, exitInBuilder;
  private final OutMarkerBuilder itOutBuilder, entryOutBuilder, checkOutBuilder; 

  public TDiagramBuilder(Trivial apex0, Trivial apex1, Computon entry,
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
    this.exitInBuilder = new InMarkerBuilder(this.apex1, this.exit);
  }
  
  // This constructs the span for the join branching structure
  // The pushout of this span yields computation to join the execution of check
  public void set_Span_Left(int entryOutport, int pApex0, int checkOutport) {
    
    // This constructs the span (entry-, check-)
    // By a theorem, every span of (out-)markers is pushable
    // So, no need to verify this condition
    
    entryOutBuilder.set_P(pApex0, entryOutport);
    checkOutBuilder.set_P(pApex0, checkOutport);
  }
  
  // This constructs the span for the branching struct. that decides termination
  // The pushout of this span yields computation to execute check or exit
  public void set_Span_Right(int checkInport, int pApex1, int exitInport) {
    
    // This constructs the span (check+, exit+)
    // By a theorem, every span of (in-)markers is pushable
    // So, no need to verify this condition
    
    checkInBuilder.set_P(pApex1, checkInport);
    exitInBuilder.set_P(pApex1, exitInport);
  }
  
  // Constructs the in-marker of the iterative computon (it's just a wrapper)
  public void set_It_In(int pApex0, int itInport) {    
    itInBuilder.set_P(pApex0, itInport);
  }
  
  // Constructs the out-marker of the iterative computon (it's just a wrapper)
  public void set_It_Out(int pApex1, int itOutport) {    
    itOutBuilder.set_P(pApex1, itOutport);
  }
  
  public TDiagram build() {  
    
    InMarker itIn = (InMarker) itInBuilder.build();
    OutMarker itOut = (OutMarker) itOutBuilder.build();
    OutMarker entryOut = (OutMarker) entryOutBuilder.build();
    InMarker checkIn = (InMarker) checkInBuilder.build();
    OutMarker checkOut = (OutMarker) checkOutBuilder.build();
    InMarker exitIn = (InMarker) exitInBuilder.build();
    
    return new TDiagram(entryOut, itIn, itOut, checkIn, checkOut, exitIn); 
  } 
}

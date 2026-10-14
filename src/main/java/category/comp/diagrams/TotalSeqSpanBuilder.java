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
public class TotalSeqSpanBuilder {
  
  private final Trivial apex;
  private final Computon left, right;
  private final OutMarkerBuilder leg1Builder;
  private final InMarkerBuilder leg2Builder;

  public TotalSeqSpanBuilder(Trivial apex, Computon left, Computon right) {
    
    this.apex = apex;
    this.left = left;
    this.right = right;
    this.leg1Builder = new OutMarkerBuilder(this.apex, this.left);
    this.leg2Builder = new InMarkerBuilder(this.apex, this.right);
  }
  
  public void set_P(int pLeft, int pApex, int pRight) {        
    
    // By a theorem, every span of markers is pushable
    // So, no need to verify this condition
    leg1Builder.set_P(pApex, pLeft);
    leg2Builder.set_P(pApex, pRight);
  }
  
  public TotalSeqSpan build() {
    
    OutMarker leg1 = (OutMarker) leg1Builder.build();
    InMarker leg2 = (InMarker) leg2Builder.build();
    
    return new TotalSeqSpan(this.left, this.right, this.apex, leg1, leg2);
  }
}

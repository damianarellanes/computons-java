package category.comp.diagrams;

import category.comp.morphisms.InMarker;
import category.comp.morphisms.OutMarker;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public class TotalSeqSpan extends ComputonSpan {
  
  public TotalSeqSpan(Computon base1, Computon base2, Trivial apex, 
    OutMarker leg1, InMarker leg2) {
      super(base1, base2, apex, leg1, leg2);
  }  
}

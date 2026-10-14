package category.comp.diagrams;

import category.comp.morphisms.InMarker;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public class BranSpan extends ComputonSpan {
 
  public BranSpan(Computon left, Computon right, Trivial apex, InMarker leg1, 
    InMarker leg2) {
      super(left, right, apex, leg1, leg2);
  } 
}

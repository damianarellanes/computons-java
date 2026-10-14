package category.comp.diagrams;

import category.comp.morphisms.Monomorphism;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public class PartialSeqSpan extends ComputonSpan {
  
  public PartialSeqSpan(Computon base1, Computon base2, Trivial apex, 
    Monomorphism leg1, Monomorphism leg2) {
      super(base1, base2, apex, leg1, leg2);
  }  
}

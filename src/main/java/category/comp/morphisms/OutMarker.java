package category.comp.morphisms;

import category.comp.objects.Computon;
import category.comp.objects.Trivial;
import category.finset.InjectiveFunctionBuilder;

/** 
 * @author Damian Arellanes
 * For efficiency, the out-marker condition is verified in the builder
 */
public class OutMarker extends Monomorphism {
  
  public OutMarker(Trivial source, Computon target, 
    InjectiveFunctionBuilder u, InjectiveFunctionBuilder p, 
    InjectiveFunctionBuilder i, InjectiveFunctionBuilder o) {
    super(source, target, u, p, i, o);
  }
  
}

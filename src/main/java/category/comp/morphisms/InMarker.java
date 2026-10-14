package category.comp.morphisms;

import category.comp.objects.Computon;
import category.comp.objects.Trivial;
import category.finset.InjectiveFunctionBuilder;

/**
 * For efficiency, the in-marker condition is verified in the builder
 * @author Damian Arellanes
 */
public class InMarker extends Monomorphism {
  
  public InMarker(Trivial source, Computon target, 
    InjectiveFunctionBuilder u, InjectiveFunctionBuilder p, 
    InjectiveFunctionBuilder i, InjectiveFunctionBuilder o) {    
      super(source, target, u, p, i, o);
  }
  
}

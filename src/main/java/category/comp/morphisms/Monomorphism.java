package category.comp.morphisms;

import category.comp.objects.Computon;
import category.finset.InjectiveFunctionBuilder;

/**
 *
 * @author Damian Arellanes
 */
public class Monomorphism extends Morphism {
  
  public Monomorphism(Computon source, Computon target, 
    InjectiveFunctionBuilder u, InjectiveFunctionBuilder p, 
    InjectiveFunctionBuilder i, InjectiveFunctionBuilder o) {
      super(source, target, u.get(), p.get(), i.get(), o.get());
  }  
}

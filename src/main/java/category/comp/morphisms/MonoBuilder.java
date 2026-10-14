package category.comp.morphisms;

import category.comp.objects.Computon;
import category.finset.InjectiveFunctionBuilder;

/**
 *
 * @author Damian Arellanes
 */
public class MonoBuilder extends MorphismBuilder {  

  public MonoBuilder(Computon source, Computon target) {
    
    super(source, target, 
      new InjectiveFunctionBuilder (source.units, target.units),
      new InjectiveFunctionBuilder (source.ports, target.ports),
      new InjectiveFunctionBuilder (source.inflows, target.inflows),
      new InjectiveFunctionBuilder (source.outflows, target.outflows));
  }
         
  @Override
  protected Monomorphism get() {
    
    return new Monomorphism(source, target, 
        (InjectiveFunctionBuilder) alphaU, 
        (InjectiveFunctionBuilder) alphaP, 
        (InjectiveFunctionBuilder) alphaI, 
        (InjectiveFunctionBuilder) alphaO);
  }
}

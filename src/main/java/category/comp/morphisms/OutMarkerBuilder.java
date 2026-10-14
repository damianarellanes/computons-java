package category.comp.morphisms;

import category.comp.objects.Trivial;
import category.comp.objects.Computon;
import category.finset.InjectiveFunctionBuilder;

/**
 *
 * @author Damian Arellanes
 */
public class OutMarkerBuilder extends MorphismBuilder {

  public OutMarkerBuilder(Trivial source, Computon target) {    
    
    super(source, target, 
      new InjectiveFunctionBuilder (source.units, target.units),
      new InjectiveFunctionBuilder (source.ports, target.ports),
      new InjectiveFunctionBuilder (source.inflows, target.inflows),
      new InjectiveFunctionBuilder (source.outflows, target.outflows));
    
    alphaU.finalise();
  }
  
  @Override
  public void set_P(int p1, int p2) {
    
    if (!target.isOutport.get(p2))
      throw new IllegalArgumentException("alpha(P0)=P1- must be satisfied");
    
    super.set_P(p1, p2);
  }
         
  @Override
  protected OutMarker get() {
    
    return new OutMarker((Trivial) source, target, 
        (InjectiveFunctionBuilder) alphaU, 
        (InjectiveFunctionBuilder) alphaP, 
        (InjectiveFunctionBuilder) alphaI, 
        (InjectiveFunctionBuilder) alphaO);
  }
}

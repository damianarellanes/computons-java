package category.comp.diagrams;

import category.comp.morphisms.Morphism;
import category.comp.objects.Computon;

/**
 * @author Damian Arellanes
 * base1 <---leg1--- apex ---leg2---> base2
 */
public class ComputonSpan {
  
  public final Computon base1; 
  public final Computon base2; 
  public final Computon apex;
  public final Morphism leg1; 
  public final Morphism leg2;
          
  public ComputonSpan(Computon base1, Computon base2, Computon apex, Morphism leg1, 
    Morphism leg2) {
    
    if(!leg1.hasEndpoints(apex, base1) || !leg2.hasEndpoints(apex, base2))
      throw new IllegalArgumentException("Invalid span endpoints");
    
    this.base1 = base1;
    this.base2 = base2;
    this.apex = apex;
    this.leg1 = leg1;
    this.leg2 = leg2;
  }
}

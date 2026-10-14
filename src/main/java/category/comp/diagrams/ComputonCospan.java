package category.comp.diagrams;

import category.comp.morphisms.Morphism;
import category.comp.objects.Computon;

/**
 * @author Damian Arellanes
 * base1 ---leg1---> coapex <---leg2--- base2
 */
public class ComputonCospan {
  
  public final Computon cobase1; 
  public final Computon cobase2; 
  public final Computon coapex;
  public final Morphism coleg1; 
  public final Morphism coleg2;
          
  public ComputonCospan(Computon cobase1, Computon cobase2, Computon coapex, 
    Morphism coleg1, Morphism coleg2) {
    
    if(!coleg1.hasEndpoints(cobase1, coapex) ||
        !coleg2.hasEndpoints(cobase2, coapex))
      throw new IllegalArgumentException("Invalid cospan endpoints");
    
    this.cobase1 = cobase1;
    this.cobase2 = cobase2;
    this.coapex = coapex;
    this.coleg1 = coleg1;
    this.coleg2 = coleg2;
  }
}

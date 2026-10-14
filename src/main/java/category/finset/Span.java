package category.finset;

/**
 *
 * @author Damian Arellanes
 */
public class Span {
  
  public final Finset base1; 
  public final Finset base2; 
  public final Finset apex;
  public final Function leg1; 
  public final Function leg2;
          
  public Span(Finset base1, Finset base2, Finset apex, Function leg1, 
    Function leg2) {
    
    if(!leg1.hasEndpoints(apex, base1) || !leg2.hasEndpoints(apex, base2))
      throw new IllegalArgumentException("Invalid span");
    
    this.base1 = base1;
    this.base2 = base2;
    this.apex = apex;
    this.leg1 = leg1;
    this.leg2 = leg2;
  }
}
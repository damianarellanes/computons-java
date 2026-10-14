package category.finset;

/**
 *
 * @author Damian Arellanes
 */
public class Cospan {
  
  public final Finset cobase1; 
  public final Finset cobase2; 
  public final Finset coapex;
  public final Function coleg1; 
  public final Function coleg2;
          
  public Cospan(Finset cobase1, Finset cobase2, Finset coapex, Function coleg1, 
    Function coleg2) {
    
    if(!coleg1.hasEndpoints(cobase1, coapex) ||
        !coleg2.hasEndpoints(cobase2, coapex))
      throw new IllegalArgumentException("Invalid cospan");
    
    this.cobase1 = cobase1;
    this.cobase2 = cobase2;
    this.coapex = coapex;
    this.coleg1 = coleg1;
    this.coleg2 = coleg2;
  }
  
  public Boolean elementFromColeg1 (int x) {
    return !coleg1.fiberOf(x).isEmpty();
  }
  
  public Boolean elementFromColeg2 (int x) {
    return !coleg2.fiberOf(x).isEmpty();
  }
}
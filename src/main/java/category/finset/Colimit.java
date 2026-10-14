package category.finset;

/**
 *
 * @author Damian Arellanes
 */
public final class Colimit {
  
  public static Cospan disjointUnion(Finset s1, Finset s2) {
    
    Finset coapex = new Finset(s1.size + s2.size);        
    Function inj1 = Misc.offsetFunction(0, s1.size, coapex.size);    
    Function inj2 = Misc.offsetFunction(s1.size, s1.size + s2.size, coapex.size);
    
    return new Cospan(s1, s2, coapex, inj1, inj2);
  }

  public static Cospan union(Finset s1, Finset s2) {
    
    Finset coapex = new Finset(Math.max(s1.size, s2.size));        
    Function inj1 = Misc.offsetFunction(0, s1.size, coapex.size);
    Function inj2 = Misc.offsetFunction(0, s2.size, coapex.size);
    
    return new Cospan(s1, s2, coapex, inj1, inj2);
  }  
  
  public static Function uniqueFrom(Finset cobase1, Finset cobase2, Finset coapex,
    Function otherColeg1, Function otherColeg2, Finset otherCoapex) {
    
    if (!otherColeg1.hasDomain(cobase1) ||
        !otherColeg2.hasDomain(cobase2)
      ) throw new IllegalArgumentException("Cannot compute universal property");
        
    FunctionBuilder f = new FunctionBuilder(coapex, otherCoapex);
    for(int i = 0; i < coapex.size; i++) {
      
      if(i < cobase1.size) f.set(i, otherColeg1.at(i));
      else f.set(i, otherColeg2.at(i - cobase1.size));
    }
    f.finalise();
    
    return f.get();
  }
  
  public static Cospan pushout(Span span) {
        
    FunctionBuilder i1 = new FunctionBuilder(span.base1);
    FunctionBuilder i2 = new FunctionBuilder(span.base2);
    int currentpEl = 0;
    
    for (int y = 0; y < span.base1.size; y++) {
      
      int eq = currentpEl;
      
      for(int x : span.leg1.fiberOf(y)) {
        
        if(i2.hasValueAt(span.leg2.at(x))) {
          eq = i2.at(span.leg2.at(x));
          break;
        }
      }
      
      for (int x : span.leg1.fiberOf(y)) i2.set(span.leg2.at(x), eq);
      i1.set(y, eq);
      
      if (eq == currentpEl) currentpEl++;
    }    
        
    for (int z = 0; z < span.base2.size; z++) {
      if(!i2.hasValueAt(z)) i2.set(z, currentpEl++);      
    }
    i1.finalise(currentpEl);
    i2.finalise(currentpEl);
        
    Finset pushoutObj = new Finset(currentpEl);
    return new Cospan(span.base1, span.base2, pushoutObj, i1.get(), i2.get()); 
  }
}

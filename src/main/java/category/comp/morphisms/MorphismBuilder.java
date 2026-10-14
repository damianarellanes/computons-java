package category.comp.morphisms;

import category.comp.objects.Computon;
import category.finset.FBuilder;

/**
 * @author Damian Arellanes
 * The recommended creation order is: alphaU, alphaP, alphaO and alphaI
 */
public abstract class MorphismBuilder {
  
  public final Computon source, target;
  public final FBuilder alphaU, alphaP, alphaI, alphaO;
  
  public MorphismBuilder (Computon source, Computon target,
    FBuilder alphaU, FBuilder alphaP, FBuilder alphaI, FBuilder alphaO) {
    
    this.source = source;
    this.target = target;    
    this.alphaU = alphaU;
    this.alphaP = alphaP;
    this.alphaI = alphaI;
    this.alphaO = alphaO;
  }    
  
  public void set_U(int u1, int u2) {
    alphaU.set(u1, u2);
  }
  
  public void set_P(int p1, int p2) {
    
    if (!alphaU.isFinalised())
      throw new IllegalStateException("alphaU must be finalised before alphaP");
    
    if (source.c.at(p1) != target.c.at(p2))
      throw new IllegalStateException(
        "alphaN∘c1=c2∘alphaP must be satisfied for p1=" + p1);        
    
    alphaP.set(p1, p2);
    
    // Verifies no new units are attached in comp2 to comp1-internal ports
    if (source.hasInternalPort(p1) && target.bP[p2] > source.bP[p1]) {
      
      if (target.bP[p2] > source.bP[p1] || target.Pb[p2] > source.Pb[p1])
        throw new IllegalStateException(
          "Units can only be attached at the interface."
        );
    }      
  }
  
  public void set_I(int i1, int i2) {
    
    if (!alphaU.isFinalised())
      throw new IllegalStateException(
        "alphaU must be finalised before setting alphaI"
      );
    if (!alphaP.isFinalised())
      throw new IllegalStateException(
        "alphaP must be finalised before setting alphaI"
      );
    if (!alphaO.isFinalised())
      throw new IllegalStateException(
        "alphaO must be finalised before setting alphaI"
      );    
    
    if (target.tau.at(i2) != alphaU.at(source.tau.at(i1)))
      throw new IllegalStateException(
        "τ2∘alphaI=alphaU∘τ1 must be satisfied for i1=" + i1
      );
    
    if (target.s.at(i2) != alphaP.at(source.s.at(i1)))
      throw new IllegalStateException(
        "s2∘alphaI=alphaP∘s1 must be satisfied for i1=" + i1
      );
    
    if (target.r.at(i2) != alphaO.at(source.r.at(i1)))
      throw new IllegalStateException(
        "r2∘alphaI=alphaO∘r1 must be satisfied for i1=" + i1
      );
    
    alphaI.set(i1, i2);
  }
  
  public void set_O(int o1, int o2) {
    
    if (!alphaU.isFinalised())
      throw new IllegalStateException(
        "alphaU must be finalised before setting alphaO"
      );
    if (!alphaP.isFinalised())
      throw new IllegalStateException(
        "alphaP must be finalised before setting alphaO"
      );    
    
    if (target.sigma.at(o2) != alphaU.at(source.sigma.at(o1)))
      throw new IllegalStateException(
        "σ2∘alphaO=alphaU∘σ1 must be satisfied for o1=" + o1
      );
    
    if (target.t.at(o2) != alphaP.at(source.t.at(o1)))
      throw new IllegalStateException(
        "t2∘alphaO=alphaP∘t1 must be satisfied for o1=" + o1
      );
    
    if (source.f.at(o1) != target.f.at(o2))
      throw new IllegalStateException(
        "alphaD∘f1=f2∘alphaO must be satisfied for o1=" + o1
      );        
    
    alphaO.set(o1, o2);
  }
  
  public void finalise_U () {
    alphaU.finalise();
  }
  
  protected abstract Morphism get ();
  
  public final Morphism build () { 
   
    if (!alphaU.isFinalised()) 
      throw new IllegalStateException("The component U must be finalised");    
    alphaP.finalise();
    alphaI.finalise();
    alphaO.finalise();
    
    return get();
  }  
}

package category.comp.diagrams;

import category.comp.morphisms.MonoBuilder;
import category.comp.morphisms.Monomorphism;
import category.comp.objects.Computon;
import category.comp.objects.Trivial;

/**
 *
 * @author Damian Arellanes
 */
public class PartialSeqSpanBuilder {
  
  private final Trivial apex;
  private final Computon left, right;
  private final MonoBuilder leg1Builder;
  private final MonoBuilder leg2Builder;

  public PartialSeqSpanBuilder(Trivial apex, Computon left, Computon right) {
    
    this.apex = apex;
    this.left = left;
    this.right = right;
    this.leg1Builder = new MonoBuilder(this.apex, this.left);
    this.leg2Builder = new MonoBuilder(this.apex, this.right);
    
    leg1Builder.alphaU.finalise(); leg2Builder.alphaU.finalise();
  }
  
  public void set_P(int pLeft, int pApex, int pRight) {
    
    // By a theorem, every sequentiable span is pushable
    // So, no need to verify this condition
    // We just verify the following    
    if(!this.left.isOutport.get(pLeft))
      throw new IllegalArgumentException("alpha1(P0) must be in P1-");
    if(!this.right.isInport.get(pRight))
      throw new IllegalArgumentException("alpha2(P0) must be in P1+");
    
    leg1Builder.set_P(pApex, pLeft);
    leg2Builder.set_P(pApex, pRight);
  }
  
  public PartialSeqSpan build() {
        
    
    Monomorphism leg1 = (Monomorphism) leg1Builder.build();
    Monomorphism leg2 = (Monomorphism) leg2Builder.build();
    
    // Since we ensure only left's outports and right's inports are covered
    // We just ensure that the P-mappings do not fully cover (for partiality)    
    if (leg1.alphaP.image.length == this.left.isOutport.cardinality() &&
      leg2.alphaP.image.length == this.right.isInport.cardinality())
      throw new IllegalStateException("The span is not partial");
    
    return new PartialSeqSpan(this.left, this.right, this.apex, leg1, leg2);
  }
}

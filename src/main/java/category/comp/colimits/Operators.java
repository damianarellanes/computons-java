package category.comp.colimits;

import static category.comp.colimits.Constructions.compose;
import static category.comp.colimits.Constructions.coproduct;
import static category.comp.colimits.Constructions.pushout;
import static category.comp.colimits.Constructions.uniqueFromCoproduct;
import category.comp.diagrams.BranSpan;
import category.comp.diagrams.ComputonCospan;
import category.comp.diagrams.ComputonSpan;
import category.comp.diagrams.HDiagram;
import category.comp.diagrams.PartialSeqSpan;
import category.comp.diagrams.TDiagram;
import category.comp.diagrams.TotalSeqSpan;
import category.comp.morphisms.Morphism;
import category.comp.objects.Computon;

/**
 *
 * @author Damian Arellanes
 */
public final class Operators {
  
  public static Computon PAR (Computon left, Computon right) {
    return coproduct(left, right).coapex;
  }
  
  public static Computon TOTAL_SEQ (TotalSeqSpan span) {
    return pushout(span).coapex;
  }
  
  public static Computon PARTIAL_SEQ (PartialSeqSpan span) {
    return Constructions.pushout(span).coapex;
  }
  
  // This is what we used to call OPEN_BRANCHING. We dont need CLOSED_BRANCHING
  // By the axiom of choice, fork-branching collapses into a single choice
  // If join is needed (for closed branching), total seq. can be performed
  public static Computon BRAN (BranSpan span) {
    return Constructions.pushout(span).coapex;
  }
  
  public static Computon H_IT (HDiagram diagram) {
   
    ComputonCospan coproduct = 
      Constructions.coproduct(diagram.getApex0(), diagram.getApex1());    
    
    ComputonSpan lSpan = new ComputonSpan(
      diagram.getExit(), 
      diagram.getIt(), 
      diagram.getApex0(), 
      diagram.exitIn, 
      diagram.itIn
    );
    ComputonCospan leftPushout = Constructions.pushout(lSpan);
    ComputonCospan otherLeft = new ComputonCospan(
      diagram.getApex0(), 
      diagram.getApex1(), 
      leftPushout.coapex, 
      Constructions.compose(diagram.exitIn, leftPushout.coleg1), 
      Constructions.compose(diagram.itOut, leftPushout.coleg2)
    );
    Morphism uniqueLeft = Constructions.uniqueFromCoproduct(coproduct, otherLeft);
    
    ComputonSpan rSpan = new ComputonSpan(
      diagram.getEntry(), 
      diagram.getCheck(), 
      diagram.getApex0(), 
      diagram.entryOut, 
      diagram.checkOut
    );
    ComputonCospan rightPushout = Constructions.pushout(rSpan);
    ComputonCospan otherRight = new ComputonCospan(
      diagram.getApex0(), 
      diagram.getApex1(), 
      rightPushout.coapex, 
      Constructions.compose(diagram.entryOut, rightPushout.coleg1), 
      Constructions.compose(diagram.checkIn, rightPushout.coleg2)
    );
    Morphism uniqueRight = Constructions.uniqueFromCoproduct(coproduct, otherRight);
    
    return Constructions.pushout(
      new ComputonSpan(leftPushout.coapex, rightPushout.coapex, 
      coproduct.coapex, 
      uniqueLeft, uniqueRight)
    ).coapex;
  }
  
  public static Computon T_IT (TDiagram diagram) {
   
    ComputonCospan coproduct = Constructions.coproduct(
      diagram.getApex0(), diagram.getApex1()
    );    
    
    ComputonCospan leftPushout = Constructions.pushout(diagram.lSpan);
    ComputonCospan rightPushout = Constructions.pushout(diagram.rSpan);
    
    ComputonSpan inducedSpan = new ComputonSpan(
      leftPushout.coapex, 
      rightPushout.coapex, 
      diagram.getCheck(), 
      leftPushout.coleg2, 
      rightPushout.coleg1
    );
    ComputonCospan inducedPushout = Constructions.pushout(inducedSpan);        
    
    Morphism unique_apex0_induced = compose(
      compose(diagram.entryOut, leftPushout.coleg1), 
      inducedPushout.coleg1
    );
    Morphism unique_apex1_induced = compose(
      compose(diagram.checkIn, leftPushout.coleg2), 
      inducedPushout.coleg1
    );
    ComputonCospan otherLeft = new ComputonCospan(
      diagram.getApex0(), 
      diagram.getApex1(), 
      inducedPushout.coapex, 
      unique_apex0_induced, 
      unique_apex1_induced
    );
    Morphism uniqueLeft = uniqueFromCoproduct(coproduct, otherLeft);
        
    ComputonCospan otherRight = new ComputonCospan(
      diagram.getApex0(), diagram.getApex1(), 
      diagram.getIt(), diagram.itIn, diagram.itOut
    );
    Morphism uniqueRight = uniqueFromCoproduct(coproduct, otherRight);
    
    return pushout(
      new ComputonSpan(
        inducedPushout.coapex, 
        diagram.getIt(), 
        coproduct.coapex, 
        uniqueLeft, 
        uniqueRight)
    ).coapex;    
  }
}

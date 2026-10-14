package category.comp.colimits;


import category.comp.diagrams.ComputonCospan;
import category.comp.diagrams.ComputonSpan;
import category.comp.morphisms.Morphism;
import category.comp.objects.Computon;
import category.finset.Colimit;
import category.finset.Cospan;
import category.finset.FBuilder;
import category.finset.Function;
import category.finset.FunctionBuilder;
import category.finset.Misc;
import category.finset.Span;
import category.finset.SurjectiveFunctionBuilder;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Damian Arellanes
 */
public class Constructions {
  
  // Returns the composite morphism "m2 . m1" (i.e., m2 after m1)
  public static Morphism compose (Morphism m1, Morphism m2) {
    
    // For efficiency, no need to compute for the Sigma or Device components
    // These are computed in the overloaded constructor of Morphism
    return new Morphism(m1.source, m2.target, 
      Misc.compose(m1.alphaU, m2.alphaU), Misc.compose(m1.alphaP, m2.alphaP), 
      Misc.compose(m1.alphaI, m2.alphaI), Misc.compose(m1.alphaO, m2.alphaO));
  }
  
  private static FBuilder buildFunctionFrom (Cospan coprDom, Cospan coprCod, 
    Function generator1, Function generator2, boolean isSurjective) {
    
    FBuilder fb;
    if(isSurjective) fb = new SurjectiveFunctionBuilder(coprDom.coapex, coprCod.coapex);
    else fb = new FunctionBuilder(coprDom.coapex, coprCod.coapex);
    
    for (int i = 0; i < coprDom.coapex.size; i++) {
      
      int val;
      if (coprDom.elementFromColeg1(i))                  
        val = coprCod.coleg1.at(generator1.at(coprDom.coleg1.fiberOf(i).getFirst()));
      else
        val = coprCod.coleg2.at(generator2.at(coprDom.coleg2.fiberOf(i).getFirst()));
      fb.set(i, val);
      
    }
    fb.finalise();
    return fb;
  }
  
  public static Morphism uniqueFromCoproduct(ComputonCospan coproduct, ComputonCospan other) {
    
    Function uUnique = Colimit.uniqueFrom(
      coproduct.cobase1.units, coproduct.cobase2.units, coproduct.coapex.units,
      other.coleg1.alphaU, other.coleg2.alphaU, other.coapex.units);
    Function pUnique = Colimit.uniqueFrom(
      coproduct.cobase1.ports, coproduct.cobase2.ports, coproduct.coapex.ports,
      other.coleg1.alphaP, other.coleg2.alphaP, other.coapex.ports);
    Function iUnique = Colimit.uniqueFrom(
      coproduct.cobase1.inflows, coproduct.cobase2.inflows, coproduct.coapex.inflows,
      other.coleg1.alphaI, other.coleg2.alphaI, other.coapex.inflows);
    Function oUnique = Colimit.uniqueFrom(
      coproduct.cobase1.outflows, coproduct.cobase2.outflows, coproduct.coapex.outflows,
      other.coleg1.alphaO,other.coleg2.alphaO, other.coapex.outflows);
    Function nUnique = Colimit.uniqueFrom(
      coproduct.cobase1.colours, coproduct.cobase2.colours, coproduct.coapex.colours,
      other.coleg1.alphaSigma, other.coleg2.alphaSigma, other.coapex.colours);
    Function dUnique = Colimit.uniqueFrom(
      coproduct.cobase1.devices, coproduct.cobase2.devices, coproduct.coapex.devices,
      other.coleg1.alphaD, other.coleg2.alphaD, other.coapex.devices);
    
    return new Morphism(coproduct.coapex, other.coapex, uUnique, pUnique, iUnique, oUnique, nUnique, dUnique);    
  }
  
  public static ComputonCospan coproduct (Computon comp1, Computon comp2) {
    
    Cospan uCopr = Colimit.disjointUnion(comp1.units, comp2.units);
    Cospan pCopr = Colimit.disjointUnion(comp1.ports, comp2.ports);
    Cospan iCopr = Colimit.disjointUnion(comp1.inflows, comp2.inflows);
    Cospan oCopr = Colimit.disjointUnion(comp1.outflows, comp2.outflows);
    Cospan coloursCopr = Colimit.union(comp1.colours, comp2.colours);
    Cospan dCopr = Colimit.union(comp1.devices, comp2.devices);
    
    FBuilder s = buildFunctionFrom (iCopr, pCopr, comp1.s, comp2.s, false);
    FBuilder t = buildFunctionFrom (oCopr, pCopr, comp1.t, comp2.t, false);
    SurjectiveFunctionBuilder sigma = (SurjectiveFunctionBuilder) buildFunctionFrom (oCopr, uCopr, comp1.sigma, comp2.sigma, true);
    SurjectiveFunctionBuilder tau = (SurjectiveFunctionBuilder) buildFunctionFrom (iCopr, uCopr, comp1.tau, comp2.tau, true);
    FBuilder c = buildFunctionFrom (pCopr, coloursCopr, comp1.c, comp2.c, false);
    SurjectiveFunctionBuilder r = (SurjectiveFunctionBuilder) buildFunctionFrom (iCopr, oCopr, comp1.r, comp2.r, true);
    FBuilder f = buildFunctionFrom (oCopr, dCopr, comp1.f, comp2.f, false);
    
    // Builds bP, Pb, inports, outports and inLabels
    int[] bP = new int[pCopr.coapex.size];
    int[] Pb = new int[pCopr.coapex.size];
    Map<Integer, String>[] inLabels = new HashMap[uCopr.coapex.size];
    BitSet isInport = new BitSet(pCopr.coapex.size);
    BitSet isOutport = new BitSet(pCopr.coapex.size);
    for(int i = 0; i < pCopr.coapex.size; i++) {
      if (pCopr.elementFromColeg1(i)) {        
        
        int p1 = pCopr.coleg1.fiberOf(i).getFirst();
        
        if(comp1.isInport.get(p1)) isInport.set(i);
        if(comp1.isOutport.get(p1)) isOutport.set(i);
        bP[i] = comp1.bP[p1];
        Pb[i] = comp1.Pb[p1];
      } else {
        
        int p2 = pCopr.coleg2.fiberOf(i).getFirst();
        
        if(comp2.isInport.get(p2)) isInport.set(i);
        if(comp2.isOutport.get(p2)) isOutport.set(i);
        bP[i] = comp2.bP[p2];
        Pb[i] = comp2.Pb[p2];
      }        
    }     
    for (int inflow = 0; inflow < iCopr.coapex.size; inflow++) {
      
      String originalLabel;
      int unit = tau.at(inflow);
      int inputPort = s.at(inflow);
      
      if (iCopr.elementFromColeg1(inflow)) 
        originalLabel = 
          comp1.inLabels[uCopr.coleg1.fiberOf(unit).getFirst()]
            .get(comp1.s.at(iCopr.coleg1.fiberOf(inflow).getFirst()));
      else
        originalLabel = 
          comp2.inLabels[uCopr.coleg2.fiberOf(unit).getFirst()]
            .get(comp2.s.at(iCopr.coleg2.fiberOf(inflow).getFirst()));
              
      if (inLabels[unit] == null) inLabels[unit] = new HashMap<>();
      inLabels[unit].put(inputPort, originalLabel);       
    }
    
    // Builds the coproduct coapex
    Computon copr = new Computon (uCopr.coapex, pCopr.coapex, iCopr.coapex, 
      oCopr.coapex, coloursCopr.coapex, dCopr.coapex, s, t, sigma, tau, c, r, f,
      isInport, isOutport, bP, Pb, inLabels);
    
    // Builds canonical coproduct injections into the coapex
    Morphism inj1 = new Morphism (comp1, copr, uCopr.coleg1, pCopr.coleg1, 
      iCopr.coleg1, oCopr.coleg1, coloursCopr.coleg1, dCopr.coleg1);
    Morphism inj2 = new Morphism (comp2, copr, uCopr.coleg2, pCopr.coleg2, 
      iCopr.coleg2, oCopr.coleg2, coloursCopr.coleg2, dCopr.coleg2);
    
    return new ComputonCospan (comp1, comp2, copr, inj1, inj2);
  }
  
  public static ComputonCospan pushout (ComputonSpan span) {    
    
    Cospan uPush = Colimit.pushout(
      new Span (span.base1.units, span.base2.units, span.apex.units, 
        span.leg1.alphaU, span.leg2.alphaU)
    );
    Cospan pPush = Colimit.pushout(
      new Span (span.base1.ports, span.base2.ports, span.apex.ports, 
        span.leg1.alphaP, span.leg2.alphaP)
    );
    Cospan iPush = Colimit.pushout(
      new Span (span.base1.inflows, span.base2.inflows, span.apex.inflows, 
        span.leg1.alphaI, span.leg2.alphaI)
    );
    Cospan oPush = Colimit.pushout(
      new Span (span.base1.outflows, span.base2.outflows, span.apex.outflows, 
        span.leg1.alphaO, span.leg2.alphaO)
    );
    Cospan coloursPush = Colimit.union(span.base1.colours, span.base2.colours);
    Cospan dPush = Colimit.union(span.base1.devices, span.base2.devices);
    
    FBuilder s = buildFunctionFrom (iPush, pPush, span.base1.s, span.base2.s, false);
    FBuilder t = buildFunctionFrom (oPush, pPush, span.base1.t, span.base2.t, false);
    SurjectiveFunctionBuilder sigma = (SurjectiveFunctionBuilder) buildFunctionFrom (oPush, uPush, span.base1.sigma, span.base2.sigma, true);
    SurjectiveFunctionBuilder tau = (SurjectiveFunctionBuilder) buildFunctionFrom (iPush, uPush, span.base1.tau, span.base2.tau, true);
    FBuilder c = buildFunctionFrom (pPush, coloursPush, span.base1.c, span.base2.c, false);
    SurjectiveFunctionBuilder r = (SurjectiveFunctionBuilder) buildFunctionFrom (iPush, oPush, span.base1.r, span.base2.r, true);
    FBuilder f = buildFunctionFrom (oPush, dPush, span.base1.f, span.base2.f, false);
    
    // Builds bP, Pb, inports and outports    
    BitSet[] matrix_bP = new BitSet[pPush.coapex.size];
    BitSet[] matrix_Pb = new BitSet[pPush.coapex.size];
    int[] bP = new int[pPush.coapex.size];
    int[] Pb = new int[pPush.coapex.size];
    Map<Integer, String>[] inLabels = new HashMap[uPush.coapex.size];
    BitSet isInport = new BitSet(pPush.coapex.size);
    BitSet isOutport = new BitSet(pPush.coapex.size);
    for (int outflow = 0; outflow < oPush.coapex.size; outflow++) {
      
      int targetPort = t.at(outflow);
      int sourceUnit = sigma.at(outflow);      
      if (matrix_bP[targetPort] == null) 
        matrix_bP[targetPort] = new BitSet (uPush.coapex.size);
      matrix_bP[targetPort].set(sourceUnit);
    }    
    for (int inflow = 0; inflow < iPush.coapex.size; inflow++) {
      
      int sourcePort = s.at(inflow);
      int targetUnit = tau.at(inflow);      
      if (matrix_Pb[sourcePort] == null) 
        matrix_Pb[sourcePort] = new BitSet (uPush.coapex.size);
      matrix_Pb[sourcePort].set(targetUnit);
    }    
    for (int port = 0; port < pPush.coapex.size; port++) {
      
      if (matrix_bP[port] == null) bP[port] = 0;
      else bP[port] = matrix_bP[port].cardinality();
      
      if (matrix_Pb[port] == null) Pb[port] = 0;
      else Pb[port] = matrix_Pb[port].cardinality();
      
      if (t.fiberAt(port) == null) isInport.set(port);
      if (s.fiberAt(port) == null) isOutport.set(port);
    }
    for (int inflow = 0; inflow < iPush.coapex.size; inflow++) {
      
      String originalLabel;
      int unit = tau.at(inflow);
      int inputPort = s.at(inflow);
      
      if (iPush.elementFromColeg1(inflow)) 
        originalLabel = 
          span.base1.inLabels[uPush.coleg1.fiberOf(unit).getFirst()]
            .get(span.base1.s.at(iPush.coleg1.fiberOf(inflow).getFirst()));
      else
        originalLabel = 
          span.base2.inLabels[uPush.coleg2.fiberOf(unit).getFirst()]
            .get(span.base2.s.at(iPush.coleg2.fiberOf(inflow).getFirst()));
              
      if (inLabels[unit] == null) inLabels[unit] = new HashMap<>();
      inLabels[unit].put(inputPort, originalLabel);       
    }
    
    // Builds the pushout coapex
    Computon push = new Computon (uPush.coapex, pPush.coapex, iPush.coapex, 
      oPush.coapex, coloursPush.coapex, dPush.coapex, s, t, sigma, tau, c, r, f,
      isInport, isOutport, bP, Pb, inLabels);
    
    // Builds the pushout-induced morphisms into the coapex
    Morphism beta1 = new Morphism (span.base1, push, uPush.coleg1, pPush.coleg1, 
      iPush.coleg1, oPush.coleg1, coloursPush.coleg1, dPush.coleg1);
    Morphism beta2 = new Morphism (span.base2, push, uPush.coleg2, pPush.coleg2, 
      iPush.coleg2, oPush.coleg2, coloursPush.coleg2, dPush.coleg2);
    
    return new ComputonCospan (span.base1, span.base2, push, beta1, beta2);
  }
}

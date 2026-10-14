package category.finset;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Damian Arellanes
 */
public class SurjectiveFunctionBuilder implements FBuilder {
  
  private final int domainSize;
  private final int codomainSize;
  private final HashMap<Integer, ArrayList<Integer>> fibers;
  private final int[] values;
  private final boolean[] assignedDomain;
  private int assignedDomainCount;
  private final boolean[] assignedCodomain;
  private int assignedCodomainCount;
  private boolean isFinalised;
  private Function function;
  
  public SurjectiveFunctionBuilder(Finset domain, Finset codomain) {
    
    this.domainSize = domain.size;
    this.codomainSize = codomain.size;
    this.fibers = new HashMap<>();
    this.values = new int[domain.size];
    this.assignedDomain = new boolean[domain.size];
    this.assignedDomainCount = 0;
    this.assignedCodomain = new boolean[codomain.size];
    this.assignedCodomainCount = 0;
    this.isFinalised = false;
  }
    
  @Override
  public boolean isTotal() {
    return this.assignedDomainCount == this.domainSize;
  }
  
  private boolean isSurjective() {
    return this.assignedCodomainCount == this.codomainSize;
  }
  
  @Override
  public boolean hasValueAt(int x) {
    return assignedDomain[x];
  }
  
  @Override
  public ArrayList<Integer> fiberAt(int x) {
    return fibers.get(x);
  }
  
  @Override
  public boolean isFinalised() {
    return isFinalised;
  }
  
  @Override
  public int at(int x) {
    return values[x];
  }
    
  @Override
  public void set(int x, int y) {
    
    if (isFinalised) 
      throw new IllegalStateException("The function has been finalised");      
    if (x < 0 || x >= domainSize)
      throw new IllegalArgumentException("Outside domain: " + x);
    
    if(!assignedDomain[x]) {
      
      assignedDomain[x] = true;
      assignedDomainCount++;  
    }
    
    if(!assignedCodomain[y]) {
      
      assignedCodomain[y] = true;
      assignedCodomainCount++;  
    }
    
    values[x] = y;
      
    if (!fibers.containsKey(y)) fibers.put(y, new ArrayList<>());
    fibers.get(y).add(x);
  }
  
  @Override
  public void finalise() {
    
    if (!isTotal()) throw new IllegalStateException(
      "The function " + function + " is not total"
    );
    if (!isSurjective()) throw new IllegalStateException(
      "The function " + function + " is not surjective"
    );
    if (isFinalised) throw new IllegalStateException(
      "The function " + function + " has been finalised"
    );
    
    this.isFinalised = true;
    this.function = new Function(values, codomainSize, this.fibers);
  }
  
  @Override
  public Function get() {
    return this.function;
  }
}

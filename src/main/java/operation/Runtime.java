package operation;

import java.util.*;
import java.util.concurrent.*;
import operation.InvocationThread.InvocationResult;

/**
 *
 * @author Damian Arellanes
 */
public class Runtime {
  
  private final FlatComputon flat;
  private final Config config;
  
  public Runtime (FlatComputon flatComputon, Config config) {
    this.flat = flatComputon;
    this.config = config;
  }
  
  // Initially: currentState = nextState
  // nextState is modified as devices (of ready units) are invoked
  // Consuming inputs (of ready units), removes existing values
  // Producing outputs (in ready units), adds new values
  private void goToNextStateFrom (State currentState) 
    throws InterruptedException, ExecutionException {
    
    // Simultaneouly invokes the devices of output ports from enabled units
    ExecutorService executor = Executors.newCachedThreadPool();
    List<Future<InvocationResult>> results = new ArrayList<>();
    
    // Invokes the devices of all ready units in parallel
    currentState.getReadyUnits().stream().forEach(readyUnit -> {
      
      for (int outflow : flat.structure.sigma.fiberOf(readyUnit)) {                
        
        InvocationThread worker = new InvocationThread (
          flat, config, outflow, currentState
        );
        Future<InvocationResult> future = executor.submit(worker);
        results.add(future);
      }      
    });
    
    // Collects output values
    for (Future<InvocationResult> result : results) {
      
      InvocationResult response = result.get(); // Waits until value is ready      
      currentState.map[response.outputPort()] = response.value();
      
      // Type checking
      currentState.checkType(response.outputPort(), response.value());
    }
    
    executor.shutdown();
    
    // After execution, we clean the input port values of all ready units
    currentState.getReadyUnits().stream().forEach(readyUnit -> {
      
      for (int inflow : flat.structure.tau.fiberOf(readyUnit))
        currentState.map[flat.structure.s.at(inflow)] = null;
    });
  }  
    
  public State step (State currentState) 
    throws InterruptedException, ExecutionException {
    
    if (!currentState.isInitial())
      throw new IllegalStateException("The initial state is invalid");
    
    int t = 0;
    do { 
      
      System.out.println("--------------- t = " + t++ + " ---------------");
      System.out.println(currentState);      
      
      currentState.updateReadyUnits();      
      goToNextStateFrom(currentState);
    } while (!currentState.isFinal());
    
    return currentState;
  }
}

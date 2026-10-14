package operation;

import java.util.HashMap;
import java.util.concurrent.Callable;
import operation.InvocationThread.InvocationResult;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Damian Arellanes
 */
public class InvocationThread implements Callable<InvocationResult> {
  
  public record InvocationResult(int outputPort, Object value) {}
  
  private final FlatComputon flat;
  private final Config config;
  private final int outflow;
  private final int device;
  private final int unit;
  private final State currentState;
  
  public InvocationThread (FlatComputon flatComputon, Config config, 
    int outflow, State currentState) {
    
    this.flat = flatComputon;
    this.config = config;
    this.outflow = outflow;
    this.device = flat.structure.f.at(outflow);
    this.unit = flat.structure.sigma.at(outflow);
    this.currentState = currentState;
  }

  @Override
  public InvocationResult call() throws Exception {      
    return new InvocationResult(flat.structure.t.at(outflow), evaluate());
  }
  
  private Object evaluate () {
    
    Boolean isControlOutput = 
      flat.structure.c.at(flat.structure.t.at(outflow)) == 0;
    
    HashMap<String, Object> inputs = new HashMap<>();
    for (int inflow : flat.structure.r.fiberOf(outflow)) {
      
      int inputPort = flat.structure.s.at(inflow);
      
      // Only updates inputs when the managed output is not a control port
      if (!isControlOutput)      
        inputs.put(
          flat.structure.inLabels[unit].get(inputPort), // Label 
          currentState.map[inputPort] // Value
        );
    }
        
    // Only trigger remote device if the managed output is not a control port
    return !isControlOutput ? triggerDeviceWith(inputs) : Control.SIGNAL;
  }
  
  private Object triggerDeviceWith (HashMap<String, Object> inputs) {
    
    int outputPort = flat.structure.t.at(outflow);
    Class outputType = config.types.get(flat.structure.c.at(outputPort));
    
    System.out.println(
      "Triggering device " + config.devices.get(device) + " with " + inputs
    );
    
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.postForObject(config.devices.get(device), inputs, outputType);
  }
}

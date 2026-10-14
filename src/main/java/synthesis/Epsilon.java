package synthesis;

import operation.Control;
import org.springframework.stereotype.Component;

/**
 * @author Damian Arellanes
 * This class represents the behaviour of the control echoing device
 */
@Component("Epsilon")
public class Epsilon implements Device <Epsilon.InEpsilon, Control> {

  public record InEpsilon(Control p1) implements Parameters {}  /* JSON Request */
  
  @Override
  public Control exec(InEpsilon inputs) {    
    return Control.SIGNAL;
  }

  @Override
  public Class<InEpsilon> getParametersClass() {
    return InEpsilon.class;
  }
  
  @Override
  public Class<Control> getReturnType() {
    return Control.class;
  }
}

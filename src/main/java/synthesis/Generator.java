package synthesis;

import category.comp.objects.Primitive;
import category.comp.objects.PrimitiveBuilder;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author Damian Arellanes
 */
public class Generator {
  
  public static Primitive buildPrimitive (TypeConfig typeConfig,
    int ports, int inflows, int outflows, int maxColour, int maxDevice, 
    DeviceRegistry devReg, int... selectedDevices) {
    
    // Creates the inverse map of colours to retrieve from class type
    HashMap<Class<?>, Integer> inverseColours 
      = new HashMap<>(typeConfig.getColour().size());
    typeConfig.getColour().forEach((typeId, typeClass) -> {      
      inverseColours.put(typeClass, typeId);
    });
    
    // Starts the construction of the primitive computon
    PrimitiveBuilder prim = new PrimitiveBuilder(
      ports, inflows, outflows, maxColour, maxDevice
    );
   
    int currentPort = 0, currentInflow = 0, currentOutflow = 0;
    for (int deviceId : selectedDevices) {
      
      // Builds the source and target functions
      for (Field input : devReg.get(deviceId).getParametersClass().getDeclaredFields()) {
                
        prim.s(currentInflow, currentPort);
        prim.c(currentPort, inverseColours.get(input.getType()));        
        prim.r(currentInflow, currentOutflow);                
        prim.label(currentPort, input.getName());
        currentInflow++; currentPort++;
      } 
      prim.c(currentPort, inverseColours.get(devReg.get(deviceId).getReturnType()));
      prim.t(currentOutflow, currentPort);
      prim.f(currentOutflow, deviceId);
      currentOutflow++; currentPort++;
    }   
    prim.finalise_s();prim.finalise_t();prim.finalise_c();
    
    for (int inflow = 0; inflow < currentInflow; inflow++) 
      prim.tau(inflow, 0);
    for (int outflow = 0; outflow < currentOutflow; outflow++) 
      prim.sigma(outflow, 0);
    
    return prim.build();
  } 
}

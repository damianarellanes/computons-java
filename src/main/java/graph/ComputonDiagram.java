package graph;

import category.comp.objects.Computon;
import java.util.ArrayList;
import java.util.HashMap;
import synthesis.DeviceRegistry;

/**
 * @author Damian Arellanes
 */
public final class ComputonDiagram {
  
  private final Graph diagram;
  private final Computon computon;
  private final DeviceRegistry devReg;
  
  public ComputonDiagram (Computon computon, DeviceRegistry devReg) {
    
    this.diagram = new Graph();
    this.computon = computon;
    this.devReg = devReg;
  }
  
  private void addPort(int id, int colour) {
    
    if(colour == 0) addControlPort("p" + String.valueOf(id));
    else addDataPort("p" + String.valueOf(id));
  }
  
  private void addDevice(Computon computon, int deviceId, String deviceName, 
    int outflowId, int... inflowIds) {
        
    String device = "d" + String.valueOf(deviceId);
    diagram.node(device, "diamond", deviceName);
    for(Integer inflowId : inflowIds) {      
      String inputPortId = "p" + String.valueOf(computon.s.at(inflowId));
      diagram.edge(inputPortId, device, "i" + inflowId);
    }
      
    String outputPortId = "p" + String.valueOf(computon.t.at(outflowId));
    diagram.edge(device, outputPortId, "o" + outflowId);
  }
  
  private void addControlPort(String id) {
    diagram.node(id, "box", id);
  }
  
  private void addDataPort(String id) {
    diagram.node(id, "circle", id);
  }
  
  private void addUnit(String id, String... deviceIds) {
    diagram.group(id, deviceIds);
  }
  
  public void generate(String filename) {     
    
    HashMap<Integer, ArrayList<String>> unitDevices = new HashMap<>();  
    
    // For device reuse (as we need separate graphics, e.g., epsilon is reused)
    int deviceCounter = 0; 
    for (int device = 0; device < computon.devices.size; device++) {            
            
      for (Integer outflow : computon.f.fiberOf(device)) {
        
        int outputPort = computon.t.at(outflow);
        addPort(outputPort, computon.c.at(outputPort));
        
        int[] inflows = new int[computon.r.fiberOf(outflow).size()];
        int inCounter = 0;
        for(Integer inflow : computon.r.fiberOf(outflow)) {
          int inputPort = computon.s.at(inflow);
          addPort(inputPort, computon.c.at(inputPort));
          inflows[inCounter++] = inflow;
        }
        
        int unit = computon.sigma.at(outflow);
        
        if(!unitDevices.containsKey(unit)) 
          unitDevices.put(unit, new ArrayList<>());
        
        // We need to set "d" to avoid overlap with port ids
        unitDevices.get(unit).add("d" + deviceCounter);        
                
        addDevice(computon, deviceCounter++, 
          devReg.get(device).getClass().getSimpleName(), outflow, inflows);        
      }      
    }
    
    // After adding ports, devices and edges, we group devices into units
    for (int unit = 0; unit < computon.units.size; unit++) {
      
      addUnit(
        String.valueOf(unit), unitDevices.get(unit).toArray(new String[0])
      );      
    }
    
    try { diagram.render(filename + ".png"); } catch (Exception e) {}
  }
}

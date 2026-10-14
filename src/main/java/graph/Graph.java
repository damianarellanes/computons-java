package graph;
import java.nio.file.*;

/**
 * 
 * @author Damian Arellanes
 */

public class Graph {
  
  private final StringBuilder dot = new StringBuilder("""
    digraph G {
      graph [rankdir=LR];
      node [width=0.5, height=0.5, fixedsize=false, label="\\N"];
      """);

  public void node(String id, String shape, String label) {
    
    dot.append(String.format(
      "\"%s\" [shape=%s, label=\"%s\"];\n",
      id, shape, label
  ));
  }

  public void edge(String from, String to, String label) {
    dot.append(String.format("%s -> %s [label=\"%s\"];\n", from, to, label));
  }

  public void group(String name, String... nodes) {

    dot.append("subgraph cluster_").append(name).append(" {\n");
    for (String node : nodes) dot.append(node).append(";\n");
    dot.append("}\n");
  }

  public void render(String filename) throws Exception {

    dot.append("}\n");

    Path dotFile = Files.createTempFile("graph-", ".dot");
    Files.writeString(dotFile, dot);

    new ProcessBuilder(
      "/opt/homebrew/bin/dot",
      "-Tpng",
      dotFile.toString(),
      "-o",
      filename
    ).inheritIO().start().waitFor();

    Files.deleteIfExists(dotFile);
  }
}
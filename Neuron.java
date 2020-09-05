import java.util.*;

public class Neuron
{
  private String name = null;

  private double activation = 0.00; // 0 to 1
  private ArrayList<Double> weights = new ArrayList<>(); // Set between +/-0 & 5
  private double bias = 0.00; // Set between +/-0 & 5

  Neuron[] prevLayer;
  Neuron[] nextLayer;


  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void initialize(boolean first, int nextLayerSize)
  {
    double tempWeight;
    for (int i = 0; i < nextLayerSize; i++)
    {
      tempWeight = (double)((int)(((Math.random() * 4)) * 100)) / 100;
      tempWeight = ((int)(Math.random() * 2) == 1) ? tempWeight * -1 : tempWeight;
      weights.add(tempWeight);
    }

    if (!first)
    {
      bias = (double)((int)(((Math.random() * 4)) * 100)) / 100;
      bias = ((int)(Math.random() * 2) == 1) ? bias * -1 : bias;
    }
  }

  public void initialize()
  {
    bias = (double)((int)(((Math.random() * 4)) * 100)) / 100;
    bias = ((int)(Math.random() * 2) == 1) ? bias * -1 : bias;
  }

  public double getActivation()
  {
    return activation;
  }

  public void setActivation(double activation)
  {
    this.activation = activation;
  }

  public void propogateCalc(int currentNode)
  {
    double newActivation = 0.00;

    System.out.println();
    for (Neuron node : prevLayer)
    {
      System.out.println("\t{" + node.getActivation() + " <- " + node.getWeight(currentNode) + " = " + node.getActivation() * node.getWeight(currentNode) + "}");
      newActivation += node.getActivation() * node.getWeight(currentNode);
    }
    System.out.println("\t\t{" + newActivation + ", " + bias + "}");
    newActivation += bias;

    setActivation(sigmoid(newActivation));
    System.out.println("\t\t\t" + activation + "\n");
  }

  public double sigmoid(double x)
  {
    return (double)((int)((1 / (1 + Math.pow(Math.E, (-1 * x)))) * 100)) / 100;
  }

  public double getWeight(int followingNode)
  {
    return weights.get(followingNode);
  }

  public void setWeight(double weight, int node)
  {
    weights.set(node, weight);
  }

  public double getBias()
  {
    return bias;
  }

  public void setBias(double bias)
  {
    this.bias = bias;
  }

  public void link(Neuron[] prevLayer, Neuron[] nextLayer)
  {
    this.prevLayer = prevLayer;
    this.nextLayer = nextLayer;
  }

  public void printInfo()
  {
    System.out.print("Previous Layer: ");
    if (prevLayer == null)
      System.out.print("[Empty]");
    else
    {
      for (Neuron node : prevLayer)
        System.out.print("[Activation: " + node.getActivation() +
                        ", Bias: " + node.getBias() + "]. ");
    }

    System.out.println("\nNeuron Values: [Activation: " + activation +
                                   ", Bias: " + bias + "].");

    System.out.print("Next Layer: ");
    if (nextLayer == null)
      System.out.print("[Empty]");
    else
    {
      for (Neuron node : nextLayer)
        System.out.print("[Activation: " + node.getActivation() +
                        ", Bias: " + node.getBias() + "]. ");
    }

    System.out.println("\n");
  }
}

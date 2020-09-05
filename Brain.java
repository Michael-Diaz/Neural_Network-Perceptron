import java.util.*;
import java.io.*;

public class Brain
{
  static Scanner userInput = new Scanner(System.in);

  public static void main(String[] args)
  {
    ArrayList<Neuron[]> network = new ArrayList<Neuron[]>();
    double[] input = {0.25, 0.99, 0.75, 0.50};

    setup(network);
    init(network);

    network.get(0)[0].setActivation(input[0]);
    network.get(0)[1].setActivation(input[1]);
    network.get(0)[2].setActivation(input[2]);
    network.get(0)[3].setActivation(input[3]);

    link(network);
    propogate(network);
    printNetwork(network);



    /* Phase 1: Calculation
      Step 1: Determine the Number of Input & Output Neurons
      Step 2: Generate a Number of "Black Box" Layers with their own Number of Neurons
      Step 3: Link the Layers Together
      Step 4: Assign a Random Weight to Each Individual Neuron
      Step 5: Assign a Bias to Each Neuron in Layers Other than the First
      Step 6: For each Neuron in the Following Layer, Calculate the Weight x Activiation
              of Every Neuron in the Previous Layer, along with its Own Bias
      Step 7: Run the result through the Sigmoid Function to Determine Activation
        Step 7a: ALTERNATIVELY, use ReLU for Better Training?
    */

    /* Phase 2: Training
      Step 1: Compare the Output Layer to the Expected Output by Adding the Squares of
              the Differences for Each Neuron
      Step 2: Average all the Costs into a Single Value
      Step 3: Find the Gradient of the Cost and Negate it
      Step 4: Add the Vector for the Current Weights & Biases to the Vector taken from
              the Negated Gradient from the Cost Function
    */
  }

  public static void setup(ArrayList<Neuron[]> network)
  {
    System.out.print("How many layers: ");
    String numLayers = userInput.next();
    System.out.println();

    for (int i = 0; i < Integer.parseInt(numLayers); i++)
    {
      System.out.print("How many nodes in layer " + (i + 1) + ": ");
      String numNodes = userInput.next();
      Neuron[] temp = new Neuron[Integer.parseInt(numNodes)];

      for (int j = 0; j < temp.length; j++)
        temp[j] = new Neuron();

      network.add(temp);
    }

    System.out.println();

    for (Neuron node : network.get(Integer.parseInt(numLayers) - 1))
    {
      System.out.print("What will be the name of this node: ");
      String name = userInput.next();
      node.setName(name);
    }

    System.out.println();
  }

  public static void init(ArrayList<Neuron[]> network)
  {
    boolean firstLayer = true;

    for (int i = 0; i < network.size(); i++)
    {
      for (int j = 0; j < network.get(i).length; j++)
      {
        if (i != network.size() - 1)
          network.get(i)[j].initialize(firstLayer, network.get(i + 1).length);
        else
          network.get(i)[j].initialize();
      }
      firstLayer = false;
    }
  }

  public static void printNetwork(ArrayList<Neuron[]> network)
  {
    for (Neuron[] layer : network)
    {
      for (int i = 0; i < layer.length; i++)
      {
        if (layer[i].getName() != null)
          System.out.print("(" + layer[i].getName() + "), ");

        System.out.print("[Activation: " + layer[i].getActivation() +
                         ", Bias: " + layer[i].getBias() + "]. ");
      }
      System.out.println();
    }

    System.out.println("\nProbabilities:");
    for (Neuron node : network.get(network.size() - 1))
    {
      int probability;

      if (node.getActivation() == 0)
        probability = 0;
      else
        probability = (int)((node.getActivation() + 0.01) * 100);

      System.out.println("\t" + node.getName() + ": " + probability + "%");
    }

    System.out.println();
  }

  public static void printNeuron(ArrayList<Neuron[]> network)
  {
    System.out.print("Which layer is the neuron in: ");
    String layerChoice = userInput.next();

    System.out.print("Out of the " + network.get(Integer.parseInt(layerChoice) - 1).length + " neurons, which one is to be selected: ");
    String nodeChoice = userInput.next();
    System.out.println();

    network.get(Integer.parseInt(layerChoice) - 1)[Integer.parseInt(nodeChoice) - 1].printInfo();
  }

  public static void link(ArrayList<Neuron[]> network)
  {
    for (int i = 0; i < network.size(); i++)
    {
      for (Neuron node : network.get(i))
      {
        switch (i)
        {
          case 0:
            node.link(null, network.get(1));
            break;
          default:
            if (i != network.size() - 1)
              node.link(network.get(i - 1), network.get(i + 1));
            else
              node.link(network.get(i - 1), null);
        }
      }
    }
  }

  public static void propogate(ArrayList<Neuron[]> network)
  {
    int nodeIndex;

    for (int i = 1; i < network.size(); i++)
    {
      nodeIndex = 0;
      for (Neuron node : network.get(i))
      {
        node.propogateCalc(nodeIndex);
        nodeIndex++;
      }
    }
  }

}

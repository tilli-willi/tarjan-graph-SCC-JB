import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class TarjanTest {
    private static final int NUMBER_OF_SMALL_TESTS = 3;
    private static final String TEST_DIR = "tests/test_data/test";

    private List<SimpleNode<Integer>> readGraph(Scanner scanner) {
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        List<SimpleNode<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new SimpleNode<>(i, new ArrayList<>()));
        }
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            graph.get(from - 1).addNeighbor(graph.get(to - 1));
        }
        return graph;
    }

    private Set<List<Integer>> readExpectedAns(String fileName) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        Scanner scanner = new Scanner(br);
        int numberOfComponents = scanner.nextInt();
        Set<List<Integer>> expAns = new HashSet<>();
        for (int i = 0; i < numberOfComponents; i++) {
            int numberOfVertInComp = scanner.nextInt();
            List<Integer> comp = new ArrayList<>();
            for (int j = 0; j < numberOfVertInComp; j++) {
                comp.add(scanner.nextInt());
            }
            expAns.add(comp);
        }
        return expAns;
    }

    private Set<List<Integer>> runTarjanSolver(Scanner scanner) {
        List<SimpleNode<Integer>> graph = readGraph(scanner);
        SCCDecomposition sccSolver = new SCCDecomposition();
        List<List<Node<?>>> condensation = sccSolver.tarjan(graph);
        Set<List<Integer>> ans = new HashSet<>();
        for (List<Node<?>> scc : condensation) {
            List<Integer> comp = new ArrayList<>();
            for (Node<?> v : scc) {
                comp.add((Integer) v.getPayload() + 1);
            }
            Collections.sort(comp);
            ans.add(comp);
        }
        return ans;
    }

    @Test
    public void testSmall() throws FileNotFoundException {
        for (int i = 1; i <= NUMBER_OF_SMALL_TESTS; i++) {
            BufferedReader br = new BufferedReader(new FileReader(String.format(TEST_DIR + "%d.txt", i)));
            Scanner scanner = new Scanner(br);
            assertEquals(readExpectedAns(String.format(TEST_DIR + "%d_ans.txt", i)), runTarjanSolver(scanner));
        }
    }

    private List<int[]> generateGraph(int numberOfVertex, double probOfEdge, Random random) {
        List<int[]> edges = new ArrayList<>();
        for (int i = 1; i <= numberOfVertex; i++) {
            for (int j = 1; j <= numberOfVertex; j++) {
                if (random.nextInt(0, 100) <= probOfEdge * 100) {
                    edges.add(new int[]{i, j});
                }
            }
        }
        return edges;
    }

    @Test
    public void testGenerated() {
        Random random = new Random();
        random.setSeed(1234);
        int numberOfTests = 10;
        double[] probOfEdge = new double[]{0.2, 0.3, 0.4, 0.5, 0.6, 0.7};
        int[] numberOfVertex = new int[]{5, 10, 25, 50, 100, 300, 500};
        for (int t = 0; t < numberOfTests; t++) {
            for (double p : probOfEdge) {
                for (int n : numberOfVertex) {
                    List<int[]> edges = generateGraph(n, p, random);
                    int numberOfEdges = edges.size();
                    StringBuilder graphAsString = new StringBuilder();
                    graphAsString.append(Integer.toString(n) + ' ' + numberOfEdges + ' ');
                    for (int[] e : edges) {
                        graphAsString.append(Integer.toString(e[0]) + ' ' + e[1] + ' ');
                    }
                    Scanner scanner = new Scanner(graphAsString.toString());
                    assertEquals(graphAsString.toString(),
                            new NaiveSCC(n, edges).decompose(), runTarjanSolver(scanner));
                }
            }
        }
    }
}

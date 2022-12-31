import java.util.*;

import static java.lang.Math.min;

class SCCDecomposition {
    List<List<Node<?>>> components = new ArrayList<>();

    private final Map<Node<?>, int[]> timeInAndUp = new HashMap<>();

    private final Stack<Node<?>> potentialComp = new Stack<>();

    private final Set<Node<?>> isInStack = new HashSet<>();

    private int time = 0;

    private void dfs(Node<?> v) {
        potentialComp.push(v);
        isInStack.add(v);
        time += 1;
        timeInAndUp.put(v, new int[]{time, time});
        for(Node<?> to : v.adjacents()) {
            int[] oldVal = timeInAndUp.get(v);
            if(timeInAndUp.get(to)[0] == 0) {
                dfs(to);
                timeInAndUp.put(v, new int[]{oldVal[0], min(oldVal[1], timeInAndUp.get(to)[1])});
            } else if (isInStack.contains(to)) {
                timeInAndUp.put(v, new int[]{oldVal[0], min(oldVal[1], timeInAndUp.get(to)[0])});
            }
        }
        int[] vInUp = timeInAndUp.get(v);
        if(vInUp[0] == vInUp[1]) {
            List<Node<?>> curSCC = new ArrayList<>();
            while(isInStack.contains(v)) {
                Node<?> curV = potentialComp.pop();
                curSCC.add(curV);
                isInStack.remove(curV);
            }
            components.add(curSCC);
        }
    }

    /**
     * Calculates the strongly connected components of a directed acyclic graph using Tarjan's algorithm
     *
     * <a href="https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm">Reference</a>
     *
     * @param graph a directed acyclic graph
     * @return a list of strongly connected components of the specified graph
     */
    public List<List<Node<?>>> tarjan(List<? extends Node<?>> graph) {
        for(Node<?> node : graph) {
            timeInAndUp.put(node, new int[]{0, 0});
        }
        for(Node<?> node : graph) {
            if(timeInAndUp.get(node)[0] == 0) {
                time = 0;
                dfs(node);
            }
        }
        return components;
    }
}
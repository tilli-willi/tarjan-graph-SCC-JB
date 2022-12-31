import java.util.*;

public class NaiveSCC {
    private final int n;
    private final List<List<Integer>> graph = new ArrayList<>();
    private final List<List<Integer>> graphReversed = new ArrayList<>();
    private final boolean[] markedDirect;
    private final boolean[] markedReverse;
    private final boolean[] alreadyInComp;

    public NaiveSCC(int n, List<int[]> edges) {
        this.n = n;
        markedDirect = new boolean[n];
        markedReverse = new boolean[n];
        alreadyInComp = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            graphReversed.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0] - 1).add(e[1] - 1);
            graphReversed.get(e[1] - 1).add(e[0] - 1);
        }
    }

    private void dfsDirect(int v) {
        markedDirect[v] = true;
        for (int to : graph.get(v)) {
            if (!markedDirect[to]) {
                dfsDirect(to);
            }
        }
    }

    private void dfsReverse(int v) {
        markedReverse[v] = true;
        for (int to : graphReversed.get(v)) {
            if (!markedReverse[to]) {
                dfsReverse(to);
            }
        }
    }

    public Set<List<Integer>> decompose() {
        Set<List<Integer>> comps = new HashSet<>();
        for (int v = 0; v < n; v++) {
            if (!alreadyInComp[v]) {
                dfsReverse(v);
                dfsDirect(v);
                List<Integer> curComp = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    if (markedDirect[i] && markedReverse[i]) {
                        curComp.add(i + 1);
                        alreadyInComp[i] = true;
                    }
                    markedReverse[i] = false;
                    markedDirect[i] = false;
                }
                Collections.sort(curComp);
                comps.add(curComp);
            }
        }
        return comps;
    }
}

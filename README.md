Class `SCCDecomposition` contains the implementation of the 
Tarjan's strongly connected components algorithm for any graph
of entities which satisfy the `Node` interface. To apply
the algorithm, an instance of the `SCCDecomposition` class should be
created (no arguments for constructor needed), and then a list of 
`Node`s is to be passed to instance's `tarjan` method. 

Anything needed for testing is stored in the `tests` folder. 
Class `SimpleNode` defines a primitive node which satisfies the `Node`
interface and is used for further testing. Class `TarjanTest` is a Unit
test class. There the main algorithm is tested in two ways: by minor
constant graphs (checked manually) and randomly generated 
graphs (`testSmall` and `testGenerated` functions respectively). In 
order to verify the Tarjan's algorithm's correctness on randomly 
generated graphs, a naive algorithm is implemented in the class
`NaiveSCC`.
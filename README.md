

## Planning and Approximate Reasoning


Practical Exercise 2: Planner


## Scenario:

[Matrix dimension N]
[Boxes = N x N - 1]
[Rooms = N x N] :: clean|dirty && #boxes
[Robot = move|clean]


### precondition:

ini:Robot(x1,y1)


### postcondition:

fin:Rooms
fin:Robot(x2,y2)

### operators:

* clean-office(0)

| Operator        |      Precondition      |  Add           | Delete           |
|-----------------|:----------------------:|---------------:|-----------------:|
| Clean(O)        | R.O, O.dirty, O.empty  | O.clean        | O.dirty          |
| Move(Oi, Oj)    | R.Oi, Oj.adj.Oi        | R.Oj           | R.Oi             |
| Push(b, Oi, Oj) | R.Oi, B.Oi, Oj.adj.Oi  | B.Oj R.Oj E.Oi | E.Oj, B.Oi, R.Oi |

### predicates:

* Robot-location(office):
* Box-location(box, office)
* Dirty(office)
* Clean(office)
* Empty(office)
* Adjacent(office, office)


#### deliverable:

1. Java code (eclipse) :: MUST BE JAVA...
2. xx.bat files [README]
3. documentation.pdf

## Documentation:


Introduction to the problem

Analysis of the problem

Planning

Implementation design

Testing cases and results & Analysis of the results

Instructions to execute the program


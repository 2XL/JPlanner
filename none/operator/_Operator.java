package none.operator;

import none.predicate._Predicate;

import java.util.List;

/**
 * Created by j on 12/12/2015.
 */
public abstract class _Operator {
     List<_Predicate> precondition; // estado actual
     //List<_Predicate> add; // se añaden
     //List<_Predicate> remove; // se quitan
     int priority;
}

package none;

import none.config.Loader;

/**
 * Created by j on 12/12/2015.
 */
public class RunnerNaive extends Runner {

    public RunnerNaive() {
        super();

    }

    @Override
    public void execute(Loader config) {
        System.out.print("RunnerNaiveRunner");
    }
}

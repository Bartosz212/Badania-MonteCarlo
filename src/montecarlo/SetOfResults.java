package montecarlo;

public class SetOfResults {
    public double[] set;
    public int numberOfVariables;

    public SetOfResults(double[] _set){
        this.set = _set;
    }

    public SetOfResults(int _numberOfVariables){
        this.numberOfVariables = _numberOfVariables;
        set = new double[numberOfVariables];
    }

    public double[] getSet() {
        return set;
    }

    public int size(){
        return set.length;
    }

    public double getById(int index){
        return this.set[index];
    }
}

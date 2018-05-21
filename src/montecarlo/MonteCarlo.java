package montecarlo;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class MonteCarlo {
    public double error = 0.01;
    public double[][] limits;
    public SetOfConditions conditions = new SetOfConditions();
    public GoalFunction goalFunction;
    public String optimization;
    public Set<String> variables;
    public int numberOfVariables;

    public MonteCarlo(SetOfConditions _conditions, GoalFunction _goalFunction, Set<String> _variables){
        this.conditions = _conditions;
        this.goalFunction = _goalFunction;
        this.optimization = this.goalFunction.getOptimizationGoal();
        this.variables = _variables;
        this.numberOfVariables = this.variables.size();
    }

    public List<SetOfResults> fillSet(){
        SetOfResults set;
        double rand;
        List<SetOfResults> list = new ArrayList<>();

        for (int i=0; i<20000; i++){
            double[] randomOne = new double[numberOfVariables];
            for(int j=0; j<numberOfVariables; j++) {
                rand = limits[j][0] + new Random().nextDouble() * (limits[j][1] - limits[j][0]);
                randomOne[j] = rand;
            }
            set = new SetOfResults(randomOne);
            list.add(i, set);
        }
        return list;
    }

    public void recalculateLimits(double r, SetOfResults bestOne){
        for(int i = 0; i<numberOfVariables; i++){
            limits[i][0] = bestOne.getById(i) - r;
            limits[i][1] = bestOne.getById(i) + r;
        }
    }

    public void setStartLimits(double left, double right){
        for(int i = 0; i<numberOfVariables; i++){
            limits[i][0] = left;
            limits[i][1] = right;
        }
    }

    public double calculateRadius(SetOfResults bestOne, List<SetOfResults> list){
        double r = 0;
        double currR;
        for (SetOfResults set: list){
            currR = 0;
            for (int i=0; i<numberOfVariables; i++) {
                currR += Math.pow(abs(bestOne.getById(i) - set.getById(i)),2);
            }
            currR = sqrt(currR);
            if (currR > r) {
                r = currR;
            }
        }
        return r;
    }

    public double checkGoalFunction(SetOfResults setOfResults) {
        Map<String,Double> set = new HashMap<>();
        int i = 0;
        for(Iterator<String> it = variables.iterator(); it.hasNext(); i++){
            set.put(it.next(),setOfResults.getById(i));
        }
        return goalFunction.calculateGoalFunction(set);
    }

    public boolean conditions(SetOfResults setOfResults){
        Map<String,Double> set = new HashMap<>();
        int i = 0;
        for(Iterator<String> it = variables.iterator(); it.hasNext(); i++){
            set.put(it.next(),setOfResults.getById(i));
        }
        return conditions.checkConditions(set);
    }

    public void calculateIfMax(){
        List<SetOfResults> list = new ArrayList<>();
        limits = new double[numberOfVariables][2];
        setStartLimits(0,10000);
        double prevvalue;
        double r;
        int iter = 0;
        double currentvalue = 0;
        SetOfResults bestOne = new SetOfResults(numberOfVariables);
        do {
            prevvalue = currentvalue;
            currentvalue = 0;
            iter += 1;
            list = fillSet();
            for (Iterator<SetOfResults> iterator = list.iterator();iterator.hasNext();) {
                SetOfResults set = iterator.next();
                if(!conditions(set)){
                    iterator.remove();
                }else{
                    if(checkGoalFunction(set)>currentvalue){
                        currentvalue = checkGoalFunction(set);
                        bestOne = set;
                    }
                }
            }
            r = calculateRadius(bestOne, list);
            r = r/numberOfVariables;
            list.clear();
            System.out.println( "Wartosc: "+currentvalue+
                                "\nIteracja: "+iter+
                                "\nR:"+r);
            recalculateLimits(r, bestOne);

        }while ((abs(prevvalue - currentvalue)) > error);

        int i = 0;
        System.out.println("WYNIKI:  \nWartość funkcji celu: "+currentvalue);
        for(Iterator<String> it = variables.iterator(); it.hasNext(); i++){
            System.out.println(it.next()+": "+bestOne.getById(i));
        }
    }

    public void calculateIfMin(){
        List<SetOfResults> list = new ArrayList<>();
        limits = new double[numberOfVariables][2];
        double toplimit = 10000;
        double downlimit = 0;
        setStartLimits(downlimit, toplimit);
        double prevvalue;
        double r;
        int iter = 0;
        double currentvalue = Double.MAX_VALUE;
        SetOfResults bestOne = new SetOfResults(numberOfVariables);
        do {
            prevvalue = currentvalue;
            currentvalue = Double.MAX_VALUE;
            iter += 1;
            list = fillSet();
            for (Iterator<SetOfResults> iterator = list.iterator();iterator.hasNext();) {
                SetOfResults set = iterator.next();
                if(!conditions(set)){
                    iterator.remove();
                }else{
                    if(checkGoalFunction(set)<currentvalue){
                        currentvalue = checkGoalFunction(set);
                        bestOne = set;
                    }
                }
            }
            r = calculateRadius(bestOne, list);
            r = r/numberOfVariables;
            list.clear();
            System.out.println( "Wartosc: "+currentvalue+
                                "\nIteracja: "+iter+
                                "\nR:"+r);
            recalculateLimits(r, bestOne);
        }while ((abs(prevvalue - currentvalue)) > error);

        int i = 0;
        System.out.println("WYNIKI:  \nWartość funkcji celu: "+currentvalue);
        for(Iterator<String> it = variables.iterator(); it.hasNext(); i++){
            System.out.println(it.next()+": "+bestOne.getById(i));
        }
    }

    public void calculateResult(){
        if(this.optimization.equals("max")){
            calculateIfMax();
        }else if (this.optimization.equals("min")){
            calculateIfMin();
        }
    }
}


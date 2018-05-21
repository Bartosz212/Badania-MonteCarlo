package montecarlo;

import net.objecthunter.exp4j.Expression;

import java.util.Map;

public class GoalFunction {
    public Expression goal;
    public String optimizationGoal;

    public GoalFunction(Expression _goal, String _optimizationGoal){
        this.goal = _goal;
        this.optimizationGoal = _optimizationGoal;
    }

    public String getOptimizationGoal() {
        return optimizationGoal;
    }

    public Double calculateGoalFunction(Map<String,Double> set) {
        return goal.setVariables(set).evaluate();
    }
}

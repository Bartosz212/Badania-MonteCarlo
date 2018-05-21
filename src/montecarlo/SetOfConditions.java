package montecarlo;

import net.objecthunter.exp4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetOfConditions {
    public List<Triple<Expression,String,Expression>> conditions;

    public SetOfConditions(){
        this.conditions = new ArrayList<>();
    }

    public void addCondition(Triple<Expression,String,Expression> condition){
        this.conditions.add(condition);
    }

    public boolean checkConditions(Map<String,Double> set){
        Double left;
        Double right;
        String mid;
        double err = 1;
        for (int i = 0; i < this.conditions.size(); i++) {
            left = this.conditions.get(i).getLeft().setVariables(set).evaluate();
            mid = this.conditions.get(i).getMid();
            right = this.conditions.get(i).getRight().setVariables(set).evaluate();
            if (mid.equals("<=")){
                if (!(left <= right)){
                    return false;
                }
            }else if (mid.equals("<")){
                if (!(left < right)){
                    return false;
                }
            }else if (mid.equals(">")){
                if (!(left > right)){
                    return false;
                }
            }else if (mid.equals(">=")){
                if (!(left >= right)){
                    return false;
                }
            }else if (mid.equals("=")){
                if (!(left<=right+err && left>=right-err)){ //albo użyć dwóch nierówności !(left<=right+err && left>=right-err) !!!! !(left.equals(right)
                    return false;
                }
            }
        }
        return true;
    }
}

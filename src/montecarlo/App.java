package montecarlo;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


import java.util.*;

public class App {

    public static void main(String[] args) {
        SetOfConditions conditions = new SetOfConditions();
        GoalFunction goalFunction;
        MonteCarlo monteCarlo;
        Triple<Expression,String,Expression> condition = new Triple<>();
        String input;
        String character_mid;
        Expression expression_left;
        Expression expression_right;
        List<String> varia;
        List<String> splitted;
        List<String> splittedConditions;
        Set<String> variables;
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj zmienne oddzielając je przecinkami(bez spacji). Na przykład \"x1,x2,x3\" ");
        input = scanner.nextLine();
        varia =  new ArrayList<String>(Arrays.asList(input.split(",")));

        variables = new HashSet<String>(varia);

        System.out.println("Podaj warunki oddzielając je \"%\"");
        input = scanner.nextLine();
        splittedConditions = new ArrayList<String>(Arrays.asList(input.split("%")));
        for (String cond: splittedConditions){
            if (cond.matches("(.*)<=(.*)")) {
                character_mid = "<=";
                splitted = new ArrayList<String>(Arrays.asList(cond.split(character_mid)));
                expression_left = new ExpressionBuilder(splitted.get(0)).variables(variables).build();
                expression_right = new ExpressionBuilder(splitted.get(1)).variables(variables).build();
                condition.setAll(expression_left, character_mid, expression_right);
            } else if (cond.matches("(.*)>=(.*)")) {
                character_mid = ">=";
                splitted = new ArrayList<String>(Arrays.asList(cond.split(character_mid)));
                expression_left = new ExpressionBuilder(splitted.get(0)).variables(variables).build();
                expression_right = new ExpressionBuilder(splitted.get(1)).variables(variables).build();
                condition.setAll(expression_left, character_mid, expression_right);
            } else if (cond.matches("(.*)>(.*)")) {
                character_mid = ">";
                splitted = new ArrayList<String>(Arrays.asList(cond.split(character_mid)));
                expression_left = new ExpressionBuilder(splitted.get(0)).variables(variables).build();
                expression_right = new ExpressionBuilder(splitted.get(1)).variables(variables).build();
                condition.setAll(expression_left, character_mid, expression_right);
            } else if (cond.matches("(.*)<(.*)")) {
                character_mid = "<";
                splitted = new ArrayList<String>(Arrays.asList(cond.split(character_mid)));
                expression_left = new ExpressionBuilder(splitted.get(0)).variables(variables).build();
                expression_right = new ExpressionBuilder(splitted.get(1)).variables(variables).build();
                condition.setAll(expression_left, character_mid, expression_right);
            } else if (cond.matches("(.*)=(.*)")) {
                character_mid = "=";
                splitted = new ArrayList<String>(Arrays.asList(cond.split(character_mid)));
                expression_left = new ExpressionBuilder(splitted.get(0)).variables(variables).build();
                expression_right = new ExpressionBuilder(splitted.get(1)).variables(variables).build();
                condition.setAll(expression_left, character_mid, expression_right);
            }
            conditions.addCondition(condition);
            condition = new Triple<>();
        }

        System.out.println("Podaj funkcje celu");
        input = scanner.nextLine();
        expression_left = new ExpressionBuilder(input).variables(variables).build();

        System.out.println("Podaj cel.(min - jeśli funkcja ma przyjmować najmniejszą wartość\nmax - jeśli funkcja ma przyjmować największą wartość.)");
        input = scanner.nextLine();
        goalFunction = new GoalFunction(expression_left,input);

        monteCarlo = new MonteCarlo(conditions,goalFunction,variables);
        monteCarlo.calculateResult();

        while(!stop) {
            System.out.println("Czy chcesz ponownie przeliczyć wynik?");
            input = scanner.nextLine();
            switch (input)
            {
                case "y":
                    monteCarlo.calculateResult();
                    break;
                default:
                    stop = true;
                    break;
            }
        }
    }
}


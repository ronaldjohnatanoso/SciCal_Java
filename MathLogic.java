import java.util.regex.Pattern;
class MathLogic{

//evaluate expression function, pass the expression and expect it to be solved
public static String eval(String exp) {
    // Parentheses
    //(5+(3*2))
    int innerMostOpenParenthesis = exp.lastIndexOf('(');
    if (innerMostOpenParenthesis != -1) {
        int outerMostCloseParenthesis = exp.indexOf(')', innerMostOpenParenthesis);
        if (outerMostCloseParenthesis == -1) {
            return "Syntax Error";
        } else {
            String subExpression = exp.substring(innerMostOpenParenthesis + 1, outerMostCloseParenthesis); //inner nested expression inside parenthesis
            String subExpWithParenthesis = exp.substring(innerMostOpenParenthesis, 1 + outerMostCloseParenthesis); //inner exp with pair of parenthesis
            String innerEval = eval(subExpression); //(5+(3*2)) --> (3*2) = 6 ==> (5*6)

            System.out.println("------");
            System.out.println(subExpression);
            System.out.println(subExpWithParenthesis);
            System.out.println(innerEval);

            //EXPONENT HANDLING
            if(innerMostOpenParenthesis>0 && exp.charAt(innerMostOpenParenthesis-1)=='^'){ //if its an exponent operator before the parenthesis
                subExpWithParenthesis = subExpression; //don't remove parenthesis, only replace expression not parenthesis
            
                String powerExp = exp.substring(innerMostOpenParenthesis+1, outerMostCloseParenthesis);
            
                powerExp = eval(powerExp); //clean the power expression , maybe they have 2^(3+2) -> 2^(6)
                Double power = Double.parseDouble(powerExp); //final value for the power/exponent

                //GET THE BASE 
                int leftStart = innerMostOpenParenthesis - 2; //index where the ^ is located, we want to go left, and capture the digit of the base
                while (leftStart >= 0 && (Character.isDigit(exp.charAt(leftStart)) || exp.charAt(leftStart) == '.' || (leftStart == 0 && exp.charAt(leftStart) == '-'))) {
                    leftStart--; //go left
                }
                leftStart++;//back one step, inside the boundary we just passed when condition is still satisfied
                Double base = Double.parseDouble(exp.substring(leftStart, innerMostOpenParenthesis - 1)); //we just got the substring of the our base

                // //GET THE POWER
                //since we already cleaned the power expression, it safe to assume that the next closing parenthesis is for the power-closing
                int rightEnd = exp.indexOf(')', innerMostOpenParenthesis);                                          

                //OPERATE the exponentiation
                Double ans = Math.pow(base, power); 
                String exponentExp =  exp.substring(leftStart, rightEnd +1); //the whole expression ex: 51^(2)
                exp = exp.replaceFirst(Pattern.quote(exponentExp),Double.toString(ans)); //replace the square root expression with proper answer
                //5^(3+2) -> 25
                exp = eval(exp); //clean once again
            }

            //SQUARE ROOT HANDLING
            //√(9)
            if(innerMostOpenParenthesis>0 && exp.charAt(innerMostOpenParenthesis-1)=='√'){ //if its a sqrt operator before the parenthesis
                subExpWithParenthesis = subExpression; //don't remove parenthesis, only replace expression not parenthesis
                String sqrtExp = exp.substring(innerMostOpenParenthesis+1, outerMostCloseParenthesis);
                sqrtExp  = eval(sqrtExp); //clean the inside expression,maybe they have √(5+4) inside sqrt
                Double sqrtFinalAns = Math.sqrt(Double.parseDouble(sqrtExp));
                exp = exp.replaceFirst(Pattern.quote("√("+sqrtExp+")"),Double.toString(sqrtFinalAns)); //replace the square root expression with proper answer
                //√(3+6) -> 3
            } 

            //treat normally without special operations
            else exp = exp.replaceFirst(Pattern.quote(subExpWithParenthesis), innerEval); //replace the inner parenthesis with new evaluated value
            //(4+5) -> 9
        }
    }

    // Base case where no parentheses are left, perform the operations
    //MDAS RULE 
    int index = -1; //
    int len = exp.length();
    int mulIndex = exp.indexOf('*');
    int divIndex = exp.indexOf('/');
    int addIndex = exp.indexOf('+',1);
    int minusIndex = exp.indexOf('-',1); //we don't search from beginnig i.e. -3 , because its a negative symbol not a subtraction

    //both mul and div exist
    if(mulIndex != -1 && divIndex != -1) index = Math.min(mulIndex, divIndex); //if both are present, pick the least index (closer to left)
    else index = Math.max(mulIndex, divIndex);//if one of them is negative, then pick the other one with max(), 

    if(index == -1){ // if stil no operation found, then move to addition and subtraction
        if(addIndex != -1 && minusIndex != -1) index = Math.min(addIndex, minusIndex); //if both are present, pick the least index (closer to left)
        else index = Math.max(addIndex, minusIndex); //if one of them is negative, then pick the other one with max(), 
    }

    
    if (index != -1) { // If an operation is found
        char operator = exp.charAt(index);
        // Find the left operand
        int leftStart = index - 1;
        //find the left operand, left of = sign
        while (leftStart >= 0 && (Character.isDigit(exp.charAt(leftStart)) || exp.charAt(leftStart) == '.' || (leftStart == 0 && exp.charAt(leftStart) == '-'))) {
            leftStart--;
        }
        leftStart ++;
        double leftOperand = Double.parseDouble(exp.substring(leftStart, index));

        // Find the right operand, right side of = sign
        int rightEnd = index + 1;
        if(exp.charAt(rightEnd) == '-') rightEnd++; //if negative operand
        if(exp.charAt(index+1 ) == '+') rightEnd++; //if explicit positive symbol
        while (rightEnd < len && (Character.isDigit(exp.charAt(rightEnd)) || exp.charAt(rightEnd) == '.'  )) {
            rightEnd++;
        }

        double rightOperand ;
        try{                                                        //5+32+5
             rightOperand = Double.parseDouble(exp.substring(index + 1, rightEnd));
        } catch (Exception ex) {
            return "Syntax Error";  
        }

        String binaryExpression = exp.substring(leftStart, rightEnd);
        String binaryEvaluated = binaryOperation(leftOperand, rightOperand, operator);
        exp = exp.replaceFirst(Pattern.quote(binaryExpression), binaryEvaluated);
        //(5+3) -> 8

        System.out.println(exp);

        return eval(exp); // Recursively evaluate the remaining expression
    }
    //base case where no operation found
    return exp;
}

public static String binaryOperation(double leftOperand, double rightOperand, char operation) {
    double result = 0;
    switch (operation) {
        case '*':
            result = leftOperand * rightOperand;
            break;
        case '/':
            result = leftOperand / rightOperand;
            break;
        case '+':
            result = leftOperand + rightOperand;
            break;
        case '-':
            result = leftOperand - rightOperand;
            break;
    }
    return Double.toString(result);
}

}

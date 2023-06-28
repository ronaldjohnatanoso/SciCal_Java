

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Main extends JFrame implements ActionListener {
    // PROGRAM CONSTANTS
    final static int BUTTONS_FRAME_WIDTH = 400;
    final static int BUTTONS_FRAME_HEIGHT = 500;
    final int BUTTON_FRAME_ROW = 6;
    final int BUTTON_FRAME_COL = 4;

    public static void main(String[] args) {
        new Main();
    }

    // components declarations
    JButton[] buttons = new JButton[10];
    JPanel buttonsPanel = new JPanel();
    JPanel outputPanel = new JPanel();
    JPanel resultPanel = new JPanel();
    JTextField outputField = new JTextField("");
    JLabel resultSymbol = new JLabel("= ");
    JLabel resultLabel = new JLabel("");

    JButton addButton = new JButton("+");
    JButton minusButton = new JButton("-");
    JButton multiplyButton = new JButton("*");
    JButton divideButton = new JButton("/");
    JButton decimalButton = new JButton(".");
    JButton equalButton = new JButton("=");
    JButton clearButton = new JButton("CLR");
    JButton deleteButton = new JButton("DEL");
    JButton ansButton = new JButton("ANS");
    JButton sqrtButton = new JButton("\u221A"); // Square root symbol
    JButton opParenthesis = new JButton("(");
    JButton clParenthesis = new JButton (")");
    JButton exponentButton = new JButton("^");
    JButton optionButton = new JButton("Options");

    public CardLayout pageCL = new CardLayout();
    JPanel mainPanel = new JPanel(); //1st page, current panel
    OptionPage optPage = new OptionPage(this); //second page/panel, pass this instance of main to be used as data
    Themes themePage = new Themes(this, optPage); //third panel
    public Main() {
        // init jframe
        setTitle("CALCULATOR_v1_super");
        setSize(BUTTONS_FRAME_WIDTH, BUTTONS_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(pageCL); //main frame will contain all major panels i.e. main and options (history panel)
        Themes.nonButtonChangableComp(this); //add to list of components for theme change later

        //main panel (operations and output page)
        mainPanel.setLayout(new BorderLayout()); //main page border layout
        mainPanel.setSize(BUTTONS_FRAME_WIDTH, BUTTONS_FRAME_HEIGHT); //page content height
        Themes.nonButtonChangableComp(mainPanel); //add to list of components for theme change later

        //option panel with history and other


        // expression field
        outputField.setBounds(10, 0, 500, 50);
        outputField.setPreferredSize(new Dimension(500, 100));
        outputField.setFont(new Font("Arial", Font.PLAIN, 20));
        Themes.nonButtonChangableComp(outputField); //add to list of components for theme change later

        // calculation result 
        resultSymbol.setBounds(10, 0, 500, 50);
        resultLabel.setBounds(30, 0, 500, 50);
        resultSymbol.setFont(new Font("Arial",Font.BOLD, 20));
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        optionButton.setFont(new Font("Arial",Font.PLAIN,10));

        // panel for the buttons
        buttonsPanel.setLayout(new GridLayout(BUTTON_FRAME_ROW, BUTTON_FRAME_COL, 10, 10));
        buttonsPanel.setPreferredSize(new Dimension(100, 300));
        Themes.nonButtonChangableComp(buttonsPanel); //add to list of components for theme change later

        // panel for output calculations
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(outputField, BorderLayout.NORTH);
        Themes.nonButtonChangableComp(outputPanel); //add to list of components for theme change later

        // pane for result when you press equal
        resultPanel.setLayout(null);
        resultPanel.add(resultLabel);
        resultPanel.add(resultSymbol);
        Themes.nonButtonChangableComp(resultPanel); //add to list of components for theme change later


        // init buttons
        for (int i = 0; i < 10; i++) {//number buttons
            buttons[i] = new JButton();
            buttons[i].setText("" + i);
            Themes.buttonChangableComp(buttons[i]); //add to list of components for theme change later
            buttonDecorator(buttons[i], buttonsPanel); // custom function that decorates the button, ex: font style or color, actionlistener
        } //buttonDecorator also adds the component to its panel in the second parameter
        buttonDecorator(addButton, buttonsPanel);   //DECORATE THE BUTTON AND ADD THEM TO THE PANEL
        buttonDecorator(minusButton, buttonsPanel);
        buttonDecorator(multiplyButton, buttonsPanel);
        buttonDecorator(divideButton, buttonsPanel);
        buttonDecorator(equalButton, buttonsPanel);
        buttonDecorator(decimalButton, buttonsPanel);
        buttonDecorator(clearButton, buttonsPanel);
        buttonDecorator(deleteButton,buttonsPanel);
        buttonDecorator(ansButton, buttonsPanel);
        buttonDecorator(sqrtButton, buttonsPanel);
        buttonDecorator(opParenthesis,buttonsPanel);
        buttonDecorator(clParenthesis,buttonsPanel);
        buttonDecorator(exponentButton, buttonsPanel);
        buttonDecorator(optionButton, buttonsPanel);

        //major panels
        setVisible(true);
        mainPanel.add(outputPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel,"1"); //main page to show 
        add(optPage,"2");// option page with history 
        add(themePage,"3"); //themes

        themePage.themeBackground(Themes.nonButtonComps);//init the dfault theme
        themePage.themeBackground(Themes.buttonComps, themePage.BUTTON_THEME);
    }

    // function that takes a button and decorates it with font style/size, action actionlistener
    public void buttonDecorator(JButton btn, JPanel buttonsPanel) {
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.addActionListener(this);
        buttonsPanel.add(btn);
        Themes.buttonChangableComp(btn); //add to list of components for theme change later
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //we first store the getText inside an intermediary String for easier readability
        String outputString = (outputField.getText());//get the latest written in the expression field
        // listener only for the numbers
        for (JButton button : buttons) {
            if (e.getSource() == button) {
                String bText = button.getText();
               // System.out.println(bText);
                outputString = outputString.concat(bText);
            }
        }
        // listener for decimal
        if (e.getSource() == decimalButton) {
            outputString += ".";
        }
        // operators listener
        if(e.getSource()==addButton){
            outputString += "+";
        }
        if(e.getSource()== minusButton){
            outputString += "-";
        }
        if(e.getSource()== multiplyButton){
            outputString += "*";
        }
        if(e.getSource()==divideButton){
            outputString += "/";
        }
        if(e.getSource()==sqrtButton){
            //if output still empty or a multiply symbol is already present when pressing the button 
            if(outputString.equals("") || outputString.charAt(outputString.length()-1)== '*' ){
                outputString += "√("; // just append the symbol
            } else {
                outputString += "*√(";//if operation is present, assume default operation for multiplication
            }
        }
        if(e.getSource()==exponentButton){
            outputString += "^(";
        }
        if(e.getSource()==opParenthesis){
            //handle cases where there is no operation beside a parenthesis, automatically put *
            if(outputString.length()>0){ //not empty
                char lastChar = outputString.charAt(outputString.length()-1);
                //if the last char is an operator, only append "("
                if(lastChar == '+' || lastChar == -'-' || lastChar == '*' || lastChar == '/' ){
                    outputString = outputString.concat("(");
                    //if there is no operator, then automatically put * as inference
                } else {
                    outputString += "*(";
                }  
            } else outputString += "(";

        }
        if(e.getSource()==clParenthesis){
            outputString += ")";
        }
        if(e.getSource()==clearButton){
            outputString = "";
            resultLabel.setText("");
        }
        if(e.getSource()==deleteButton){
            if(outputString.equals("")) return; //dont delete at all if its already empty
            outputString = outputString.substring(0, outputString.length()-1); //delete the last character of the string
        }
        if(e.getSource()==ansButton){//pull up the last computed answer to your expression
            if(resultLabel.getText().equals("")) return;//if there is still no answer result to show, dont proceed
            outputString = resultLabel.getText(); //the last answer is to be put as the next expression
            resultLabel.setText("");
        }
        if(e.getSource()==equalButton){
            resultLabel.setText(MathLogic.eval(outputString)); //solve the whole expression
            if(!outputString.isEmpty())
           optPage.createHistory(outputString+"="+resultLabel.getText()); //add this record to history ledger in option page
        }
        outputField.setText(outputString); //set the label to whatever output string is
        //second page button 
        if(e.getSource()==optionButton){
            pageCL.show(this.getContentPane(),"2");
        }
    }

    
}












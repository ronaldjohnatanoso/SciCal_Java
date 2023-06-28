import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.util.ArrayList;

public class OptionPage extends JPanel implements ActionListener {

    JButton goToMain = new JButton("Go Back to Main Page");
    JButton deleteHistory = new JButton("Delete History");
    JPanel historyLedger = new JPanel();
    Main mainFrame;
    ArrayList<JButton> historyList = new ArrayList<>();

    JButton goToTheme = new JButton("Themes");

    public OptionPage(Main mainFrame) { //constructor
        this.mainFrame = mainFrame; //reference to our main page
        setLayout(new BorderLayout());
        setSize(Main.BUTTONS_FRAME_WIDTH, Main.BUTTONS_FRAME_HEIGHT);
        Themes.nonButtonChangableComp(this); //add to list of components for theme change later

        // go back button listener
        goToMain.addActionListener(this);
        deleteHistory.addActionListener(this);
        goToTheme.addActionListener(this);

        // history ledger
        historyLedger.setLayout(new BoxLayout(historyLedger, BoxLayout.Y_AXIS));
        historyLedger.add(goToTheme);
        historyLedger.add(deleteHistory);
        Themes.buttonChangableComp(goToMain); //add to list of components for theme change later
        Themes.buttonChangableComp(goToTheme); //add to list of components for theme change later
        Themes.buttonChangableComp(deleteHistory); //add to list of components for theme change later
        Themes.nonButtonChangableComp(historyLedger); //add to list of components for theme change later

        add(goToMain, BorderLayout.SOUTH); //go back to main button 
        add(historyLedger); //list of history
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goToMain) {
            mainFrame.pageCL.show(mainFrame.getContentPane(), "1"); //show back to main page
        }
        if(e.getSource()==goToTheme){
            mainFrame.pageCL.show(mainFrame.getContentPane(),"3"); //show themes page
        }
        if (e.getSource() == deleteHistory) {
            //loop through the arraylist of buttons, and remove each of them
            int len = historyList.size();
            for(int i=0;i<len;i++){
                JButton btn = historyList.get(i); //loop through the buttons
                historyList.remove(i); //arrast list 
                historyLedger.remove(btn); //button 
                len--;
                i--;
            }
            historyLedger.repaint();//re render the page
        }
    }

    //called when equals is clicked, create a history entry
    public void createHistory(String info){
        int equalsIndex = info.indexOf('='); //we need to divide the left and right side to represent in the ouput and result field
        String expression = info.substring(0, equalsIndex); //left side of the equation
        String equals = info.substring(equalsIndex+1); //right side of the equation
        JButton b = new JButton(info); //create button with info passed when clicked
        Themes.buttonChangableComp(b); //add to list of components for theme change later
        b.setBackground(mainFrame.themePage.BUTTON_THEME); //set color of this button 
        historyList.add(b); //add this button to array list of buttons to keep track
        historyLedger.add(b);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); //set ssize of button
        b.addActionListener(e-> {//lambda expression to handle when a history entry is clicked, it loads the info to the calculator field
            mainFrame.outputField.setText(expression); //set output field 
            mainFrame.resultLabel.setText(equals); //set result 
            mainFrame.pageCL.show(mainFrame.getContentPane(), "1"); //go back to main
        });
    }

}

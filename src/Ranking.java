import acm.program.DialogProgram;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking extends DialogProgram {

    private ArrayList<Integer> ranking = new ArrayList<>();

    @Override
    public void run() {
        initView();
        initData();
    }

    private void initView(){
        // appearing the window
        // 1、removing the menu
        setJMenuBar(null);

        // 2、Set the closing pop-up box to not close the program
        setExitOnClose(false);

        // 3、set the title
        setTitle("Ranking list");

        // 4、Set the position of the pop-up (middle of the screen), and the width and height
        setSize(600,400);
        setCenterLocation(getScreenWidth() / 2, getScreenHeight() / 2);

        // 5、Set size cannot be changed
        setResizable(false);
    }
    private void initData(){
        String[][] data = new String[10][2]; //only show the top 10 results
        for(int i = 0; i < ranking.size(); i ++){
            data[i][0] = "" + (i + 1); // the ranking
            data[i][1] = "" + ranking.get(i);
            if(i == 9){
                break;
            }
        }

        // Create the table to be displayed
        JTable table = new JTable(data, new String[]{"ranking", "score"});
        // Creating scrollbars in tables
        JScrollPane scrollPane = new JScrollPane(table);
        // First remove all components
        removeAll();
        // Adding scrollbars to pop-up boxe
        add(scrollPane);
    }

    public void scoreValues(int x){
        ranking.add(x);
        Collections.sort(ranking, Collections.reverseOrder());
    }
}


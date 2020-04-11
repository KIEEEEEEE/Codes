package project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.*;
import javax.swing.border.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.text.SimpleDateFormat;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


/**
 *
 * @author Jackie Leung
 */
public class TimeManager 
{
    public static Lists lists = new Lists();
    public static final double ImportanceFullMarks = 10;
    public static final double UrgencyFullMarks = 10;
    static JLabel task1 = new JLabel();
    static JLabel task2 = new JLabel();
    static JLabel task3 = new JLabel();
    static JLabel task4 = new JLabel();
    static JLabel task5 = new JLabel();
    static JLabel task6 = new JLabel();
    static JLabel secLabel = new JLabel();
    static JLabel minLabel = new JLabel();
    static JButton Mode = new JButton();
    static JCheckBox target1Done = new JCheckBox();
    static JCheckBox target2Done = new JCheckBox();
    static JCheckBox target3Done = new JCheckBox();
    static JCheckBox target4Done = new JCheckBox();
    static JCheckBox target5Done = new JCheckBox();
    static JCheckBox target6Done = new JCheckBox();

    
    public static int sec =0;
    public static int min =60;
    static Timer clock = new Timer(1000,new ActionListener(){
    public void actionPerformed(ActionEvent e){
        if(sec==0)
        {
            sec = 60;
            min--;
            secLabel.setText(" "+sec);
            if(min>=10)
                minLabel.setText(" "+min);
            else minLabel.setText(" 0"+min);
        }
        sec--;
        if(sec>=10)
            secLabel.setText(" "+sec);
        else secLabel.setText(" 0"+sec);
        if((min==0)&&(sec==0))
        {
            if(!lists.isAllFinished())
            { 
                rotate();
                UpdateTask();
                min =60;
            }
            else
            {
                clock.stop();;
                JDialog congrats = new JDialog();
                congrats.setTitle("Congratulations!");
                JLabel Success = new JLabel("恭喜你！你已完成今天的全部目标任务!");
                congrats.add(Success,BorderLayout.CENTER);
                congrats.setSize(250,250);
                congrats.setLocationRelativeTo(null);
                congrats.setVisible(true);
            }


        }
    }
});
    
    
    

    
    private static void mainFrame()
    {
        JFrame frame = new JFrame("Time Manager");
        JPanel panel = new JPanel();
        panel.setBackground(new Color(246,255,183));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        frame.getContentPane().add(panel);
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        panel.add(buttonPanel);
        
        buttonPanel.setLayout(new FlowLayout());
        JButton Start = new JButton("开始");
        Start.setPreferredSize(new Dimension(100,40));
        JButton Stop = new JButton("暂停");
        Stop.setPreferredSize(new Dimension(100,40));
        JButton Add = new JButton("添加任务");
        Add.setPreferredSize(new Dimension(100,40));
        Mode.setText("模式转换");
        Mode.setPreferredSize(new Dimension(100,40));
        JButton AllTask = new JButton("全部任务");
        AllTask.setPreferredSize(new Dimension(100,40));
        buttonPanel.add(Start);
        buttonPanel.add(Stop);
        buttonPanel.add(Add);
        buttonPanel.add(Mode);
        buttonPanel.add(AllTask);
        
        Start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                clock.setRepeats(true);
                clock.start();
                target1Done.setEnabled(true);
                target2Done.setEnabled(true);
                target3Done.setEnabled(true);
                target4Done.setEnabled(true);
                target5Done.setEnabled(true);
                target6Done.setEnabled(true);
            }
        });
        
        Stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                clock.stop();
                target1Done.setEnabled(false);
                target2Done.setEnabled(false);
                target3Done.setEnabled(false);
                target4Done.setEnabled(false);
                target5Done.setEnabled(false);
                target6Done.setEnabled(false);
            }
        });
        
        Add.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                addTask();
            }
        });
        
        Mode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                lists.changeMode();
                if(lists.mode==true)
                {
                    Mode.setBackground(Color.RED);
                    Mode.setText("紧急模式");
                    ViewAll();
                }
                else if(lists.mode==false){
                    Mode.setBackground(Add.getBackground());     
                    Mode.setText("普通模式");
                    lists.RefreshTargets();
                    UpdateTask();
                }
                    
            }
        });
        
        AllTask.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                ViewAll();
            }
        });
        
        JPanel clockPanel = new JPanel();
        clockPanel.setBackground(new java.awt.Color(0, 0, 0));
        clockPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        JLabel colonLabel = new JLabel();


        minLabel.setFont(new java.awt.Font("Calibri", 1, 80)); 
        minLabel.setForeground(new java.awt.Color(255, 255, 255));
        if(min<10)
        {
            minLabel.setText(" "+"0"+min);
        }
        else minLabel.setText(" "+min);
        minLabel.setPreferredSize(new java.awt.Dimension(120, 68));

        secLabel.setFont(new java.awt.Font("Calibri", 1, 80)); 
        secLabel.setForeground(new java.awt.Color(255, 255, 255));
        if(sec<10)
            secLabel.setText(" 0"+sec);
        else secLabel.setText(" "+sec);
        secLabel.setPreferredSize(new java.awt.Dimension(120, 68));

        colonLabel.setFont(new java.awt.Font("Calibri", 1, 100)); 
        colonLabel.setForeground(new java.awt.Color(255, 255, 255));
        colonLabel.setText(":");        
        javax.swing.GroupLayout clockPanelLayout = new javax.swing.GroupLayout(clockPanel);
        clockPanel.setLayout(clockPanelLayout);
        clockPanelLayout.setHorizontalGroup(
            clockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clockPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(minLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colonLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(secLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );
        clockPanelLayout.setVerticalGroup(
            clockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colonLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(secLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );        
        
        panel.add(clockPanel);
        

        
        
        
        
        
        
        JPanel Target1Panel = new JPanel();
        JPanel Target2Panel = new JPanel();
        JPanel Target3Panel = new JPanel();
        JPanel Target4Panel = new JPanel();
        JPanel Target5Panel = new JPanel();
        JPanel Target6Panel = new JPanel();
        

        
        target1Done.setEnabled(false);
        target2Done.setEnabled(false);
        target3Done.setEnabled(false);
        target4Done.setEnabled(false);
        target5Done.setEnabled(false);
        target6Done.setEnabled(false);
        
        
        UpdateTask();
        
        
        Target1Panel.setBackground(new java.awt.Color(255, 255, 153));
        Target1Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        Target1Panel.setPreferredSize(new java.awt.Dimension(660, 100));

        target1Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if((lists.SixTargets[0]!=null)&&clock.isRunning())
                {   
                    lists.SixTargets[0].Tickoff();
                    task1.setText("完成任务");
                }
            }
        });

        task1.setFont(new java.awt.Font("新细明体", 0, 25)); 
        task1.setPreferredSize(new java.awt.Dimension(250, 50));

        javax.swing.GroupLayout Target1PanelLayout = new javax.swing.GroupLayout(Target1Panel);
        Target1Panel.setLayout(Target1PanelLayout);
        Target1PanelLayout.setHorizontalGroup(
            Target1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Target1PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(target1Done)
                .addContainerGap())
            .addGroup(Target1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target1PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Target1PanelLayout.setVerticalGroup(
            Target1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Target1PanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(target1Done, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Target1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target1PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Target2Panel.setBackground(new java.awt.Color(255, 255, 153));
        Target2Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        Target2Panel.setPreferredSize(new java.awt.Dimension(660, 100));

        target2Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if((lists.SixTargets[1]!=null)&&clock.isRunning())
                {
                    lists.SixTargets[1].Tickoff();
                    task2.setText("完成任务");
                }
            }
        });

        task2.setFont(new java.awt.Font("新细明体", 0, 25)); 
        task2.setPreferredSize(new java.awt.Dimension(250, 50));

        javax.swing.GroupLayout Target2PanelLayout = new javax.swing.GroupLayout(Target2Panel);
        Target2Panel.setLayout(Target2PanelLayout);
        Target2PanelLayout.setHorizontalGroup(
            Target2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Target2PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(target2Done)
                .addContainerGap())
            .addGroup(Target2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target2PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Target2PanelLayout.setVerticalGroup(
            Target2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Target2PanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(target2Done, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(Target2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target2PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Target3Panel.setBackground(new java.awt.Color(255, 255, 153));
        Target3Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        Target3Panel.setPreferredSize(new java.awt.Dimension(660, 100));

        target3Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if((lists.SixTargets[02]!=null)&&clock.isRunning())
                {
                    lists.SixTargets[2].Tickoff();
                    task3.setText("完成任务");
                }
            }
        });

        task3.setFont(new java.awt.Font("新细明体", 0, 25)); 
        task3.setPreferredSize(new java.awt.Dimension(250, 50));

        javax.swing.GroupLayout Target3PanelLayout = new javax.swing.GroupLayout(Target3Panel);
        Target3Panel.setLayout(Target3PanelLayout);
        Target3PanelLayout.setHorizontalGroup(
            Target3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Target3PanelLayout.createSequentialGroup()
                .addContainerGap(610, Short.MAX_VALUE)
                .addComponent(target3Done)
                .addContainerGap())
            .addGroup(Target3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target3PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Target3PanelLayout.setVerticalGroup(
            Target3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Target3PanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(target3Done, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(Target3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target3PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Target4Panel.setBackground(new java.awt.Color(255, 255, 153));
        Target4Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        Target4Panel.setPreferredSize(new java.awt.Dimension(660, 100));

        target4Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if((lists.SixTargets[3]!=null)&&clock.isRunning())
                {
                    lists.SixTargets[3].Tickoff();
                    task4.setText("完成任务");
                }
            }
        });

        task4.setFont(new java.awt.Font("新细明体", 0, 25)); 
        task4.setPreferredSize(new java.awt.Dimension(250, 50));

        javax.swing.GroupLayout Target4PanelLayout = new javax.swing.GroupLayout(Target4Panel);
        Target4Panel.setLayout(Target4PanelLayout);
        Target4PanelLayout.setHorizontalGroup(
            Target4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Target4PanelLayout.createSequentialGroup()
                .addContainerGap(610, Short.MAX_VALUE)
                .addComponent(target4Done)
                .addContainerGap())
            .addGroup(Target4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target4PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Target4PanelLayout.setVerticalGroup(
            Target4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Target4PanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(target4Done, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(Target4PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target4PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Target5Panel.setBackground(new java.awt.Color(255, 255, 153));
        Target5Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        Target5Panel.setPreferredSize(new java.awt.Dimension(660, 100));

        target5Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if((lists.SixTargets[4]!=null)&&clock.isRunning())
                {
                    lists.SixTargets[4].Tickoff();
                    task5.setText("完成任务");
                }
            }
        });

        task5.setFont(new java.awt.Font("新细明体", 0, 25)); 
        task5.setPreferredSize(new java.awt.Dimension(250, 50));

        javax.swing.GroupLayout Target5PanelLayout = new javax.swing.GroupLayout(Target5Panel);
        Target5Panel.setLayout(Target5PanelLayout);
        Target5PanelLayout.setHorizontalGroup(
            Target5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Target5PanelLayout.createSequentialGroup()
                .addContainerGap(610, Short.MAX_VALUE)
                .addComponent(target5Done)
                .addContainerGap())
            .addGroup(Target5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target5PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Target5PanelLayout.setVerticalGroup(
            Target5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Target5PanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(target5Done, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(Target5PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target5PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        Target6Panel.setBackground(new java.awt.Color(255, 255, 153));
        Target6Panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        Target6Panel.setPreferredSize(new java.awt.Dimension(660, 100));

        target6Done.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if((lists.SixTargets[5]!=null)&&clock.isRunning())
                {
                    lists.SixTargets[5].Tickoff();
                    task6.setText("完成任务");
                }
            }
        });

        task6.setFont(new java.awt.Font("新细明体", 0, 25)); 
        task6.setPreferredSize(new java.awt.Dimension(250, 50));

        javax.swing.GroupLayout Target6PanelLayout = new javax.swing.GroupLayout(Target6Panel);
        Target6Panel.setLayout(Target6PanelLayout);
        Target6PanelLayout.setHorizontalGroup(
            Target6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Target6PanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(target6Done)
                .addContainerGap())
            .addGroup(Target6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target6PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        Target6PanelLayout.setVerticalGroup(
            Target6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Target6PanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(target6Done, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(Target6PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Target6PanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(task6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Target1Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clockPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Target2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Target3Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Target4Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Target5Panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Target6Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(clockPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Target1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Target2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Target3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Target4Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Target5Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(Target6Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );        

        
        
        //Setting the frame size
        frame.setSize(new Dimension(600,930));
        frame.setResizable(false);
        //Setting the start position
        frame.setLocationRelativeTo(null);
        //Set a default close action
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we)
            {
                Date today = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = formatter.format(today);
                Iterator<Task> iter = lists.Unfinished.iterator();
                while(iter.hasNext())
                {
                    Task t1 = iter.next();
                    if((t1.isCompleted())||(today.compareTo(t1.getDueDate())>0))
                    {
                        if(t1.isCompleted())
                            iter.remove();
                        else if(!t1.getDueDateString().equals(todayString))
                        {
                            iter.remove();
                        }
                        
                    }
                }

                SaveUnfinshed();
                System.exit(0);
            }
        });
        frame.setVisible(true);        
    }
    
        private static void addTask()
    {
        JFrame AddTask = new JFrame("添加任务");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel.setBackground(new java.awt.Color(255, 255, 153));
        AddTask.getContentPane().add(panel);
        
        JLabel TaskName = new JLabel("任务名称: ");
        TaskName.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(TaskName,c);
        c.gridy++;
        JLabel Importance = new JLabel("重要性: ");
        Importance.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(Importance,c);
        c.gridy++;       
        JLabel Urgency = new JLabel("紧急性: ");
        Urgency.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(Urgency,c);
        c.gridy++;
        JLabel DueDate = new JLabel("任务期限: ");
        DueDate.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(DueDate,c);
        c.gridy++;

        c.gridx = 1;
        c.gridy = 0;
        String[] data = {"0","1","2","3","4","5","6","7","8","9","10"};
        c.anchor = GridBagConstraints.LINE_START;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(180,30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;        
        panel.add(nameField,c);
        c.gridy++;
        JComboBox importanceField = new JComboBox(data);
        importanceField.setEditable(true);
        importanceField.setPreferredSize(new Dimension(180,30)); 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;        
        panel.add(importanceField,c);
        c.gridy++;        
        JComboBox urgencyField = new JComboBox(data);
        urgencyField.setEditable(true);      
        urgencyField.setPreferredSize(new Dimension(180,30));  
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;        
        panel.add(urgencyField,c);
        c.gridy++;        
        JComboBox yearField = new JComboBox();
        for(int i=2000;i<2100;i++){
            yearField.addItem(i);
        }
        JComboBox monthField = new JComboBox();
        for(int i=1;i<13;i++)
        {
            if(i<10)
                monthField.addItem("0"+i);
            else monthField.addItem(i);
        }
        JComboBox dayField = new JComboBox();
        for(int i=1;i<32;i++)
        {
            if(i<10)
                dayField.addItem("0"+i);            
            else dayField.addItem(i);
        }


        c.gridwidth = 1;
        panel.add(yearField,c);
        c.gridx++;        
        c.gridwidth = 1;
        panel.add(monthField,c);
        c.gridx++;
        c.gridwidth = 1;
        panel.add(dayField,c);
        c.gridy++;
        
        
        JButton Submit = new JButton("Sumbit");
        Submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                String name = nameField.getText();
                String importance = importanceField.getSelectedItem().toString();
                String urgency = urgencyField.getSelectedItem().toString();
                String date = yearField.getSelectedItem().toString()+"-"+monthField.getSelectedItem().toString()+"-"+dayField.getSelectedItem().toString();
                
                if((!name.isEmpty())&&(!importance.isEmpty())&&(!urgency.isEmpty())&&(!date.isEmpty()))
                {
                    try {
                    lists.addTask(name,importance,urgency,date);
                    JDialog confirm = new JDialog(AddTask,"Confirm");
                    JLabel Success = new JLabel("成功添加!");
                    confirm.add(Success);
                    confirm.setSize(200,200);
                    confirm.setLocationRelativeTo(null);
                    confirm.setVisible(true);
                    lists.Unfinished.sort(new TaskComparator());
                    lists.RefreshTargets();;
                    UpdateTask();
                    } catch (Exception ex) {
                        Logger.getLogger(TimeManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                


        }
        });
        
        panel.add(Submit,c);


        
        AddTask.setSize(new Dimension(400,400));    
        AddTask.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        AddTask.setLocationRelativeTo(null);
        AddTask.setResizable(false);
        AddTask.setVisible(true);
        
        
        
        
        
        
    }
        
        private static void ViewAll()
        {
            JFrame ViewAll = new JFrame("全部任务");
            JPanel task = new JPanel();
            task.setLayout(new GridBagLayout());
            GridBagConstraints c2 = new GridBagConstraints();
            c2.gridx = 0;
            c2.gridy = 0;
            c2.gridheight = 2;
            JLabel[] tempTask = new JLabel[lists.Unfinished.size()];
            JButton[] tempSelect = new JButton[lists.Unfinished.size()];
            JButton[] tempChange = new JButton[lists.Unfinished.size()];
            JButton[] tempDelete = new JButton[lists.Unfinished.size()];
            
            
            task.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            task.setBackground(new java.awt.Color(255, 255, 153));            
            for(int i=0;i<lists.Unfinished.size();i++)
            {
                if(lists.Unfinished.get(i).isCompleted())
                    continue;
                JLabel taskLabel = new JLabel();
                JLabel importanceLabel = new JLabel();
                JLabel urgencyLabel = new JLabel();
                JLabel totalLabel = new JLabel();
                JLabel dateLabel = new JLabel();
                taskLabel.setText("任务名称: "+lists.Unfinished.get(i).getTaskName());
                importanceLabel.setText("重要性: "+lists.Unfinished.get(i).getImportance());
                urgencyLabel.setText("紧急性: "+lists.Unfinished.get(i).getUrgency());
                totalLabel.setText("总分: "+lists.Unfinished.get(i).getTotal());
                dateLabel.setText("任务期限： "+lists.Unfinished.get(i).getDueDateString());
                taskLabel.setFont(new java.awt.Font("新细明体",Font.BOLD , 15));
                importanceLabel.setFont(new java.awt.Font("新细明体",Font.BOLD , 15));
                urgencyLabel.setFont(new java.awt.Font("新细明体",Font.BOLD , 15));
                totalLabel.setFont(new java.awt.Font("新细明体",Font.BOLD , 15));
                dateLabel.setFont(new java.awt.Font("新细明体",Font.BOLD , 15));
                taskLabel.setPreferredSize(new Dimension(250,30));
                importanceLabel.setPreferredSize(new Dimension(100,30));
                urgencyLabel.setPreferredSize(new Dimension(100,30));
                totalLabel.setPreferredSize(new Dimension(100,30));
                dateLabel.setPreferredSize(new Dimension(200,30));
                JButton Select = new JButton();
                JButton Change = new JButton();
                JButton Delete = new JButton();
                Select.setText("选择");
                Change.setText("修改");
                Delete.setText("删除");
                
                
                c2.gridy++;
                task.add(taskLabel,c2);
                c2.gridx++;
                task.add(importanceLabel,c2);
                c2.gridx++;
                task.add(urgencyLabel,c2);
                c2.gridx++;
                task.add(totalLabel,c2);
                c2.gridx++;
                task.add(dateLabel,c2);
                c2.gridx++;
                task.add(Select,c2);
                c2.gridx++;
                task.add(Change,c2);
                c2.gridx++;              
                task.add(Delete,c2);
                c2.gridx = 0;           
                c2.gridy++;
                
                tempTask[i] = taskLabel;
                tempSelect[i] = Select;
                tempChange[i] = Change;
                tempDelete[i] = Delete;
                
                
                tempDelete[i].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    {
                        lists.Unfinished.get(Helper(tempDelete,e.getSource())).Tickoff();
                        tempTask[Helper(tempDelete,e.getSource())].setText("已删除");
                        lists.RefreshTargets();
                        UpdateTask();
                        
                    }
                });
                
                tempChange[i].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent a)
                    {
                        ChangeInfo(lists.Unfinished.get(Helper(tempChange,a.getSource())));
                        ViewAll.dispose();
                    }
                });
                
                tempSelect[i].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent ae)
                    {
                        if(lists.mode==false)
                        {
                            JDialog confirm = new JDialog(ViewAll,"模式错误");
                            JLabel Success = new JLabel("请先进入紧急模式");
                            confirm.add(Success);
                            confirm.setSize(200,200);
                            confirm.setLocationRelativeTo(null);
                            confirm.setVisible(true);
                        }
                        else
                        {
                            for(int i=0;i<6;i++)
                            {
                                lists.SixTargets[i] = lists.Unfinished.get(Helper(tempSelect,ae.getSource()));

                            }
                            UpdateTask();
                            JDialog confirm = new JDialog(ViewAll,"Confirm");
                            JLabel Success = new JLabel("选择成功!");
                            confirm.add(Success);
                            confirm.setSize(200,200);
                            confirm.setLocationRelativeTo(null);
                            confirm.setVisible(true);
                        }
                    }
                });                
 
            }
            
            
        ViewAll.getContentPane().add(task);
        ViewAll.setSize(new Dimension(1000,930));    
        ViewAll.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ViewAll.setLocationRelativeTo(null);
        ViewAll.setResizable(false);
        ViewAll.setVisible(true);
            
            
        }
    
        
        private static<T> int Helper(T[] oArray,T obj)
        {     
            for(int i=0;i<oArray.length;i++)
            {
                if(obj.equals(oArray[i]))
                    return i;
            }
            return -1;
            
        }
        
        
        private static void ChangeInfo(Task t)
    {
        JFrame ChangeTask = new JFrame("修改任务");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel.setBackground(new java.awt.Color(255, 255, 153));
        ChangeTask.getContentPane().add(panel);
        
        JLabel TaskName = new JLabel("任务名称: ");
        TaskName.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(TaskName,c);
        c.gridy++;
        JLabel Importance = new JLabel("重要性: ");
        Importance.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(Importance,c);
        c.gridy++;       
        JLabel Urgency = new JLabel("紧急性: ");
        Urgency.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(Urgency,c);
        c.gridy++;
        JLabel DueDate = new JLabel("任务期限: ");
        DueDate.setFont(new Font("新细明体", Font.BOLD, 20));
        panel.add(DueDate,c);
        c.gridy++;

        c.gridx = 1;
        c.gridy = 0;
        String[] data = {"0","1","2","3","4","5","6","7","8","9","10"};
        c.anchor = GridBagConstraints.LINE_START;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(180,30));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;        
        panel.add(nameField,c);
        c.gridy++;
        JComboBox importanceField = new JComboBox(data);
        importanceField.setEditable(true);
        importanceField.setPreferredSize(new Dimension(180,30)); 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;        
        panel.add(importanceField,c);
        c.gridy++;        
        JComboBox urgencyField = new JComboBox(data);
        urgencyField.setEditable(true);      
        urgencyField.setPreferredSize(new Dimension(180,30));  
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;        
        panel.add(urgencyField,c);
        c.gridy++;        
        JComboBox yearField = new JComboBox();
        for(int i=2000;i<2100;i++){
            yearField.addItem(i);
        }
        JComboBox monthField = new JComboBox();
        for(int i=1;i<13;i++)
        {
            if(i<10)
                monthField.addItem("0"+i);
            else monthField.addItem(i);
        }
        JComboBox dayField = new JComboBox();
        for(int i=1;i<32;i++)
        {
            if(i<10)
                dayField.addItem("0"+i);            
            else dayField.addItem(i);
        }


        c.gridwidth = 1;
        panel.add(yearField,c);
        c.gridx++;        
        c.gridwidth = 1;
        panel.add(monthField,c);
        c.gridx++;
        c.gridwidth = 1;
        panel.add(dayField,c);
        c.gridy++;
        
        
        JButton Change = new JButton("修改");
        Change.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                String name = nameField.getText();
                String importance = importanceField.getSelectedItem().toString();
                String urgency = urgencyField.getSelectedItem().toString();
                String date = yearField.getSelectedItem().toString()+"-"+monthField.getSelectedItem().toString()+"-"+dayField.getSelectedItem().toString();
                
                if((!name.isEmpty())&&(!importance.isEmpty())&&(!urgency.isEmpty())&&(!date.isEmpty()))
                {
                    try {
                        t.setTaskName(name);
                        t.setImportance(importance);
                        t.setUrgency(urgency);
                        t.setDueDate(date);
//                        t.setIncrementFactor(t.CalculateIncrementFactor());
                                               
                        lists.Unfinished.sort(new TaskComparator());
                        lists.RefreshTargets();
                        UpdateTask();
                        
                        ChangeTask.dispose();
                        ViewAll();
                        
                    } catch (Exception ex) {
                        Logger.getLogger(TimeManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                


        }
        });
        
        panel.add(Change,c);


        
        ChangeTask.setSize(new Dimension(400,400));    
        ChangeTask.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ChangeTask.setLocationRelativeTo(null);
        ChangeTask.setResizable(false);
        ChangeTask.setVisible(true);
        
        
        
        
        
        
    }
        
        
        
        
        
        public static void rotate()
        {
            //Remove finished targets
            ArrayList<Task> temp = new ArrayList<Task>();
            
            for(int i=0;i<6;i++)
            {
                if(lists.SixTargets[i]!=null)
                {
                    if(!lists.SixTargets[i].isCompleted())
                        temp.add(lists.SixTargets[i]);
                }
                
            }
            temp.sort(new TaskComparator());
            Task[] temp2 = new Task[temp.size()];
            
        if(temp.size()==6)
        {
            for(int i=0;i<6;i++)
            {
                temp2[i] = temp.get(i);
            }
        }
        else
        {
            
            for(int i=0;i<temp.size();i++)
            {
                temp2[i] = temp.get(i);
            }            
        }            
            
        
        lists.SixTargets[temp.size()-1] = temp2[0];
        for(int i=0;i<temp.size()-1;i++)
        {
            lists.SixTargets[i] = temp2[i+1];
        };
        for(int i =temp.size();i<6;i++)
        {
            lists.SixTargets[i] = null;
        }
        
        
        }
        
        public static void SaveUnfinshed()
        {
            try{
                DataOutputStream out = new DataOutputStream(new FileOutputStream("D:/Java/Project/JavaProject/src/project/TaskList.dat"));
                for(int i=0;i<lists.Unfinished.size();i++)
                {
                    out.writeBytes(lists.Unfinished.get(i).toPlainText());
                    out.writeBytes("\n");
                }
                out.close();
            }
            catch(Exception e)
            {e.printStackTrace();}
        }
        
        public static void ReadUnfinished()
        {
            try{
                
                Scanner scan = new Scanner(new File("D:/Java/Project/JavaProject/src/project/TaskList.dat"));
                scan.useDelimiter("[\t\n]");
                while(scan.hasNext())
                {
                    String[] oneTask = new String[4];
                    for(int i=0;i<4;i++)
                    {
                        oneTask[i] = scan.next();
                    }
                    lists.addTask(oneTask[0],oneTask[1], oneTask[2], oneTask[3]);
                }
                
            }
            catch(Exception e){e.printStackTrace();}
            
            
        }
        
        
        
        private static void UpdateTask()
        {              
            if((lists.SixTargets[0]!=null)&&(!lists.SixTargets[0].isCompleted()))
            {                
                task1.setText(lists.SixTargets[0].getTaskName());
            }
            else task1.setText("目前没有任务");
                
            if((lists.SixTargets[1]!=null)&&(!lists.SixTargets[1].isCompleted()))
            {
                task2.setText(lists.SixTargets[1].getTaskName());
            }
            else task2.setText("目前没有任务");
           
            if((lists.SixTargets[2]!=null)&&(!lists.SixTargets[2].isCompleted()))
            {
                task3.setText(lists.SixTargets[2].getTaskName());
            }
            else task3.setText("目前没有任务");
            
            if((lists.SixTargets[3]!=null)&&(!lists.SixTargets[3].isCompleted()))
            {
                task4.setText(lists.SixTargets[3].getTaskName());
            }
            else task4.setText("目前没有任务");

            if((lists.SixTargets[4]!=null)&&(!lists.SixTargets[4].isCompleted()))
            {
                task5.setText(lists.SixTargets[4].getTaskName());
            }
            else task5.setText("目前没有任务");

            if((lists.SixTargets[5]!=null)&&(!lists.SixTargets[5].isCompleted()))
            {
                task6.setText(lists.SixTargets[5].getTaskName());
            }
            else task6.setText("目前没有任务");            
        }
        
    
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
        public void run()
        {
            ReadUnfinished();      
//            for(int i=0;i<lists.Unfinished.size();i++)
//            {
//                lists.Unfinished.get(i).markIncrement();
//            }
            lists.Unfinished.sort(new TaskComparator());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String today = formatter.format(new Date());
            int stop = 0;
            for(int i =0;i<lists.Unfinished.size();i++)
            {
                if(lists.Unfinished.get(i).getDueDateString().equals(today))
                {
                    lists.changeMode();
                    stop = i;
                    break;
                }
            }

            if(lists.mode==true)
            {
                for(int i=0;i<6;i++)
                {
                    lists.SixTargets[i] = lists.Unfinished.get(stop);
                }
                Mode.setBackground(Color.RED);
                Mode.setText("紧急模式");

            }
            else lists.RefreshTargets();
            mainFrame();
            
                        
        }
    }
        );    
    }
    
    
    
}

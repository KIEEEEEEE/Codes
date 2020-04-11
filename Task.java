package project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Jackie Leung
 */
public class Task implements java.lang.Comparable,Serializable {
    String taskName;                //Name of the task
    double Importance;              //Importance marks of the task
    double Urgency;                 //Urgency marks of the task
    double Total;                   //Total marks of the task
    Date dueDate;                   //Due date of the task
    double incrementFactor;         //Urgency Marks increase per day
    boolean status;                 //The status of the task: true = finished; false = unfinished
    
    public Task()
    {
       taskName = "";
       Importance = 0;                      
       Urgency = 0;              
       Total =0;
       Date dueDate = new Date();          
       incrementFactor = 0;                 
       status = false;           
       
    }
    
    public Task(String taskName,String Importance,String Urgency,String date) throws Exception
    {
        this.taskName = taskName;
        this.Importance = Double.parseDouble(Importance);    
        this.Urgency = Double.parseDouble(Urgency);
        this.Total = this.Importance + this.Urgency;
        dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        incrementFactor = CalculateIncrementFactor();                                         //(difference between current urgency marks and full marks)/difference in days between currect date and due date
        status = false;
    }
    
    public String getTaskName()
    {
        return taskName;
    }
    
    public void setTaskName(String name)
    {
        this.taskName = name;
    }
    

    public double getImportance()
    {
        return Importance;
    }
    
    public void setImportance(String Importance)
    {
        this.Importance = Double.parseDouble(Importance);
        this.Total = this.Importance + this.Urgency;
    }
    
    public double getUrgency()
    {
        return Urgency;
    }
    
    public void setUrgency(String Urgency)
    {
        this.Urgency = Double.parseDouble(Urgency);
        this.Total = this.Importance + this.Urgency;
        incrementFactor = CalculateIncrementFactor();
        
    }
    
    public double getTotal()
    {
        return Total;
    }

    public void setDueDate(String date) throws Exception
    {
        dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        incrementFactor = CalculateIncrementFactor();
    }
    
    public boolean isCompleted()
    {
        return status;
    }
    
    public void Tickoff()
    {
        status = true;
    }
    
    public void setIncrementFactor(double factor)
    {
        this.incrementFactor = factor;
    }
    
    public void markIncrement()
    {
        if(Urgency<TimeManager.UrgencyFullMarks)
        {
            this.Urgency += incrementFactor;
            if(this.Urgency>TimeManager.UrgencyFullMarks)
                this.Urgency = TimeManager.UrgencyFullMarks;
        }
        else this.Urgency = TimeManager.UrgencyFullMarks;
        this.Total = this.Urgency + this.Importance;
        
    }
    
    public double CalculateIncrementFactor()
    {
        Date today = new Date();                                                         //Calculate the increment factor: 
        long diffInMillies = Math.abs(dueDate.getTime()-today.getTime());
        long dayDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);     //Calculate the number of days from due date
        double marksDiff = TimeManager.UrgencyFullMarks - this.Urgency;
        
        return marksDiff/dayDiff;
    }
    
    public int compareTo(Object o)
    {
        Task task = (Task)o;
        return this.dueDate.compareTo(task.dueDate);
    }
    
    public String toPlainText()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return (this.taskName+"\t"+this.Importance+"\t"+this.Urgency+"\t"+formatter.format(this.dueDate));
    }

    public String getDueDateString()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(this.dueDate);
    }
    
    public Date getDueDate()
    {
        return this.dueDate;
    }
    


    
    

    
    
}

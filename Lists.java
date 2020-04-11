package project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author Jackie Leung
 */
public class Lists {
    public static ArrayList<Task>  Unfinished = new ArrayList<Task>();

    Task[] SixTargets = new Task[6];
    boolean mode = false;
    
    //Create a new task and add it to the Unfinished list
    public void addTask(String name,String importance,String urgency, String date) throws Exception
    {
        Unfinished.add(new Task(name,importance,urgency,date));
    }
        
    
    public void changeMode()
    {
        if(this.mode==false)
            this.mode = true;
        else if(this.mode==true)
        {
            this.mode = false;
        }
    }
    
    public void initTargets()
    {
        for(int i=0;i<6;i++)
        {
            SixTargets[i] = null;
        }
    }
    
    public String getTask(int index)
    {   
        if(SixTargets[index] != null)
        {
            return SixTargets[index].getTaskName();
        }
        else 
            return null;
        

    }
    
    
    
    public void RefreshTargets()
    {
        ArrayList<Task> temp = new ArrayList<Task>();
//        if(Unfinished.size()>=6)
//        {
            for(int i=0;(temp.size()<6)&&(i<Unfinished.size());i++)
            {
                if((Unfinished.get(i)!=null)&&(!Unfinished.get(i).isCompleted()))
                {
                    temp.add(Unfinished.get(i));
                }
//                else
//                {
//                    SixTargets[i] = null;
//                }
            }
//        }
//        else
//        {
//            for(int i=0;i<Unfinished.size();i++)
//            {
//                if((Unfinished.get(i)!=null)&&(!Unfinished.get(i).isCompleted()))
//                {
//                    SixTargets[i] = Unfinished.get(i);
//                }
//                else
//                {
//                    SixTargets[i] = null;
//                }
//            }            
//        }

        if(temp.size()==6)
        {
            for(int i=0;i<6;i++)
            {
                SixTargets[i] = temp.get(i);
            }
        }
        else
        {
            for(int i=0;i<temp.size();i++)
            {
                SixTargets[i] = temp.get(i);
            }
            for(int i=temp.size();i<6;i++)
            {
                SixTargets[i] = null;
            }
        }
        
    }
    
    public boolean isAllFinished()
    {
        for(int i=0;i<6;i++)
        {
            if(SixTargets[i]!=null)
            {
                if(!SixTargets[i].isCompleted())
                    return false;
            }
        }
        return true;
    }
}

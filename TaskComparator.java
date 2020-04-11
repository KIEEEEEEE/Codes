package project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jackie Leung
 */
public class TaskComparator implements java.util.Comparator
{
    public int compare(Object o1,Object o2)
    {
        Task task1 = (Task)o1;
        Task task2 = (Task)o2;
        double task1Total = task1.getTotal();
        double task2Total = task2.getTotal();
        if(task1Total<task2Total)
        {
            return 1;
        }
        else if(task1Total>task2Total)
        {
            return -1;
        }
        else{
           return task1.compareTo(o2);
        }

    }
    
}

package com.idgform.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Task
{

    @Id
    @GeneratedValue
    private long id;
    private String taskDescription;
    private Date startTime;
    private Date endTime;
    @ManyToOne
    private FormUser formUser;
    private Date dateAdded;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public FormUser getFormUser()
    {
        return formUser;
    }

    public void setFormUser(FormUser formUser)
    {
        this.formUser = formUser;
    }

    @Override
    public String toString()
    {
        return "Task: " + taskDescription + " Start Time: " + startTime + " End Time: " + endTime;
    }

    public Date getDateAdded()
    {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
    }

}

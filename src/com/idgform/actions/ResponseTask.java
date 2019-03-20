package com.idgform.actions;

import com.idgform.models.Task;
import com.idgform.utils.DateParser;

public class ResponseTask
{

    private long id;
    private String taskDescription;
    private String startTime;
    private String endTime;

    public ResponseTask(Task task)
    {
        setStartTime(DateParser.dateToString(task.getStartTime()));
        setEndTime(DateParser.dateToString(task.getEndTime()));
        setId(task.getId());
        setTaskDescription(task.getTaskDescription());
    }

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

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

}

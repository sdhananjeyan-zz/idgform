package com.idgform.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Operations
{

    @Id
    @GeneratedValue
    private long id;
    private Date actionTime;
    private String Description;
    @ManyToOne
    private User editedUser;
    @ManyToOne
    private Task task;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public Date getActionTime()
    {
        return actionTime;
    }

    public void setActionTime(Date actionTime)
    {
        this.actionTime = actionTime;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public User getEditedUser()
    {
        return editedUser;
    }

    public void setEditedUser(User editedUser)
    {
        this.editedUser = editedUser;
    }

}

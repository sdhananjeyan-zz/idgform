package com.idgform.actions;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;

import com.idgform.core.FactoryGenerator;
import com.idgform.models.Task;
import com.idgform.utils.DateParser;

public class UpdateTask implements SessionAware
{

    private Map<String, Object> session = new HashMap<>();
    private String description;
    private String startTime;
    private String endTime;
    private long id;
    private Map<String, Object> response = new HashMap<>();

    public String execute()
    {
        String result = "success";
        if (session.get("activeUser") == null)
        {
            response.put("status", "failed");
            response.put("message", "access denied");
            return result;
        }
        Session hbSession = FactoryGenerator.getHibernateSessionFactory().openSession();
        try
        {
            hbSession.beginTransaction();
            Task task = (Task) hbSession.load(Task.class, id);

            task.setTaskDescription(description);
            task.setEndTime(DateParser.stringToDate(endTime + ":00"));
            task.setStartTime(DateParser.stringToDate(startTime + ":00"));
            hbSession.update(task);
            hbSession.getTransaction().commit();
        }
        catch (ParseException e)
        {
            response.put("status", "failed");
            response.put("message", "invalid input supplied");
            hbSession.getTransaction().rollback();
            return result;
        }
        catch (Exception e)
        {
            response.put("status", "failed");
            response.put("message", "internal error");
            hbSession.getTransaction().rollback();
            return result;
        }
        finally
        {
            if (hbSession.isOpen())
            {
                hbSession.close();
            }
        }
        response.put("status", "success");
        response.put("message", "Task successfully updated");
        return result;
    }

    @Override
    public void setSession(Map<String, Object> session)
    {
        this.session = session;
    }

    public Map<String, Object> getResponse()
    {
        return response;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public void setId(long id)
    {
        this.id = id;
    }

}

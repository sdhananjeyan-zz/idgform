package com.idgform.actions;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;

import com.idgform.core.FactoryGenerator;
import com.idgform.models.FormUser;
import com.idgform.models.Task;
import com.idgform.utils.DateParser;

public class AddTask implements SessionAware
{

    private String description;
    private String startTime;
    private String endTime;
    private Map<String, Object> response = new HashMap<>();
    private Map<String, Object> session = new HashMap<>();

    private static final Logger logger = Logger.getLogger(AddTask.class);

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

    public Map<String, Object> getResponse()
    {
        return response;
    }

    public String execute()
    {
        logger.debug("executing");
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
            FormUser formUser = (FormUser) hbSession.load(FormUser.class, (Long) session.get("activeUser"));
            Task task = new Task();
            task.setTaskDescription(description);
            task.setEndTime(DateParser.stringToDate(endTime + ":00"));
            task.setStartTime(DateParser.stringToDate(startTime + ":00"));
            task.setFormUser(formUser);
            task.setDateAdded(new Date());
            hbSession.save(task);
            hbSession.getTransaction().commit();
        }
        catch (ParseException e)
        {
            response.put("status", "failed");
            response.put("message", "invalid input supplied");
            return result;
        }
        catch (Exception e)
        {
            response.put("status", "failed");
            response.put("message", e.getMessage());
            response.put("stack", e.getStackTrace());
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
        response.put("message", "Task successfully added");
        return result;
    }

    public String getDescription()
    {
        return description;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    @Override
    public void setSession(Map<String, Object> session)
    {
        this.session = session;

    }

}

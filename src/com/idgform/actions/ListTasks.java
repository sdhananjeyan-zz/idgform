package com.idgform.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;

import com.idgform.core.FactoryGenerator;
import com.idgform.models.Task;

public class ListTasks implements SessionAware
{

    private Map<String, Object> session;
    private Map<String, Object> response = new HashMap<>();

    @Override
    public void setSession(Map<String, Object> sessionMap)
    {
        session = sessionMap;
    }

    public String execute()
    {
        String result = "success";
        if (session.get("activeUser") == null)
        {
            response.put("status", "Un-Authorized Access");
            return result;
        }
        List<Task> tasks;
        Session hbSession = FactoryGenerator.getHibernateSessionFactory().openSession();
        try
        {
            hbSession.beginTransaction();
            tasks = hbSession.createCriteria(Task.class).list();
            List<ResponseTask> responseTasks = new ArrayList<>();
            for (Task task : tasks)
            {
                responseTasks.add(new ResponseTask(task));
            }
            response.put("status", "success");
            response.put("data", responseTasks);
            hbSession.getTransaction().commit();
        }
        catch (Exception e)
        {
            response.clear();
            response.put("status", "error");
            response.put("message", "something went wrong please try again later " + e);
        }
        finally
        {
            if (hbSession.isOpen())
            {
                hbSession.close();
            }
        }

        return result;
    }

    public Map<String, Object> getResponse()
    {
        return response;
    }

}

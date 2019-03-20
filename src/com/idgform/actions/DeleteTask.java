package com.idgform.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;

import com.idgform.core.FactoryGenerator;
import com.idgform.models.Task;

public class DeleteTask implements SessionAware
{
    private Map<String, Object> session = new HashMap<>();
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
            hbSession.delete(task);
            hbSession.getTransaction().commit();
        }
        catch (Exception e)
        {
            response.put("status", "failed");
            response.put("message", e.getMessage());
            response.put("stack", e.getStackTrace());
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
        response.put("message", "Task successfully deleted");
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

    public void setId(long id)
    {
        this.id = id;
    }

}

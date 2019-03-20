package com.idgform.actions;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import com.idgform.core.FactoryGenerator;
import com.idgform.models.FormUser;

public class Login implements SessionAware
{

    private static final Logger logger = Logger.getLogger(Login.class);
    private Map<String, Object> session;
    private long userId;
    private String password;
    private String logedInUser;

    public String execute()
    {
        String result = "";
        Session hbSession = FactoryGenerator.getHibernateSessionFactory().openSession();
        hbSession.beginTransaction();
        try
        {
            result = "";
            FormUser user = (FormUser) hbSession.load(FormUser.class, new Long(userId));
            if (user.getPassword().equals(password))
            {

                session.put("activeUser", userId);
                logedInUser = user.getName();
                result = "success";
            }
            else
            {
                result = "auth_failed";
            }
        }
        catch (ObjectNotFoundException e)
        {
            result = "no_user";
        }
        catch (Exception e)
        {
            logger.debug("failure");
            logger.debug(e);
            logger.debug(e.getStackTrace());
            result = "error";
            hbSession.getTransaction().rollback();
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

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public void setSession(Map<String, Object> sessionMap)
    {
        session = sessionMap;
    }

    public String getLogedInUser()
    {
        return logedInUser;
    }

}

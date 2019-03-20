package com.idgform.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class Logout implements SessionAware
{
    private Map<String, Object> sessionMap;

    public String execute()
    {
        sessionMap.remove("activeUser");
        return "success";
    }

    @Override
    public void setSession(Map<String, Object> sessionMap)
    {
        this.sessionMap = sessionMap;

    }
}

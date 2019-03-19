package com.idgform.actions;

public class ListTasks
{

    private String testString = "test";
    private int testInt = 123;

    public String getTestString()
    {
        return testString;
    }

    public void setTestString(String testString)
    {
        this.testString = testString;
    }

    public int getTestInt()
    {
        return testInt;
    }

    public void setTestInt(int testInt)
    {
        this.testInt = testInt;
    }

    public String execute()
    {
        return "success";
    }

}

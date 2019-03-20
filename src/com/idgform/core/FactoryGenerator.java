package com.idgform.core;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

import com.idgform.models.FormUser;
import com.idgform.models.Operations;
import com.idgform.models.Task;

public class FactoryGenerator
{

    public static final SessionFactory sessionFactory;
    static
    {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(FormUser.class);
        configuration.addAnnotatedClass(Task.class);
        configuration.addAnnotatedClass(Operations.class);

        configuration.configure();
        sessionFactory = configuration.buildSessionFactory(
                new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry());
    }

    public static SessionFactory getHibernateSessionFactory()
    {
        return sessionFactory;
    }
}

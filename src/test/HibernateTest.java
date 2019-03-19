package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.idgform.core.FactoryGenerator;
import com.idgform.models.User;

public class HibernateTest
{
    public static void main(String args[])
    {
        SessionFactory factory = FactoryGenerator.getHibernateSessionFactory();
        Session hbSession = factory.openSession();
        try
        {
            hbSession.beginTransaction();
            User user = new User();
            user.setName("admin");
            user.setPassword("root".toCharArray());
            user.setEmail("admin@idgform.com");
            hbSession.save(user);
            hbSession.getTransaction().commit();
        }
        catch (Exception e)
        {
            hbSession.getTransaction().rollback();
        }
        finally
        {
            if (hbSession.isOpen())
            {
                hbSession.close();
            }

        }
    }

}

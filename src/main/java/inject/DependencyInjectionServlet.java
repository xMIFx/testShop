package inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.applet.AppletContext;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Vlad on 09.02.2015.
 */
public class DependencyInjectionServlet extends HttpServlet {
    private static final String APP_CTX_PATH ="/appContext-dao-aop-schema-jdbc.xml" ;//"contextConfigLocation";

    @Override
    public final void init() throws ServletException {
        /*String appCtxPath = this.getServletContext().getInitParameter(APP_CTX_PATH);
        System.out.println("Load " + APP_CTX_PATH + " to --->" + appCtxPath);
        if (appCtxPath == null) {
            System.out.println("I need init param: " + APP_CTX_PATH);
            throw new ServletException(APP_CTX_PATH + " :init param == null");
        }*/
        try {
            ApplicationContext appCtx = ApplicationContextHolder.getClassPathXmlApplicationContext(APP_CTX_PATH);//new ClassPathXmlApplicationContext(APP_CTX_PATH);
            List<Field> allFields = FieldReflector.collectUpTo(this.getClass(), DependencyInjectionServlet.class);
            List<Field> injectFields = FieldReflector.filterInject(allFields);
            /*
                1. Iterate on all fields with annotation @Inject
                2. Get this annotation from field(there can be a lot of annotations)
                3. Read value from my annotation
                4. Get bean
                5. Set bean in my field
             */
            for (Field field : injectFields) {
                field.setAccessible(true);
                Inject annotation = field.getAnnotation(Inject.class);
               // System.out.println("I find method marked by @Inject: " + field);
                String beanName = annotation.value();
               // System.out.println("I must instantiate and inject '" + beanName + "'");
                Object bean = appCtx.getBean(beanName);
                //System.out.println("Instantiate is ok '" + beanName + "'");
                if (bean == null) {
                    throw new ServletException("There isn't bean with name " + beanName);
                }
                field.set(this,bean);
            }
        }
        catch (Exception e){throw new ServletException("Can't inject from "+APP_CTX_PATH, e);}

    }
}

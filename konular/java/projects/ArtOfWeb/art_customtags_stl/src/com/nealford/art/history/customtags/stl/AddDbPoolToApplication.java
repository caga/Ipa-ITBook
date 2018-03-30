package com.nealford.art.history.customtags.stl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.jsp.tagext.*;

public class AddDbPoolToApplication extends TagSupport {
    private String initUrlName;
    private String initDriverClassName;
    private String initUserName;
    private String initPasswordName;

    public AddDbPoolToApplication() {}

    public int doStartTag() throws javax.servlet.jsp.JspException {
        String driverClass = pageContext.getServletContext()
                           .getInitParameter(initDriverClassName);
        String dbUrl = pageContext.getServletContext()
                     .getInitParameter(initUrlName);
        String user = pageContext.getServletContext()
                    .getInitParameter(initUserName);
        String password = pageContext.getServletContext()
                        .getInitParameter(initPasswordName);
        DbPool dbPool = null;
        Connection connection = null;
        try {
            dbPool = new DbPool(driverClass, dbUrl, user, password);
            pageContext.getServletContext().setAttribute("DbPool",
                                                         dbPool);
        } catch (SQLException sqlx) {
            pageContext.getServletContext().log(
                    "Connection exception", sqlx);
        }

        return SKIP_BODY;
    }

    public void setInitUrlName(String initUrl) {
        this.initUrlName = initUrl;
    }

    public void setInitDriverClassName(String initDriverClass) {
        this.initDriverClassName = initDriverClass;
    }

    public void setInitUserName(String initUser) {
        this.initUserName = initUser;
    }

    public void setInitPasswordName(String initPassword) {
        this.initPasswordName = initPassword;
    }
}

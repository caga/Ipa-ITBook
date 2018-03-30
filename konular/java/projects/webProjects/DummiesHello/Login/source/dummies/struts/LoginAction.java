package dummies.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Mike Robinson
 *
 */
public class LoginAction extends Action 
{
	/**
	 * Handles user's request for login
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward execute( 	ActionMapping mapping,
									ActionForm form,
									HttpServletRequest request,
									HttpServletResponse response)
									throws Exception
	{
		// create a new LoginBean with valid users in it
		LoginBean lb = new LoginBean();
		
		// check to see if this user/password combination are valid
		if(lb.validateUser(((LoginForm)form).getUserName(),((LoginForm)form).getPassword()))
		{
			request.setAttribute("userName",((LoginForm)form).getUserName());
			return (mapping.findForward("success"));
		}
		else	// username/password not validated
		{
			// create ActionError and save in the request
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("error.login.invalid");
			errors.add("login",error);
			saveErrors(request,errors);
			
			return (mapping.findForward("failure"));
		}
	}
}

<%--
  -- WebWork, Web Application Framework
  --
  -- Distributable under LGPL license
  -- See terms of license at opensource.org
  --
  --
  -- checkbox.jsp
  --
  -- Required Parameters:
  --   * label       - The description that will be used to identfy the control
  --   * name        - The name of the attribute to put and pull the result from
  --                   Equates to the NAME parameter of the HTML INPUT tag
  --   * fieldValue  - The value displayed by the control.  Equates to the value
  --                   of the HTML INPUT tag
  --
  -- Optional Parameters:
  --   * disabled        - DISABLED parameter of the HTML INPUT tag
  --   * tabindex        - tabindex parameter of the HTML INPUT tag
  --   * onchange        - onkeyup parameter of the HTML INPUT tag
  --%>
 <%@ taglib uri="webwork" prefix="webwork" %>

 <webwork:property value="errors[parameters['name']]" id="errmsg">
   <webwork:if test=".">
      <tr>
        <td align="left" valign="top" colspan="2">
            <span class="errorMessage"><webwork:property value="."/></span>
        </td>
      </tr>
   </webwork:if>
</webwork:property>

<tr>
    <td  valign="top" colspan="2">
        <%-- Use an extra table so that the checkbox doesn't align with the other columns. --%>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr><td valign="top">
                <input type="checkbox" <webwork:if test="parameters['nameValue'] == true">checked="checked"</webwork:if>
                       name="<webwork:property value="parameters['name']"/>"
                       value="<webwork:property value="parameters['fieldValue']"/>"
                     <webwork:property value="parameters['disabled']">
                        <webwork:if test="parameters['disabled'] == true">disabled="disabled"</webwork:if>
                     </webwork:property>
                     <webwork:property value="parameters['tabindex']">
                        <webwork:if test=".">tabindex="<webwork:property value="."/>"</webwork:if>
                     </webwork:property>
                     <webwork:property value="parameters['onchange']">
                        <webwork:if test=".">onchange="<webwork:property value="."/>"</webwork:if>
                     </webwork:property>
                     <webwork:property value="parameters['id']">
                        <webwork:if test=".">id="<webwork:property value="."/>"</webwork:if>
                     </webwork:property>
                />
            </td><td width="100%" valign="top">
                <webwork:if test="@errmsg">
                    <span class="checkboxErrorLabel">
                </webwork:if>
                <webwork:else>
                   <span class="checkboxLabel">
                </webwork:else>
                <webwork:property value="parameters['label']"/>
                </span>
              </td></tr>
        </table>
<%@ include file="controlfooter.jsp" %>
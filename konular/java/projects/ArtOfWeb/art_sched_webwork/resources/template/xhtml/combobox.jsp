<%--
  -- WebWork, Web Application Framework
  --
  -- Distributable under LGPL license.
  -- See terms of license at opensource.org
  --
  --
  -- combobox.jsp
  --
  -- Required Parameters:
  --   * label  - The description that will be used to identfy the control.
  --   * name   - The name of the attribute to put and pull the result from.
  --              Equates to the NAME parameter of the HTML SELECT tag.
  --   * list   - Iterator that will provide the options for the control.
  --              Equates to the HTML OPTION tags in the SELECT and supplies
  --              both the NAME and VALUE parameters of the OPTION tag.
  --
  -- Optional Parameters:
  --   * labelposition   - determines were the label will be place in relation
  --                       to the control.  Default is to the left of the control.
  --   * size       - SIZE parameter of the HTML INPUT tag.
  --   * maxlength  - MAXLENGTH parameter of the HTML INPUT tag.
  --   * disabled   - DISABLED parameter of the HTML INPUT tag.
  --   * onkeyup    - onkeyup parameter of the HTML INPUT tag.
  --   * tabindex  - tabindex parameter of the HTML INPUT tag.
  --   * onchange  - onkeyup parameter of the HTML INPUT tag.
  --
  --%>
<%@ taglib uri="webwork" prefix="webwork" %>
<%@ include file="controlheader.jsp" %>

<input type="text"
       name="<webwork:property value="parameters['name']"/>"
         <webwork:property value="parameters['size']">
            <webwork:if test=".">size="<webwork:property value="."/>"</webwork:if>
         </webwork:property>
         <webwork:property value="parameters['maxlength']">
            <webwork:if test=".">maxlength="<webwork:property value="."/>"</webwork:if>
         </webwork:property>
         <webwork:property value="parameters['nameValue']">
            <webwork:if test=".">value="<webwork:property value="."/>"</webwork:if>
         </webwork:property>
         <webwork:property value="parameters['disabled']">
            <webwork:if test="parameters['disabled'] == true">disabled="disabled"</webwork:if>
         </webwork:property>
         <webwork:property value="parameters['onkeyup']">
            <webwork:if test=".">onkeyup="<webwork:property value="."/>"</webwork:if>
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
/><br/>

<webwork:property value="parameters['list']">
   <webwork:if test=".">
      <select onChange="this.form.elements['<webwork:property value="parameters['name']"/>'].value=this.options[this.selectedIndex].value"
         <webwork:property value="parameters['disabled']">
            <webwork:if test="{parameters['disabled']}">disabled="disabled"</webwork:if>
         </webwork:property>
      >
            <webwork:iterator value=".">
               <option value="<webwork:property value="."/>" <webwork:if test="parameters['nameValue'] == .">selected="selected"</webwork:if>><webwork:property value="."/>
               </option>
            </webwork:iterator>
      </select>
   </webwork:if>
</webwork:property>

<%@ include file="controlfooter.jsp" %>
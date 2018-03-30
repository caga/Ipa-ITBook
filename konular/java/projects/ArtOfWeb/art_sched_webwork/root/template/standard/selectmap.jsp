<%--
  -- WebWork, Web Application Framework
  --
  -- Distributable under LGPL license.
  -- See terms of license at opensource.org
  --
  --
  -- selectmap.jsp
  --
  -- Required Parameters:
  --   * label     - The description that will be used to identfy the control.
  --   * name      - The name of the attribute to put and pull the result from.
  --                 Equates to the NAME parameter of the HTML tag SELECT.
  --   * list      - Iterator that will provide the options for the control.
  --                 Equates to the HTML OPTION tags in the SELECT.
  --   * listKey   - Where to get the values for the OPTION tag.  Equates to
  --                 the VALUE parameter of the OPTION tag.
  --   * listValue - The value displayed by the control.  Equates to the body
  --                 of the HTML OPTION tag.
  --
  -- Optional Parameters:
  --   * labelposition   - determines were the label will be place in relation
  --                       to the control.  Default is to the left of the control.
  --   * disabled        - DISABLED parameter of the HTML SELECT tag.
  --   * tabindex        - tabindex parameter of the HTML SELECT tag.
  --   * onchange        - onkeyup parameter of the HTML SELECT tag.
  --   * size            - SIZE parameter of the HTML SELECT tag.
  --   * multiple        - MULTIPLE parameter of the HTML SELECT tag.  
  --
  --
  --%>
<%@ taglib uri="webwork" prefix="webwork" %>
<%@ include file="controlheader.jsp" %>

<select name="<webwork:property value="parameters['name']"/>"
      <webwork:property value="parameters['disabled']">
         <webwork:if test="parameters['disabled'] == true">DISABLED</webwork:if>
      </webwork:property>
      <webwork:property value="parameters['tabindex']">
         <webwork:if test=".">tabindex="<webwork:property value="."/>"</webwork:if>
      </webwork:property>
      <webwork:property value="parameters['onchange']">
         <webwork:if test=".">onchange="<webwork:property value="."/>"</webwork:if>
      </webwork:property>
      <webwork:property value="parameters['size']">
         <webwork:if test=".">size="<webwork:property value="."/>"</webwork:if>
      </webwork:property>
      <webwork:property value="parameters['multiple']">
         <webwork:if test="parameters['multiple'] == true">multiple</webwork:if>
      </webwork:property>      
>
   <webwork:iterator value="parameters['list']">
      <option value="<webwork:property value="{parameters['listKey']}"/>"
         <webwork:if test="{memberOf(parameters['nameValue'], {parameters['listKey']})} == true">SELECTED</webwork:if>
      >
         <webwork:property value="{parameters['listValue']}"/>
      </option>
   </webwork:iterator>
</select>

<%@ include file="controlfooter.jsp" %>
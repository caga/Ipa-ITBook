<%--
  -- WebWork, Web Application Framework
  --
  -- Distributable under LGPL license.
  -- See terms of license at opensource.org
  --
  -- selectcascading.jsp
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
  --   * labelposition - determines were the label will be place in relation
  --                     to the control.  Default is to the left of the control.
  --   * receiver      - The name of the receiving SELECT tag.
  --   * onChange      - The value of the Javascript that would be triggered if this SELECT is changed.
  --   * receiverMap   - Iterator that will provide the options for the control.
  --                 	 Equates to the HTML OPTION tags in the SELECT.
  --   * disabled      - DISABLED parameter of the HTML SELECT tag.
  --   * tabindex      - tabindex parameter of the HTML SELECT tag.
  --   * onchange      - IGNORED, it has its own onchange defined
  --   * size          - SIZE parameter of the HTML SELECT tag.
  --
  --%>
<%@ taglib uri="webwork" prefix="webwork" %>
<%@ include file="controlheader.jsp" %>

<select name="<webwork:property value="parameters['name']"/>" onChange="callerHasChanged( this.name, '<webwork:property value="parameters['receiver']"/>' );"
      <webwork:property value="parameters['disabled']">
         <webwork:if test="parameters['disabled'] == true">DISABLED</webwork:if>
      </webwork:property>
      <webwork:property value="parameters['tabindex']">
         <webwork:if test=".">tabindex="<webwork:property value="."/>"</webwork:if>
      </webwork:property>
      <webwork:property value="parameters['size']">
         <webwork:if test=".">size="<webwork:property value="."/>"</webwork:if>
      </webwork:property>
>
	<webwork:if test="parameters['initialValue']">
	  <option value="<webwork:property value="parameters['initialValue']"/>" SELECTED>
		 <webwork:property value="parameters['initialValue']"/>
	  </option>
   </webwork:if>
   <webwork:iterator value="parameters['list']">
	  <option value="<webwork:property value="{parameters['listKey']}"/>"
		<webwork:if test="{parameters['listKey']} == parameters['nameValue']">SELECTED</webwork:if>>
		<webwork:property value="{parameters['listValue']}"/>
	  </option>
   </webwork:iterator>
</select>

<script>
	addCallerReceiverPair( "<webwork:property value="parameters['name']"/>", "<webwork:property value="parameters['receiver']"/>" );
	tmpDataMap = new Map();

<webwork:if test="parameters['receiverMap']">
	<webwork:iterator value="parameters['receiverMap']">
		tmpMap = new Map();
		<webwork:iterator value="value">
			tmpMap.put( "<webwork:property value="key"/>", "<webwork:property value="value"/>" );
		</webwork:iterator>
		tmpDataMap.put( "<webwork:property value="{parameters['listKey']}"/>", tmpMap );
	</webwork:iterator>
</webwork:if>
	setDataMapForPair( "<webwork:property value="parameters['name']"/>", "<webwork:property value="parameters['receiver']"/>", tmpDataMap );
</script>

<%@ include file="controlfooter.jsp" %>
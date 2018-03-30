<%@ taglib uri="webwork" prefix="webwork" %>

<div class="control">
<%-- Only show message if errors are available.
  -- This will be done if ActionFormSupport is used.
  --%>
<webwork:property value="errors[parameters['name']]">
   <webwork:if test=".">
      <div class="errorMessage"><webwork:property value="."/></div>
   </webwork:if>
</webwork:property>

<%-- if the label position is top,
  -- then give the label it's own row in the table
  --%>

<webwork:if test="errors[parameters['name']]">
   <div class="errorLabel">
</webwork:if>
<webwork:else>
   <div class="label">
</webwork:else>
<webwork:property value="parameters['label']"/>:
</div>

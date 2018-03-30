<%-- Only show message if errors are available.
  -- This will be done if ActionFormSupport is used.
  --%>
<webwork:property value="errors[parameters['name']]">
   <webwork:if test=".">
      <tr>
        <webwork:if test="parameters['labelposition'] == 'top'">
            <td align="left" valign="top" colspan="2">
        </webwork:if>
        <webwork:else>
            <td align="center" valign="top" colspan="2">
        </webwork:else>
            <span class="errorMessage"><webwork:property value="."/></span>
        </td>
      </tr>
   </webwork:if>
</webwork:property>

<%-- if the label position is top,
  -- then give the label it's own row in the table
  --%>

<tr>
        <webwork:if test="parameters['labelposition'] == 'top'">
            <td align="left" valign="top" colspan="2">
        </webwork:if>
        <webwork:else>
            <td align="right" valign="top">
        </webwork:else>

        <webwork:if test="errors[parameters['name']]">
           <span class="errorLabel">
        </webwork:if>
        <webwork:else>
           <span class="label">
        </webwork:else>

        <webwork:property value="parameters['label']"/>:

        </span>

    </td>

<%-- add the extra row  --%>
<webwork:if test="parameters['labelposition'] == 'top'">
   </tr><tr>
</webwork:if>

<td>

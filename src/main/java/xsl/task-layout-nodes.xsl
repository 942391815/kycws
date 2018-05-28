<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- =========================== -->
  <!-- Process the Action Node -->
  <!-- =========================== -->
  <xsl:template name="processActionNodeByLayout">
    <xsl:param name="actionNode"/>
    <xsl:variable name="taskActionId">
      <xsl:value-of select="$taskId"/>_action_<xsl:value-of select="$actionNode/@name"/>
    </xsl:variable>
    <xsl:element name="script">
      
      <xsl:attribute name="language">Javascript</xsl:attribute>
       <xsl:choose>
       <xsl:when test="count($actionNode/params) > 0">
         var rules = [];    
         <xsl:for-each select="$actionNode/params/param">
           rules.push('<xsl:value-of select="."/>');
         </xsl:for-each>
             adf.Task.addValidationRules('<xsl:value-of select="$taskActionId"/>',rules);
             adfdojo.addOnLoad(function(){
               var pass = adf.Task.validateTask('<xsl:value-of select="$taskActionId"/>');
               var action = adfdojo.byId('button_<xsl:value-of select="$taskActionId"/>')
               if (!pass &amp;&amp; action &amp;&amp; action.type=="image") {
                 adf.Utils.setImageSrc(action, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$actionNode/action-properties/@disabled-image"/>');
               }                
               adfdojo.query('[id *= \'widget_'+'<xsl:value-of select="$taskId"/>'+'\']').forEach(
                  function (dom) {
                    var dNode = adfdijit.byNode(dom);
                    if (adf.Task._connections) {
                      adf.Task._connections.push(adfdojo.connect(dNode, "onBlur", function() {
                        var pass = adf.Task.validateTask('<xsl:value-of select="$taskActionId"/>');
                        var action = adfdojo.byId('button_<xsl:value-of select="$taskActionId"/>');
                        if (!pass &amp;&amp; action &amp;&amp; action.type=="image") {
                          adf.Utils.setImageSrc(action, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$actionNode/action-properties/@disabled-image"/>');
                          adfdojo.attr(action,"cursor","default");
                        } 
                        if (pass &amp;&amp; action &amp;&amp; action.type=="image") {
                          adf.Utils.setImageSrc(action, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$actionNode/action-properties/@default-image"/>');
                        }
                      }));
                    }
                  });
               });            
       </xsl:when>
       </xsl:choose>
    </xsl:element>    
    <xsl:element name="input">
      <xsl:attribute name="name">button_<xsl:value-of select="$taskActionId" /></xsl:attribute>
      <xsl:attribute name="id">button_<xsl:value-of select="$taskActionId" /></xsl:attribute>
      <xsl:choose>
        <xsl:when test="$actionNode/action-properties/@renderer-type = 'IMAGEONLY'">
          <xsl:attribute name="type">IMAGE</xsl:attribute>
          <xsl:choose>
            <xsl:when test="$actionNode/action-properties/@disabled = 'true'">
              <xsl:attribute name="src"><xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@disabled-image"/></xsl:attribute>
              <xsl:attribute name="onclick">return false;</xsl:attribute>
              <xsl:attribute name="style">cursor:default;</xsl:attribute>
              <xsl:attribute name="disabled"><xsl:value-of select="$actionNode/action-properties/@disabled" /></xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
              <xsl:attribute name="src"><xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@default-image"/></xsl:attribute>
              <xsl:attribute name="onmouseover">adf.Utils.getEventSource(event).src = '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@hover-image"/>';</xsl:attribute>
              <xsl:attribute name="onmousedown">adf.Utils.getEventSource(event).src = '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@selected-image"/>';</xsl:attribute>
              <xsl:attribute name="onclick">adf.Controls.maps['<xsl:value-of select="//map-id"/>'].createCurrentToolItem('<xsl:value-of select="$taskActionId"/>', 'button_<xsl:value-of select="$taskActionId" />', 'adf.MapServerAction', <xsl:value-of select="$actionNode/action-properties/@show-loading-image"/>, <xsl:value-of select="//client-post-back"/>, null, null, '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@default-image"/>', '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@hover-image"/>', '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@selected-image"/>', '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@disabled-image"/>'); return false;</xsl:attribute>
              <xsl:attribute name="onmouseout">adf.Utils.getEventSource(event).src = '<xsl:value-of select="$contextpath" /><xsl:value-of select="$actionNode/action-properties/@default-image"/>';</xsl:attribute>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
         <xsl:attribute name="adfdojoType">adfdijit.form.Button</xsl:attribute>
         <xsl:attribute name="type">BUTTON</xsl:attribute>
          <xsl:attribute name="label"><xsl:value-of select="$actionNode/display-name"/></xsl:attribute>
          <xsl:choose>
            <xsl:when test="$actionNode/action-properties/@disabled = 'true'">
              <xsl:attribute name="className">esriToolDisabled</xsl:attribute>
              <xsl:attribute name="onClick">return false;</xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
               <xsl:attribute name="className">dijitReset dijitStretch dijitButtonContents</xsl:attribute>
              <xsl:attribute name="onClick">adf.Controls.maps['<xsl:value-of select="//map-id"/>'].createCurrentToolItem('<xsl:value-of select="$taskActionId"/>', 'button_<xsl:value-of select="$taskActionId" />', 'adf.MapServerAction', <xsl:value-of select="$actionNode/action-properties/@show-loading-image"/>, <xsl:value-of select="//client-post-back"/>, null, null, 'dijitReset dijitStretch dijitButtonContents', 'dijitReset dijitStretch dijitButtonContents esriToolHover', 'dijitReset dijitStretch dijitButtonContents esriToolSelected', 'dijitReset dijitStretch dijitButtonContents esriToolDisabled'); return false;</xsl:attribute>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:attribute name="title"><xsl:value-of select="$actionNode/action-properties/@tool-tip"/></xsl:attribute>
    </xsl:element>
  </xsl:template>

  <!-- =========================== -->
  <!-- Process the Tool Node -->
  <!-- =========================== -->
  <xsl:template name="processToolNodeByLayout">
    <xsl:param name="toolNode"/>
    <xsl:variable name="taskToolId">
      <xsl:value-of select="$taskId"/>_tool_<xsl:value-of select="$toolNode/@name"/>
    </xsl:variable>
    <xsl:if test="$toolNode/tool-properties/@justBeenDisabled = 'true'">
	    <xsl:element name="script">
	      <xsl:attribute name="type">text/javascript</xsl:attribute>
	      <xsl:attribute name="language">JavaScript</xsl:attribute>
			if(adf.Controls.maps['<xsl:value-of select="//map-id"/>'].currentTool &amp;&amp; adf.Controls.maps['<xsl:value-of select="//map-id"/>'].currentTool.id == "<xsl:value-of select="$taskToolId"/>"){adf.Controls.maps['<xsl:value-of select="//map-id"/>'].clearCurrentToolItem();}
	     </xsl:element>
   	</xsl:if>
    <xsl:element name="script">
      <xsl:attribute name="language">Javascript</xsl:attribute>
          <xsl:choose>
       <xsl:when test="count($toolNode/params) > 0">
          var rules = [];    
         <xsl:for-each select="$toolNode/params/param">
           rules.push('<xsl:value-of select="."/>');
         </xsl:for-each>
            adf.Task.addValidationRules('<xsl:value-of select="$taskToolId"/>',rules);
            adfdojo.addOnLoad(function(){
              var pass = adf.Task.validateTask('<xsl:value-of select="$taskToolId"/>');
              var tool = adfdojo.byId('button_<xsl:value-of select="$taskToolId"/>')
              if (!pass &amp;&amp; tool &amp;&amp; tool.type=="image") {
                adf.Utils.setImageSrc(tool, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$toolNode/tool-properties/@disabled-image"/>');
                adfdojo.attr(tool,"cursor","default");                
              }              
              adfdojo.query('[id *= \'widget_'+'<xsl:value-of select="$taskId"/>'+'\']').forEach(
                function (dom) {
                  var dNode = adfdijit.byNode(dom);
                  if (adf.Task._connections) {
                    adf.Task._connections.push(adfdojo.connect(dNode, "onBlur", function() {
                      var pass = adf.Task.validateTask('<xsl:value-of select="$taskToolId"/>');
                      var tool = adfdojo.byId('button_<xsl:value-of select="$taskToolId"/>');
                      if (!pass &amp;&amp; tool &amp;&amp; tool.type=="image") {
                        adf.Utils.setImageSrc(tool, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$toolNode/tool-properties/@disabled-image"/>');
                      }
                      if (pass &amp;&amp; tool &amp;&amp; tool.type=="image") {
                        adf.Utils.setImageSrc(tool, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$toolNode/tool-properties/@default-image"/>');
                      }
                      if (!pass &amp;&amp; adf.Controls.maps['<xsl:value-of select="//map-id"/>'].currentTool   &amp;&amp; (adf.Controls.maps['<xsl:value-of select="//map-id"/>'].currentTool.id == '<xsl:value-of select="$taskToolId"/>')) {
                        adf.Controls.maps['<xsl:value-of select="//map-id"/>'].deactivateCurrentToolItem();
                        if (tool.type=="image") adf.Utils.setImageSrc(tool, '<xsl:value-of select="$contextpath" />'+'<xsl:value-of select="$toolNode/tool-properties/@disabled-image"/>');
                      }
                    }));
                  }
                });
            });   
       </xsl:when>
       </xsl:choose>
      </xsl:element>    
    <xsl:element name="input">
      <xsl:attribute name="name">button_<xsl:value-of select="$taskToolId"/></xsl:attribute>
      <xsl:attribute name="id">button_<xsl:value-of select="$taskToolId"/></xsl:attribute>
      <xsl:choose>
        <xsl:when test="$toolNode/tool-properties/@renderer-type = 'IMAGEONLY'">
          <xsl:attribute name="type">IMAGE</xsl:attribute>
          <xsl:choose>
            <xsl:when test="$toolNode/tool-properties/@disabled = 'true'">
              <xsl:attribute name="src"><xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@disabled-image"/></xsl:attribute>
              <xsl:attribute name="style">cursor:default;</xsl:attribute>
              <xsl:attribute name="onclick">return false;</xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
                <xsl:when test="contains(//active-tool, $taskToolId) = 'true'">
                  <xsl:attribute name="src"><xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@selected-image"/></xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:attribute name="src"><xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@default-image"/></xsl:attribute>
                </xsl:otherwise>
              </xsl:choose>
              <xsl:attribute name="onmouseover">adf.Utils.getEventSource(event).src = '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@hover-image"/>';</xsl:attribute>
              <xsl:attribute name="onmousedown">adf.Utils.getEventSource(event).src = '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@selected-image"/>';</xsl:attribute>
              <xsl:attribute name="onclick">adf.Controls.maps['<xsl:value-of select="//map-id"/>'].createCurrentToolItem('<xsl:value-of select="$taskToolId"/>', 'button_<xsl:value-of select="$taskToolId"/>', '<xsl:value-of select="$toolNode/tool-properties/@client-action"/>', <xsl:value-of select="$toolNode/tool-properties/@show-loading-image"/>, <xsl:value-of select="//client-post-back"/>, '<xsl:value-of select="$toolNode/tool-properties/@line-color"/>', <xsl:value-of select="$toolNode/tool-properties/@line-width"/>, '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@default-image"/>', '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@hover-image"/>', '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@selected-image"/>', '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@disabled-image"/>'); return false;</xsl:attribute>
              <xsl:attribute name="onmouseout">adf.Utils.getEventSource(event).src = '<xsl:value-of select="$contextpath" /><xsl:value-of select="$toolNode/tool-properties/@default-image"/>';</xsl:attribute>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:when>
        <xsl:otherwise>
          <xsl:attribute name="adfdojoType">adfdijit.form.Button</xsl:attribute>
          <xsl:attribute name="type">BUTTON</xsl:attribute>
          <xsl:attribute name="value"><xsl:value-of select="$toolNode/display-name"/></xsl:attribute>
          <xsl:attribute name="label"><xsl:value-of select="$toolNode/display-name"/></xsl:attribute>
          <xsl:choose>
             <xsl:when test="$toolNode/tool-properties/@disabled = 'true'">
              <xsl:attribute name="className">esriToolDisabled</xsl:attribute>
              <xsl:attribute name="onClick">return false;</xsl:attribute>
            </xsl:when>
            <xsl:otherwise>
              <xsl:choose>
                <xsl:when test="contains(//active-tool, $taskToolId) = 'true'">
                    <xsl:attribute name="className">dijitReset dijitStretch dijitButtonContents esriToolSelected</xsl:attribute>
                </xsl:when>
                <xsl:otherwise>
                   <xsl:attribute name="className">dijitReset dijitStretch dijitButtonContents</xsl:attribute>
                </xsl:otherwise>
              </xsl:choose>
              <xsl:attribute name="onClick">adf.Controls.maps['<xsl:value-of select="//map-id"/>'].createCurrentToolItem('<xsl:value-of select="$taskToolId"/>', 'button_<xsl:value-of select="$taskToolId"/>', '<xsl:value-of select="$toolNode/tool-properties/@client-action"/>', <xsl:value-of select="$toolNode/tool-properties/@show-loading-image"/>, <xsl:value-of select="//client-post-back"/>, '<xsl:value-of select="$toolNode/tool-properties/@line-color"/>', <xsl:value-of select="$toolNode/tool-properties/@line-width"/>, 'dijitReset dijitStretch dijitButtonContents', 'dijitReset dijitStretch dijitButtonContents esriToolHover', 'dijitReset dijitStretch dijitButtonContents esriToolSelected', 'dijitReset dijitStretch dijitButtonContents esriToolDisabled'); return false;</xsl:attribute>
            </xsl:otherwise>
          </xsl:choose>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:attribute name="title"><xsl:value-of select="$toolNode/tool-properties/@tool-tip"/></xsl:attribute>
    </xsl:element>
  </xsl:template>

  <!-- =========================== -->
  <!-- Process the Param Node -->
  <!-- =========================== -->
  <xsl:template name="processParamNodeByLayout">
    <xsl:param name="paramNode"/>
    <xsl:param name="layout"/>
    <xsl:variable name="val" select="value"/>
    <xsl:choose>
      <xsl:when test="$paramNode/read-only = 'true'">
        <xsl:value-of select="$val"/>
      </xsl:when>
      <xsl:when test="$paramNode/@type = 'TEXT'">
        <xsl:if test="$layout = 'tabularLayout' or $layout = 'absoluteLayout'">
          <xsl:element name="span">
            <xsl:attribute name="style">margin-right:10px;</xsl:attribute>
            <xsl:value-of select="$paramNode/display-name"/>
          </xsl:element>
        </xsl:if>
        <xsl:choose>
          <xsl:when test="count($paramNode/adfdijit-type) > 0 and count($paramNode/adfdijit-type/@time) > 0">
            <xsl:element name="input">
              <xsl:attribute name="type">TEXT</xsl:attribute>
              <xsl:attribute name="adfdojoType">adfdijit.form.DateTextBox</xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_date</xsl:attribute>
              <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_date</xsl:attribute>
              <xsl:attribute name="intermediateChanges">true</xsl:attribute>
              <xsl:if test="$paramNode/@disabled = 'true'">
                <xsl:attribute name="disabled">true</xsl:attribute>
              </xsl:if>
                <xsl:for-each select="*">
                  <xsl:for-each select="@*">
                    <xsl:variable name="atr" select="name()"/>
                    <xsl:if test="$atr != 'adfdojoType'">
                      <xsl:attribute name="{$atr}"> <xsl:value-of select="."/></xsl:attribute>
                    </xsl:if>
                  </xsl:for-each>
                </xsl:for-each>
              <xsl:if test="$val !=''">
                <xsl:attribute name="value">
                  <xsl:value-of select="$val"/>
                </xsl:attribute>
              </xsl:if>
            </xsl:element>
            <xsl:element name="input">
              <xsl:attribute name="type">TEXT</xsl:attribute>
              <xsl:attribute name="adfdojoType">adfdijit.form.TimeTextBox</xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_time</xsl:attribute>
              <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_time</xsl:attribute>
              <xsl:attribute name="intermediateChanges">true</xsl:attribute>
              <xsl:if test="$paramNode/@disabled = 'true'">
                <xsl:attribute name="disabled">true</xsl:attribute>
              </xsl:if>
              <xsl:for-each select="*">
                <xsl:for-each select="@*">
                  <xsl:variable name="atr" select="name()"/>
                  <xsl:if test="$atr != 'adfdojoType'">
                    <xsl:attribute name="{$atr}"> <xsl:value-of select="."/></xsl:attribute>
                  </xsl:if>
                </xsl:for-each>
              </xsl:for-each>
              <xsl:if test="$val !=''">
                <xsl:attribute name="value">
                  <xsl:value-of select="$val"/>
                </xsl:attribute>
              </xsl:if>
            </xsl:element>
            <xsl:element name="input">
              <xsl:attribute name="type">HIDDEN</xsl:attribute>
              <xsl:attribute name="adfdojoType">adfdijit.form.TextBox</xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
              <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
              <xsl:if test="$val !=''">
                <xsl:attribute name="value">
                  <xsl:value-of select="$val"/>
                </xsl:attribute>
              </xsl:if>
            </xsl:element>
             <xsl:element name="script">
              <xsl:attribute name="language">Javascript</xsl:attribute>
               adfdojo.addOnLoad(function() { 
                 adf.Task._dateAndTimeInputSubmissionUtility('<xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_date','<xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_time','<xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>');
               });
              </xsl:element>
          </xsl:when>
          <xsl:otherwise>
            <xsl:element name="input">
              <xsl:attribute name="type">TEXT</xsl:attribute>
              <xsl:choose>
               <xsl:when test="count($paramNode/adfdijit-type) > 0">
                <xsl:for-each select="*">
                  <xsl:for-each select="@*">
                    <xsl:variable name="atr" select="name()"/>
                    <xsl:attribute name="{$atr}"> <xsl:value-of select="."/></xsl:attribute>
                  </xsl:for-each>
                </xsl:for-each>
               </xsl:when>
               <xsl:otherwise>
                 <xsl:attribute name="adfdojoType">adfdijit.form.TextBox</xsl:attribute>
              </xsl:otherwise>
             </xsl:choose>
              <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
              <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
              <xsl:if test="$val !=''">
                <xsl:attribute name="value">
                  <xsl:value-of select="$val"/>
                </xsl:attribute>
              </xsl:if>
              <xsl:if test="$paramNode/@disabled = 'true'">
                <xsl:attribute name="disabled">true</xsl:attribute>
              </xsl:if>             
           </xsl:element>
         </xsl:otherwise>
       </xsl:choose>
      </xsl:when>
      <xsl:when test="$paramNode/@type = 'CHECKBOX'">
        <xsl:if test="$layout = 'tabularLayout' or $layout = 'absoluteLayout'">
          <xsl:element name="span">
            <xsl:attribute name="style">margin-right:10px;</xsl:attribute>
            <xsl:value-of select="$paramNode/display-name"/>
          </xsl:element>
        </xsl:if>
        <xsl:element name="input">
          <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
          <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
          <xsl:if test="$paramNode/@disabled = 'true'">
            <xsl:attribute name="disabled">true</xsl:attribute>
          </xsl:if>
          <xsl:attribute name="type">CHECKBOX</xsl:attribute>
          <xsl:attribute name="adfdojoType">adfdijit.form.CheckBox</xsl:attribute>
          <xsl:choose>
            <xsl:when test="$val = 'true'">
              <xsl:attribute name="checked">checked</xsl:attribute>
            </xsl:when>
          </xsl:choose>
        </xsl:element>
      </xsl:when>
      <xsl:when test="$paramNode/@type = 'RADIO'">
        <xsl:if test="$layout = 'tabularLayout' or $layout = 'absoluteLayout'">
          <xsl:element name="span">
            <xsl:attribute name="style">margin-right:10px;</xsl:attribute>
            <xsl:value-of select="$paramNode/display-name"/>
          </xsl:element>
        </xsl:if>
        <xsl:for-each select="select/option">
          <xsl:element name="input">
            <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
            <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/>_<xsl:value-of select="generate-id(.)"/></xsl:attribute>
            <xsl:attribute name="type">RADIO</xsl:attribute>
            <xsl:attribute name="adfdojoType">adfdijit.form.RadioButton</xsl:attribute>
            <xsl:if test="$paramNode/@disabled = 'true'">
              <xsl:attribute name="disabled">true</xsl:attribute>
            </xsl:if>
            <xsl:attribute name="value">
              <xsl:value-of select="./@value"/>
            </xsl:attribute>
            <xsl:if test="$val = ./@value">
              <xsl:attribute name="checked">checked</xsl:attribute>
            </xsl:if>
            <xsl:value-of select="./@name"/>
          </xsl:element>
          <br/>
        </xsl:for-each>
      </xsl:when>
      <xsl:when test="$paramNode/@type = 'SELECT'">
        <xsl:if test="$layout = 'tabularLayout' or $layout = 'absoluteLayout'">
          <xsl:element name="span">
            <xsl:attribute name="style">margin-right:10px;</xsl:attribute>
            <xsl:value-of select="$paramNode/display-name"/>
          </xsl:element>
        </xsl:if>
        <xsl:element name="select">
         <xsl:choose>
          <xsl:when test="$paramNode/@size &gt; 1">
            <xsl:attribute name="size"><xsl:value-of select="$paramNode/@size"/></xsl:attribute>
	        <xsl:attribute name="adfdojoType">adfdijit.form.MultiSelect</xsl:attribute>
	        <xsl:attribute name="multiple">true</xsl:attribute>
         </xsl:when>
         <xsl:otherwise>
           <xsl:attribute name="adfdojoType">adfdijit.form.FilteringSelect</xsl:attribute>
         </xsl:otherwise>
         </xsl:choose>
         <xsl:attribute name="autoComplete">true</xsl:attribute>
          <xsl:attribute name="name"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
          <xsl:attribute name="id"><xsl:value-of select="$taskId"/>_param_<xsl:value-of select="$paramNode/@name"/></xsl:attribute>
          <xsl:if test="$paramNode/@disabled = 'true'">
            <xsl:attribute name="disabled">true</xsl:attribute>
          </xsl:if>
          <xsl:for-each select="$paramNode/select/option">
            <xsl:element name="option">
              <xsl:attribute name="value">
                <xsl:value-of select="./@value"/>
              </xsl:attribute>
              <xsl:if test="contains($val, ./@value)">
                <xsl:attribute name="selected">true</xsl:attribute>
              </xsl:if>
              <xsl:value-of select="./@name"/>
            </xsl:element>
          </xsl:for-each>
        </xsl:element>
      </xsl:when>
    </xsl:choose>
  </xsl:template>
</xsl:stylesheet>

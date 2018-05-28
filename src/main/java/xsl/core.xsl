<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Copyright (c) 2000-2002, Environmental Systems Research Institute, Inc. All rights reserved. -->
  <!-- XSL file defining elements that all other XSL files have common.  -->
  <xsl:variable name="contextpath"><xsl:value-of select="//context-path" />/</xsl:variable>
  <xsl:variable name="userAgent"><xsl:value-of select="//user-agent" /></xsl:variable>
  <xsl:variable name="errorPage"><xsl:value-of select="//error-page" /></xsl:variable>
  <xsl:variable name="redirectPage"><xsl:value-of select="//redirect-page" /></xsl:variable>
  <xsl:template match="/">
    <xsl:element name="input">
      <xsl:attribute name="type">hidden</xsl:attribute>
      <xsl:attribute name="name">__ADFPostBack__</xsl:attribute>
      <xsl:attribute name="value">true</xsl:attribute>
    </xsl:element>
    <xsl:element name="script">
      <xsl:attribute name="type">text/javascript</xsl:attribute>
      <xsl:attribute name="language">Javascript</xsl:attribute>
        var esriInitItems = new Array();
        var userOnload = window.onload;
        //window.onload = esriInitApp;
        adf.Controls.contextPath = "<xsl:value-of select="$contextpath" />";
        adf.Controls.errorPage = "<xsl:value-of select="$errorPage" />";
        adf.Controls.redirectPage = "<xsl:value-of select="$redirectPage" />";
        function esriInitApp(e) {
          esriLoadApp();
          if (userOnload) {
            userOnload(e);
          }

          adfdojo.publish("/adf/app/init", [{ controls: adf.Controls }]);
        }

        function esriLoadApp() {
          var mapInitializers = new Array();
          for (var i=0;i&lt;esriInitItems.length;i++) {
            if (esriInitItems[i].toString().indexOf("esriMapInit") == 0) {
              eval(esriInitItems[i]);
              mapInitializers.push(i);
            }
          }

          for (var i=0;i&lt;esriInitItems.length;i++) {
            if (mapInitializers.indexOf(i) == -1) {
              eval(esriInitItems[i]);
            }
          }
          esriInitItems = new Array();
        }
        
        adfdojo.addOnLoad(esriInitApp);
    </xsl:element>

    <!-- Including the ADF Version number -->
    <xsl:element name="div"><xsl:attribute name="style">display:none</xsl:attribute><xsl:value-of select="//version"/></xsl:element>
  </xsl:template>
</xsl:stylesheet>

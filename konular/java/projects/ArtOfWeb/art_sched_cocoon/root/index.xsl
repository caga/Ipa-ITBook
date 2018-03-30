<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes"/>
  <xsl:output encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>

  <xsl:template match="StartPage">
    <xsl:processing-instruction name="cocoon-format">type="text/html"</xsl:processing-instruction>
    <HTML>
      <HEAD>
        <META HTTP-EQUIV="Content-Type" CONTENT="text/html; UTF-8"/>
        <TITLE><xsl:value-of select="Cocoon/MainTitle"/></TITLE>
      </HEAD>
      <BODY>
        <CENTER>
          <xsl:apply-templates select ="Cocoon"/>
          <BR/>
          <BR/>
          <xsl:apply-templates select ="OtherFeatures"/>
          <P ALIGN="center">
            <FONT SIZE="-1">
               Copyright &#169; 2001 <A HREF="http://www.borland.com">Borland Software Company</A>.<BR/>
               All rights reserved.
            </FONT>
          </P>
        </CENTER>
      </BODY>
    </HTML>
  </xsl:template>

  <xsl:template match="Cocoon">
    <TABLE BORDER="0" WIDTH="80%" CELLPADDING="4" BGCOLOR="#000000">
      <TR>
        <TD WIDTH="80%" ALIGN="center" BGCOLOR="#800080">
          <FONT SIZE="+3" COLOR="#FFFFFF"><STRONG><xsl:value-of select="MainTitle"/></STRONG></FONT>
        </TD>
      </TR>
      <TR>
        <TD WIDTH="100%" BGCOLOR="#ffffff" ALIGN="left">
          <STRONG><xsl:apply-templates select="MainDescription/Greeting"/></STRONG>
          <BR/>
          <xsl:for-each select="MainDescription/Para">
            <P>
              <xsl:value-of select="."/>
            </P>
          </xsl:for-each>
          <xsl:for-each select="MainDescription/FeatureList">
            <UL>
              <xsl:for-each select="FeatureListItems">
                <LI>
                  <xsl:value-of select="." />
                </LI>
              </xsl:for-each>
            </UL>
          </xsl:for-each>
        </TD>
      </TR>
    </TABLE>
  </xsl:template>

  <xsl:template match="OtherFeatures">
    <TABLE BORDER="0" WIDTH="100%" CELLPADDING="4">
      <TR>
        <TD WIDTH="60%" BGCOLOR="#ffffff" ALIGN="left">
          <TABLE BORDER="1" WIDTH="100%" CELLPADDING="4">
          <TR>
            <TH>
              <xsl:value-of select="Brief"/>
            </TH>
          </TR>
          <xsl:apply-templates select="Feature"/>
          </TABLE>
        </TD>
      </TR>
    </TABLE>
  </xsl:template>

  <xsl:template match="Feature">
    <TR>
      <TD WIDTH="60%" ALIGN="center" BGCOLOR="#FFFF00">
        <FONT SIZE="+1"><xsl:value-of select="Title"/></FONT>
      </TD>
    </TR>
    <TR>
      <TD WIDTH="60%" BGCOLOR="#ffffff" ALIGN="left">
        <xsl:apply-templates select="Description"/>
      </TD>
    </TR>
  </xsl:template>

  <xsl:template match="Description">
    <xsl:for-each select="Para">
      <P>
        <xsl:value-of select="."/>
      </P>
    </xsl:for-each>
    <xsl:for-each select="FeatureList">
      <UL>
        <xsl:for-each select="FeatureListItems">
          <LI>
            <xsl:value-of select="." />
          </LI>
        </xsl:for-each>
      </UL>
    </xsl:for-each>
  </xsl:template>

</xsl:stylesheet>

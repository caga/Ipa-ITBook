<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="Planets">
    <html>
      <xsl:apply-templates/>
    </html>
  </xsl:template>

  <xsl:template match="Planet">
    <P>
      <ul>
      Name is: <b><xsl:value-of select="Name"/></b><br/>
      Diameter is: <i><xsl:value-of select="Diameter"/></i>
      	<xsl:apply-templates select="Moon"/>
      </ul>
    </P>
  </xsl:template>

  <xsl:template match="Moon">
      <li><xsl:apply-templates/></li>
  </xsl:template>

  <xsl:template match="Name">
    <b>
      <xsl:apply-templates/>
    </b>
  </xsl:template>
</xsl:stylesheet>
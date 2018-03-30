<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
      <html>
        <head>
          <title>
              Planetary Diameter and Escape Velocity
          </title>
        </head>
        <body>
          <xsl:apply-templates select="Planets"/>
        </body>
      </html>
    </xsl:template>

    <xsl:template match="Planets">
      <h1>Diameter vs. Escape Velocity</h1>
      <table border="1" >
        <th>Planet</th>
        <th>Diameter</th>
        <th>Escape Velocity</th>
          <xsl:apply-templates select="Planet"/>
      </table>
    </xsl:template>

    <xsl:template match="Planet">
      <tr>
        <td><xsl:value-of select="Name"/></td>
        <td align="right">
            <xsl:value-of select="Diameter"/>
        </td>
        <td align="right">
            <xsl:value-of select="Escape-Velocity"/>
        </td>
      </tr>
    </xsl:template>

</xsl:stylesheet>

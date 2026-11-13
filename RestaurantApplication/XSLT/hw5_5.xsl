<?xml version="1.0" encoding="UTF-8"?>
<!--
    5. Map each UserName to a count of their Reviews.
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>UserName and Reviews</title></head><body>
        <table border="1">
            <tr>
                <th>UserName</th>
                <th>ReviewCount</th>
            </tr>
            <xsl:for-each-group select="Reviews/Review" group-by="UserName">
                <tr>
                    <td>
                        <xsl:value-of select="current-grouping-key()"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="count(current-group())"></xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each-group>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>
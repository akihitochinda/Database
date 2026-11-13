<?xml version="1.0" encoding="UTF-8"?>
<!--
    8. For each Review of “restaurantid1”, display the Review’s RestaurantId, UserName, and Rating.
-->
<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/RestaurantApplication">
        <html><head><title>Reviews for restaurantid1</title></head><body>
        <table border="1">
            <tr>
                <th>RestaurantId</th>
                <th>UserName</th>
                <th>Rating</th>
            </tr>
            <xsl:for-each select="Reviews/Review[RestaurantId='1']">
                <tr>
                    <td>
                        <xsl:value-of select="RestaurantId"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="UserName"></xsl:value-of>
                    </td>
                    <td>
                        <xsl:value-of select="Rating"></xsl:value-of>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        </body></html>
    </xsl:template>
</xsl:stylesheet>